package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import static org.openqa.selenium.remote.DesiredCapabilities.safari;

public final class Safari extends Browsers {

    public WebDriver getNewWebDriverInstance(){
        SafariOptions so = SafariOptions.fromCapabilities( dc );
        return new SafariDriver( so );
    }

    public DesiredCapabilities getDesiredCapabilities(){
        dc = safari();
        return dc;
    }

    @Override
    public boolean isMobile() {
        return false;
    }
}
