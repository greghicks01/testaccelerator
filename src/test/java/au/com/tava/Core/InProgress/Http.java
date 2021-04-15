package au.com.tava.Core.InProgress;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeMultipart;

import javax.net.ssl.*;
import java.security.*;


/**
 * Created by Greg on 9/02/2017.
 */
public class Http  {

    private static KeyStore trustStore;
    private static TrustManager blindTrustManager;
    private X509TrustManager standardTrustManager;

/*    public static KeyStore getTrustStore() {
        return trustStore;
    }

    public static TrustManager getBlindTrustManager() {
        return blindTrustManager;
    }

    public void teset(){
        List<String> groups = null;

        List<String> groupFiltered = groups.stream().filter( s -> s.equals("") ).collect(Collectors.toList());
    }

    public int connectToHttp(String endpoint, String uname, String pwd) throws IOException {
        URL url = new URL(endpoint);

        HttpURLConnection connection;

        if(endpoint.contains("https")){
            connection = (HttpsURLConnection) url.openConnection();
            //((HttpsURLConnection) connection).setSSLSocketFactory(getSecurityInfo());
        }
        else
        {
            connection = (HttpURLConnection)url.openConnection();
            String authString = String.join(":", uname, Base64.getDecoder().decode(pwd.getBytes())).getBytes();
            String auth = "Basic" + new sun.misc.BASE64Encoder().encode(authString.getBytes());
        }
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        //connection.setRequestProperty("Content-Type", toSend.getContentType());
        connection.setRequestProperty("SOAPAction","ebXML");
        connection.setRequestProperty("MIME Version","1.0");

        //toSend.writeTo(connection.getOutputStream());
    }

    *//**
     * Create an object from which SSLSocketFactories can be made
     *
     * @param cert InputStream for the P12 certificate file
     * @param password Passphrase for the P12 certificate
     * @param ksType Usually "PKCS12"
     * @param ksAlgorithm Usually "sunx509", may vary between JSSE providers
     * @return
     *//*
    public static SSLContext newSSLContext(InputStream cert,String password,String ksType,String ksAlgorithm)
            throws APIException {
        try {
            KeyStore ks = KeyStore.getInstance(ksType);
            ks.load(cert,password.toCharArray());

            // Get a KeyManager and initialize it
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(ksAlgorithm);
            kmf.init(ks,password.toCharArray());

            // Get a TrustManagerFactory and init with KeyStore
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(ksAlgorithm);
            tmf.init(ks);

            // Get the SSLContext to help create SSLSocketFactory
            SSLContext sslc = SSLContext.getInstance("TLS");
            sslc.init(kmf.getKeyManagers(),null,null);
            return sslc;
        }
        catch (Throwable ex) {
            throw new APIException("Error creating SSL context",ex);
        }

    }

    public static SSLContext createSSLContext(final KeyStore keyStore, String password, final KeyStore trustStore) throws Exception {
        KeyManager[] keyManagers;
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password.toCharArray());
        keyManagers = keyManagerFactory.getKeyManagers();

        TrustManager[] trustManagers = null;
        if (trustStore != null) {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            trustManagers = trustManagerFactory.getTrustManagers();
        }

        SSLContext sslContext;
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagers, trustManagers, null);

        return sslContext;
    }


    *//**
     * Get a custom SSL context for secure server connections. The key store of
     * the context contains the private key and bridge certificate. The trust
     * manager contains system and own certificates or blindly accepts every
     * server certificate.
     *//*
    public static SSLContext getCustomSSLContext(
            PrivateKey privateKey,
            X509Certificate bridgeCert,
            boolean validateCertificate)
            throws KeyStoreException,
            IOException,
            NoSuchAlgorithmException,
            CertificateException,
            UnrecoverableKeyException,
            NoSuchProviderException,
            KeyManagementException {
        // in-memory keystore
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(null, null);
        keystore.setKeyEntry("private",
                privateKey,
                new char[0],
                (java.security.cert.Certificate[]) new Certificate[]{(Certificate) bridgeCert});

        // key managers
        KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmFactory.init(keystore, new char[0]);

        KeyManager[] km = kmFactory.getKeyManagers();

        // trust managers
        TrustManager[] tm;
        if (validateCertificate) {
            // use modified truststore
            TrustManagerFactory tmFactory = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmFactory.init(getTrustStore());
            tm = tmFactory.getTrustManagers();
        } else {
            // trust everything!
            tm = new TrustManager[]{getBlindTrustManager()};
        }
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(km, tm, null);
        return ctx;
    }

    *//**
     * Constructor for EasyX509TrustManager.
     *//*
    public void EwsX509TrustManager(KeyStore keystore, TrustManager trustManager)
            throws NoSuchAlgorithmException, KeyStoreException {
        //super();
        if (trustManager == null) {
            TrustManagerFactory factory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init(keystore);
            TrustManager[] trustmanagers = factory.getTrustManagers();
            if (trustmanagers.length == 0) {
                throw new NoSuchAlgorithmException("no trust manager found");
            }
            this.standardTrustManager = (X509TrustManager) trustmanagers[0];
        } else {
            standardTrustManager = (X509TrustManager) trustManager;
        }
    }

    private SSLOptions getSslOptions() throws Exception
    {
        // init trust store
        TrustManagerFactory tmf = null;
        NativeString sslOptions=null;
        String truststorePath = (String) sslOptions.get("ssl.truststore");
        String truststorePassword = (String) sslOptions.get("ssl.truststore.password");
        if (truststorePath != null && truststorePassword != null)
        {
            FileInputStream tsf = new FileInputStream(truststorePath);
            KeyStore ts = KeyStore.getInstance("JKS");
            ts.load(tsf, truststorePassword.toCharArray());
            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ts);
        }

        // init key store
        KeyManagerFactory kmf = null;
        String keystorePath = (String) sslOptions.get("ssl.keystore");
        String keystorePassword = (String) sslOptions.get("ssl.keystore.password");
        if (keystorePath != null && keystorePassword != null)
        {
            FileInputStream ksf = new FileInputStream(keystorePath);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(ksf, keystorePassword.toCharArray());
            kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, keystorePassword.toCharArray());

        }

        // init cipher suites
        String[] ciphers = DEFAULT_SSL_CIPHER_SUITES;
        if (sslOptions.containsKey("ssl.ciphersuites"))
            ciphers = sslOptions.get("ssl.ciphersuits").split(",\\s*");

        SSLContext ctx = SSLContext.getInstance("SSL");
        ctx.init(kmf == null ? null : kmf.getKeyManagers(),
                tmf == null ? null : tmf.getTrustManagers(),
                new SecureRandom());

        return new SSLOptions(ctx, ciphers);
    }
    MyX509TrustManager(File trustStore, char[] password) throws Exception {
        // create a "default" JSSE X509TrustManager.

        KeyStore ks = KeyStore.getInstance("JKS");

        ks.load(new FileInputStream(trustStore), password);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
        tmf.init(ks);

        TrustManager tms [] = tmf.getTrustManagers();

    *//*
     * Iterate over the returned trustmanagers, look
     * for an instance of X509TrustManager.  If found,
     * use that as our "default" trust manager.
     *//*
        for (int i = 0; i < tms.length; i++) {
            if (tms[i] instanceof X509TrustManager) {
                X509TrustManager pkixTrustManager = (X509TrustManager) tms[i];
                return;
            }
        }

    *//*
     * Find some other way to initialize, or else we have to fail the
     * constructor.
     *//*
        throw new Exception("Couldn't initialize");
    }
    *//**
     * Returns the TrustManagerFactory instance
     *
     * @return TrustManagerFactory instance
     *//*
    public TrustManagerFactory getTrustManagerFactoryInstance() {

        try {
            if (log.isDebugEnabled()) {
                log.debug("Creating a TrustManagerFactory instance");
            }
            KeyStore trustStore = this.getTrustStore();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            return trustManagerFactory;
        } catch (Exception e) {
            handleException("Error getting TrustManagerFactory: ", e);
        }

        return null;
    }

    public XX509TrustManager(KeyStore keystore)
            throws NoSuchAlgorithmException, KeyStoreException {
        super();
        TrustManagerFactory factory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(keystore);
        TrustManager[] trustmanagers = factory.getTrustManagers();
        if (trustmanagers.length == 0) {
            throw new NoSuchAlgorithmException("no trust manager found");
        }
        this.mStandardTrustManager = (X509TrustManager) trustmanagers[0];
    }

    void reloadTrustManager() throws GeneralSecurityException {
        Object certManager = null;
        KeyStore ks = certManager.asKeyStore();

        // initialize a new TMF with the KeyStore we just created
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        // acquire an X509 trust manager from the TMF
        // and update the `tmDelegate` to that value
        TrustManager[] tms = tmf.getTrustManagers();
        for (TrustManager tm : tms) {
            if (tm instanceof X509TrustManager) {
                tmDelegate = (X509TrustManager) tm;
                return;
            }
        }

        // should have returned in the `for` loop above
        throw new NoSuchAlgorithmException("No X509TrustManager in TrustManagerFactory");
    }


    *//**
     * Makes {@link HttpsURLConnection} accepts our certificate.
     *//*
    private static void acceptMyCertificate() {
        final char[] JKS_PASSWORD = "persona".toCharArray();
        final char[] KEY_PASSWORD = "password".toCharArray();
        try {
		*//* Get the JKS contents *//*
            final KeyStore keyStore = KeyStore.getInstance("JKS");
            try (final InputStream is = new FileInputStream(fullPathOfKeyStore())) {
                keyStore.load(is, JKS_PASSWORD);
            }
            final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
                    .getDefaultAlgorithm());
            kmf.init(keyStore, KEY_PASSWORD);
            final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory
                    .getDefaultAlgorithm());
            tmf.init(keyStore);

		*//*
		 * Creates a socket factory for HttpsURLConnection using JKS
		 * contents
		 *//*
            final SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new java.security.SecureRandom());
            final SSLSocketFactory socketFactory = sc.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);

        } catch (final GeneralSecurityException | IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    public void init(Properties properties) throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ts = KeyStore.getInstance("JKS");
        String keyStorePassword = properties.getProperty("keyStorePassword");
        if (keyStorePassword == null) {
            keyStorePassword = System.getProperty("javax.net.ssl.keyStorePassword");
        }
        String keyStore = properties.getProperty("keyStore");
        if (keyStore == null) {
            keyStore = System.getProperty("javax.net.ssl.keyStore");
        }
        if (keyStore == null || keyStorePassword == null) {
            throw new RuntimeException("SSL is enabled but keyStore[Password] properties aren't set!");
        }
        String keyManagerAlgorithm = getProperty(properties, "keyManagerAlgorithm", "SunX509");
        String trustManagerAlgorithm = getProperty(properties, "trustManagerAlgorithm", "SunX509");
        String protocol = getProperty(properties, "protocol", "TLS");
        final char[] passPhrase = keyStorePassword.toCharArray();
        final String keyStoreFile = keyStore;
        ks.load(new FileInputStream(keyStoreFile), passPhrase);
        ts.load(new FileInputStream(keyStoreFile), passPhrase);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(keyManagerAlgorithm);
        kmf.init(ks, passPhrase);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(trustManagerAlgorithm);
        tmf.init(ts);
        sslContext = SSLContext.getInstance(protocol);
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
    }

    public MyX509TrustManager(Properties props) throws Exception{
        boolean trustServerCertificate  =  props.getProperty("trustServerCertificate") != null;
        if (trustServerCertificate)
            return;

        serverCertFile =  props.getProperty("serverSslCert");
        InputStream inStream;

        if (serverCertFile.startsWith("-----BEGIN CERTIFICATE-----")) {
            inStream = new ByteArrayInputStream(serverCertFile.getBytes());
        } else if (serverCertFile.startsWith("classpath:")) {
            // Load it from a classpath relative file
            String classpathFile = serverCertFile.substring("classpath:".length());
            inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(classpathFile);
        } else {
            inStream = new FileInputStream(serverCertFile);
        }

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate ca = (X509Certificate) cf.generateCertificate(inStream);
        inStream.close();
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        try {
            // Note: KeyStore requires it be loaded even if you don't load anything into it:
            ks.load(null);
        } catch (Exception e) {

        }
        ks.setCertificateEntry(UUID.randomUUID().toString(), ca);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        for(TrustManager tm : tmf.getTrustManagers()) {
            if (tm instanceof X509TrustManager) {
                trustManager = (X509TrustManager) tm;
                break;
            }
        }
        if (trustManager == null) {
            throw new RuntimeException("No X509TrustManager found");
        }
    }*/
}
