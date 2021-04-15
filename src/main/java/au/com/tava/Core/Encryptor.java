package au.com.tava.Core;

import au.com.tava.Core.InProgress.CoreBase;
import org.testng.log4testng.Logger;
import java.util.Base64;

/**
 * Created by Greg on 6/02/2017.
 */
public class Encryptor extends CoreBase {

    //URL: https://stackoverflow.com/questions/19743851/base64-java-encode-and-decode-a-string

    private Logger logger = Logger.getLogger(this.getClass());

    public static String encode(String toEncode) {
        return new String(Base64.getEncoder().encode(toEncode.getBytes()));
    }

    public static String decode(String toDecode) {
        return new String(Base64.getDecoder().decode(toDecode));
    }

}
