package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers;

import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.openqa.selenium.remote.DesiredCapabilities.internetExplorer;

public final class InternetExplorer extends Browsers{

    public WebDriver getNewWebDriverInstance(){

        try {
            InternetExplorerDriverManager.getInstance().setup();
        } catch (Exception e){}

        return new InternetExplorerDriver( getDesiredCapabilities() );
    }

    public DesiredCapabilities getDesiredCapabilities(){

        dc = internetExplorer();
        dc.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        dc.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        dc.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
        dc.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, "accept");
        return dc;
    }

    @Override
    public boolean isMobile() {
        return false;
    }
}
