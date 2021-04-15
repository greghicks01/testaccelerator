package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static org.openqa.selenium.remote.DesiredCapabilities.android;


public final class Android extends Mobile {

    @Override
    public WebDriver getNewWebDriverInstance() throws MalformedURLException {
        return new AndroidDriver<>( new URL( hubUrl ) , dc );
    }

    @Override
    public DesiredCapabilities getDesiredCapabilities(){

        dc = android();
        return dc;
    }


}
