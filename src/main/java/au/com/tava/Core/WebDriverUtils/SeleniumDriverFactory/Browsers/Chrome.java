package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.github.bonigarcia.wdm.ChromeDriverManager;

import java.util.List;

import static org.openqa.selenium.remote.DesiredCapabilities.chrome;

public final class Chrome extends Browsers {

    ChromeOptions chromeOptions;

    public Chrome(){
        dc = chrome();
        chromeOptions  = new ChromeOptions();
    }

    public WebDriver getNewWebDriverInstance(){

        try {
            ChromeDriverManager.getInstance().setup();
        } catch ( IllegalStateException ex) { }
        chromeOptions.addArguments("start-maximized");
        //chromeOptions.addArguments("--window-size=1920,1080");
        //driver.manage().window().setSize(new Dimension(1024,768));

        //dc.setCapability( );

        return new ChromeDriver( dc );
    }

    public void addChromeOptions( List<String > options){

    }

    public DesiredCapabilities getDesiredCapabilities(){
        return dc;
    }

}
