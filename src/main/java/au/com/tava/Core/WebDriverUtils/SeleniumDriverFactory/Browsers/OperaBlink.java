package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers;

import io.github.bonigarcia.wdm.OperaDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.openqa.selenium.remote.DesiredCapabilities.operaBlink;

public final class OperaBlink  extends Browsers{

    public WebDriver getNewWebDriverInstance(){

        OperaDriverManager.getInstance().setup();

        OperaOptions oO = new OperaOptions();
        //oO.merge( getDesiredCapabilities());

        return new OperaDriver(  oO );
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
