package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers;

/**
 * Created by Greg on 17/06/2017.
 */

public class NameMobileDriverException extends RuntimeException {

    public NameMobileDriverException(String browserName ){
        super( browserName + " not found ");
    }
}
