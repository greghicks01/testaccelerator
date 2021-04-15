package test;

import au.com.tava.Core.WebDriverUtils.CustomAnnotations.BrowserMap;
import au.com.tava.Core.WebDriverUtils.CustomAnnotations.TriggerLevel;
import au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.MultiBrowserController;
import au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.SeleniumDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class basetest {
    protected final String contextDriversAttribute = "drivers";

    static protected ThreadLocal<ITestContext> context = new ThreadLocal<>();
    protected WebDriver driver;
    String targetPlatform = "";
    String baseURL = "";

    public static ThreadLocal<MultiBrowserController> multiBrowserController = new ThreadLocal<>();
    protected static List<BrowserMap> browserMaps = null;


    private void startWeb(TriggerLevel triggerLevel ) throws Exception {

        Reporter.log( "Start Web Thread id" + Thread.currentThread().getId() , true );
        if( multiBrowserController.get() == null ) multiBrowserController.set(new MultiBrowserController());

        Reporter.log( "Thread id" + Thread.currentThread().getId() , true );
        for(BrowserMap browserMap : browserMaps){
            if ( browserMap == null ) continue ;
            if ( browserMap.disabled() ) return ;

            Reporter.log( "Thread id" + Thread.currentThread().getId() , true );
            if(browserMap.trigger() == triggerLevel){

                multiBrowserController.get().addSessionDriver(browserMap.browser(), System.getProperty( "browser" ) );
            }
        }
    }

    private void stopWeb(TriggerLevel triggerLevel) throws InstantiationException, IllegalAccessException {

        Reporter.log( "Stop Web Thread id" + Thread.currentThread().getId() , true );
        for(BrowserMap browserMap : browserMaps){
            if ( browserMap == null ) continue ;
            if ( browserMap.disabled() ) return ;

            if( browserMap.trigger() == triggerLevel ){
                multiBrowserController.get().quitSession( browserMap.browser() );
            }
        }
    }

    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        Reporter.log( "Test Thread beforeSuite id" + Thread.currentThread().getId() , true );
        context.setAttribute("context",context);

        // fires up more than expected?
        browserMaps = new ArrayList<>();
        for(ITestNGMethod method : context.getAllTestMethods()) {

            browserMaps.addAll( Arrays.asList( method.getConstructorOrMethod().getMethod()
                    .getDeclaredAnnotationsByType( BrowserMap.class ) ) );
            browserMaps.addAll( Arrays.asList( method.getTestClass()
                    .getRealClass().getAnnotationsByType( BrowserMap.class ) ) );
        }
    }

    @BeforeGroups
    public void beforeGroups(){
        Reporter.log( "Test Thread beforeGroups id" + Thread.currentThread().getId() , true );
    }

    @BeforeTest
    public void beforeTest() throws Exception {
        Reporter.log( "Test Thread beforeTest id" + Thread.currentThread().getId() , true );
        startWeb(TriggerLevel.TEST);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        Reporter.log( "Test Thread beforeMethod* id" + Thread.currentThread().getId() , true );
        startWeb( TriggerLevel.METHOD );
    }

    @BeforeClass
    public void beforeClass() throws Exception {
        Reporter.log( "Test Thread beforeClass id" + Thread.currentThread().getId() , true );
        startWeb(TriggerLevel.CLASS);
    }
    @AfterSuite
    public void afterSuite() throws IllegalAccessException, InstantiationException {
        Reporter.log( "Test Thread afterSuite id" + Thread.currentThread().getId() , true );
    }

    @AfterClass
    public void afterClass() throws IllegalAccessException, InstantiationException {
        Reporter.log( "Test Thread afterClass id" + Thread.currentThread().getId() , true );
        stopWeb( TriggerLevel.CLASS );
    }

    @AfterTest
    public void afterTest() throws IllegalAccessException, InstantiationException {
        Reporter.log( "Test Thread afterTest id" + Thread.currentThread().getId() , true );
        stopWeb( TriggerLevel.TEST );
    }

    @AfterMethod
    public void afterMethod() throws IllegalAccessException, InstantiationException {
        Reporter.log( "Test Thread afterMethod* id" + Thread.currentThread().getId() , true );
        stopWeb( TriggerLevel.METHOD );
    }

}
