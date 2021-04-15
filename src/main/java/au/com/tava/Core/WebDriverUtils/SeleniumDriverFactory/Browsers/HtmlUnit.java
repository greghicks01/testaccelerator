package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers;

import io.github.bonigarcia.wdm.OperaDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.openqa.selenium.remote.DesiredCapabilities.operaBlink;

public final class HtmlUnit extends Browsers {

    public WebDriver getNewWebDriverInstance(){

        try {
            OperaDriverManager.getInstance().setup();
        } catch (Exception e){}

        return new OperaDriver(  getDesiredCapabilities() );
    }

    public DesiredCapabilities getDesiredCapabilities(){

        dc = operaBlink();
        return dc;
    }

    @Override
    public boolean isMobile() {
        return false;
    }
}
