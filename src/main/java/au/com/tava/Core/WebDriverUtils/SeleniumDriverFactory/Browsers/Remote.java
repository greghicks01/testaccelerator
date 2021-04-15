package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Remote extends Browsers {

    String hubUrl;

    public Remote sethubUrl( String hub ) {
        hubUrl = hub ;
        return this;
    }

    @Override
    public DesiredCapabilities getDesiredCapabilities() {
        return null;
    }

    @Override
    public boolean isMobile() {
        return false;
    }

    @Override
    public WebDriver getNewWebDriverInstance() throws MalformedURLException {
        return new RemoteWebDriver( new URL( hubUrl ) , dc );
    }
}
