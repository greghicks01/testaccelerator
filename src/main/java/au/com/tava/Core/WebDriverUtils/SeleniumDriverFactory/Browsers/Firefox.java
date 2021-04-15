package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers;

import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.openqa.selenium.remote.DesiredCapabilities.firefox;

public final class Firefox  extends Browsers{

    public WebDriver getNewWebDriverInstance(){

        try {
            FirefoxDriverManager.getInstance().setup();
        } catch (Exception e){}

        return new ChromeDriver( getDesiredCapabilities() );
    }

    public DesiredCapabilities getDesiredCapabilities(){

        dc = firefox();
        return dc;
    }

    @Override
    public boolean isMobile() {
        return false;
    }
}
