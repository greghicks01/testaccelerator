package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers;

import io.github.bonigarcia.wdm.EdgeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.openqa.selenium.remote.DesiredCapabilities.edge;

public final class Edge extends Browsers{

    public Edge(){
        dc = edge();
        EdgeDriverManager.getInstance().setup();
    }

    public WebDriver getNewWebDriverInstance(){

        return new EdgeDriver( getDesiredCapabilities() );
    }

    public DesiredCapabilities getDesiredCapabilities(){
        return dc;
    }

    @Override
    public boolean isMobile() {
        return false;
    }
}
