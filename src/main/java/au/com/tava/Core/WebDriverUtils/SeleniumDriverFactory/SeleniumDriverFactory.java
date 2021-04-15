package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory;

import au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers.Browsers;
import au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers.NameMobileDriverException;
import au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers.Remote;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.util.Strings;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class SeleniumDriverFactory {

    String browser;
    String version;
    String platform;

    String driverVersion;
    String device ;
    String deviceOSVersion ;
    String deviceAppPackage ;

    String browserclasspath = "au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.Browsers." ;
    String localAppHub = "http://127.0.0.1:4234";

    DesiredCapabilities extras = null ;

    /**
     *
     * @param targetBrowser
     * @param targetBrowserVersion
     * @param targetBrowserPlatform
     * @param webDriverVersion
     * @param targetDevice
     * @param targetDeviceOSVersion
     * @param targetDeviceAppPackage
     */
    public SeleniumDriverFactory(
        String targetBrowser ,
        String targetBrowserVersion ,
        String targetBrowserPlatform ,
        String webDriverVersion ,
        String targetDevice ,
        String targetDeviceOSVersion ,
        String targetDeviceAppPackage
    ){
        this.browser = StringUtils.capitalize( targetBrowser ).replace( " " , "") ; //proper case no spaces
        this.version = targetBrowserVersion ;
        this.platform = targetBrowserPlatform ;
    }

    public WebDriver getNewDriver() throws Exception {

        Browsers browser = getDriverInstance();

        DesiredCapabilities dc = browser.getDesiredCapabilities();

        if( extras != null )
            browser.setDesiredCapability( dc.merge( extras )  );

        if( browser.isMobile() ){}

        if ( ! Strings.isNullOrEmpty( System.getProperty( "hub.url") ) && ! browser.isMobile() ) {
            return  new Remote().sethubUrl( System.getProperty( "hub.url") ).getNewWebDriverInstance();
        }

        return browser.getNewWebDriverInstance();
    }

    private <T> T instantiateDriver(Class<T> driverProxy) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        Constructor<T> constructor = driverProxy.getConstructor();
        return constructor.newInstance();

    }

    public SeleniumDriverFactory setDriverClassPath(String value ){
        browserclasspath = value;
        return this;
    }

    @SuppressWarnings("unchecked")
    private Browsers getDriverInstance() {
        try {
            return instantiateDriver( ( Class<Browsers> ) Class.forName( browserclasspath + browser ) );
        } catch ( Exception e ) {
            throw new NameMobileDriverException( browser );
        }

    }

}
