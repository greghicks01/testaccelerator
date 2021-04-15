package au.com.tava.Core;

import au.com.tava.Core.FileIO.XL;
import au.com.tava.Core.WebDriverUtils.CustomAnnotations.BrowserMap;
import au.com.tava.Core.WebDriverUtils.CustomAnnotations.TriggerLevel;
import au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.MultiBrowserController;

import au.com.tava.Core.runner.TestNGListeners;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Listeners({TestNGListeners.class})

/**
 * Created by Greg on 20/05/2017.
 */
public class TestBase {

    protected final String contextDriversAttribute = "drivers";

    static protected ThreadLocal<ITestContext> context = new ThreadLocal<>();
    protected WebDriver driver;
    String targetPlatform = "";
    String baseURL = "";

    public static ThreadLocal<MultiBrowserController> multiBrowserController = new ThreadLocal<>();
    protected static List<BrowserMap> browserMaps = new ArrayList<>();

    @BeforeSuite
    public void beforeSuite(ITestContext context )  throws IllegalAccessException, InstantiationException {

        context.setAttribute("context",context);
        this.context.set( context );

        for(ITestNGMethod method : context.getAllTestMethods()) {

            browserMaps.addAll( Arrays.asList( method.getConstructorOrMethod().getMethod()
                    .getDeclaredAnnotationsByType( BrowserMap.class )));
            browserMaps.addAll( Arrays.asList( method.getTestClass()
                    .getRealClass().getAnnotationsByType( BrowserMap.class )) );
        }

    }

    private void startWeb(TriggerLevel triggerLevel ) throws Exception {

        Reporter.log( "Start Web Thread id" + Thread.currentThread().getId() , true );
        if( multiBrowserController.get() == null ) multiBrowserController.set(new MultiBrowserController());

        Reporter.log( "Thread id" + Thread.currentThread().getId() , true );
        for(BrowserMap browserMap : browserMaps){
            if ( browserMap == null ) continue ;
            if ( browserMap.disabled() ) return ;

            Reporter.log( "Thread id" + Thread.currentThread().getId() , true );
            if(browserMap.trigger() == triggerLevel){

                multiBrowserController.get().addSessionDriver(browserMap.browser(), System.getProperty("target.browser"));
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

    @BeforeTest
    public void beforeTest( ) throws Exception {
        startWeb(TriggerLevel.TEST);
    }

    @BeforeGroups
    public void beforeGroups( ) throws InstantiationException, IllegalAccessException {
    }

    @BeforeClass
    public void beforeClass( ) throws Exception {

        startWeb(TriggerLevel.CLASS);
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context) throws Exception {

        startWeb( TriggerLevel.METHOD );
    }

    @AfterGroups ( alwaysRun = true )
    public void afterGroups( ) {

    }

    @AfterMethod ( alwaysRun = true )
    public void afterMethod(Method method) throws InstantiationException, IllegalAccessException {

        stopWeb( TriggerLevel.METHOD );
    }

    @AfterTest ( alwaysRun = true )
    public void afterTest( ) throws InstantiationException, IllegalAccessException {

        stopWeb( TriggerLevel.TEST );
    }

    @AfterClass ( alwaysRun = true )
    public void afterClass() throws InstantiationException, IllegalAccessException {
        stopWeb( TriggerLevel.CLASS );
    }

    @AfterSuite ( alwaysRun = true )
    public void afterSuite( ) { }

    public static final String ReadExcelData = "readExcelData";
    @DataProvider( name = ReadExcelData , parallel = true )
    public Object[][] readExcelData(Method method) throws IOException, IllegalAccessException {
        XL xl = new XL(Globals.dataFileName, method);
        xl.setSkipHeaders(new ArrayList<String>(){{add("Comments");}});
        return xl.getSheetData();
    }

    public static void takeScreenShot(WebDriver d, String description) throws IOException {

        if(d == null) return;
        ITestContext c = context.get();

        File screenshot = ((TakesScreenshot) d).getScreenshotAs(OutputType.FILE);

        Path outputFolder = new File(c.getOutputDirectory()).toPath();
        Path outputImageFolder = Paths.get(outputFolder.toString(),"image");

        String methodName = c.getName();
        String fileNameTemplate = "{0}_{1}_{2}" + Globals.imageFileExtension;

        String fileName = MessageFormat.format(fileNameTemplate,new DateTime().toString( Globals.imageDateTimeFormat ),methodName, description);
        Path path = Paths.get(outputImageFolder.toString(),fileName);

        FileUtils.moveFile(screenshot, path.toFile());
        String relPath = Paths.get(".", outputFolder.relativize(path).toString()).toString();

        // embed a thumbnail into the link
        String linkImageTemplate = "<a href=\"{0}\" target=\"blank\"> <img src=\"{1}\" height=\"{2}\" alt \"{3}\"/></a>";
        String linkImage = MessageFormat.format
                (
                        linkImageTemplate,
                        relPath,
                        relPath,
                        100,
                        FilenameUtils.getName(path.toString())
                );
        Reporter.log(linkImage , true);
    }
}
