package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static org.openqa.selenium.remote.DesiredCapabilities.iphone;


public final class Ios extends Mobile {

    public WebDriver getNewWebDriverInstance(  ) throws MalformedURLException {
        return new IOSDriver<>( new URL( hubUrl ) , dc );
    }

    public DesiredCapabilities getDesiredCapabilities(){
        dc = iphone();
        return dc;
    }
}
