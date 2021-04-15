package au.com.tava.Core.InProgress;

import org.testng.log4testng.Logger;

import javax.activation.DataHandler;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.soap.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Greg on 22/11/2016.
 */
public class HTTPDriver {

    private Logger logger = Logger.getLogger(this.getClass());



    // REST Section
    public void httpCon() throws IOException {
        String httpsURL = "https://careers.virtusa.com/";
        URL myurl = new URL(httpsURL);
        HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
        InputStream ins = con.getInputStream();
    }
}
