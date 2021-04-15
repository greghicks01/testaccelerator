package au.com.tava.Core.InProgress;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by Greg on 8/06/2017.
 */
public class CustomX509TrustManager implements X509TrustManager {

    X509TrustManager defaultTrustManager;

        public void CustomX509TrustManager( KeyStore keystore ) throws KeyStoreException, NoSuchAlgorithmException {
            TrustManagerFactory trustMgrFactory = TrustManagerFactory
                    .getInstance( TrustManagerFactory.getDefaultAlgorithm() ) ;
            trustMgrFactory.init( keystore );
            TrustManager trustManagers[] = trustMgrFactory.getTrustManagers();
            for ( int i = 0 ; i < trustManagers.length ; i++ ) {
                if ( trustManagers[ i ] instanceof X509TrustManager ) {
                    defaultTrustManager = ( X509TrustManager ) trustManagers [i ];
                    return;
                }
            }
        }

    @Override
    public void checkClientTrusted( X509Certificate[] x509Certificates , String s ) throws CertificateException {

    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        try {
            defaultTrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException ce) {
            /* Handle untrusted certificates */
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[ 0 ] ;
    }
}