package au.com.tava.Core.SeleniumDriverFactory.Browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;

public abstract class Browsers {

    protected DesiredCapabilities dc;

    abstract public WebDriver getNewWebDriverInstance() throws MalformedURLException;
    abstract public DesiredCapabilities getDesiredCapabilities();

    public Browsers setDesiredCapability( DesiredCapabilities updatedValues ){
        dc = updatedValues;
        return this;
    }


    public boolean isMobile() {
        return false;
    }

}
