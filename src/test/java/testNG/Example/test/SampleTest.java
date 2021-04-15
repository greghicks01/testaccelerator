package testNG.Example.test;

import au.com.tava.Core.WebDriverUtils.CustomAnnotations.*;
import au.com.tava.Core.FileIO.FileFunc;
import au.com.tava.Core.TestBase;
import au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.MultiBrowserController;
import au.com.tava.Core.WebDriverUtils.selophane.elements.widget.Table;
import au.com.tava.Core.WebDriverUtils.selophane.elements.widget.TableImpl;
import au.com.tava.Core.WebDriverUtils.selophane.elements.widget.TextInput;
import au.com.tava.Core.WebDriverUtils.selophane.elements.widget.TextInputImpl;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.joda.time.Period;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import testNG.Example.Pages.LoginPage;
import testNG.Example.Pages.SearchPage;


import java.io.File;
import java.io.IOException;

import static au.com.tava.Core.WebDriverUtils.CustomAnnotations.WebInterfaces.*;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by JKV on 20/05/2017.
 */
public class SampleTest extends TestBase {

    public final String BROWSER_NAME = "browser";
    public final String BROWSER_NAME2 = "browser2";

    @Test//(dataProvider = ReadExcelData )
    //@BrowserMap(browser = BROWSER_NAME, environment = env1)
    public void LoginTest(){  //String UserName, String Password, String Result) throws IllegalAccessException, InstantiationException {
        Reporter.log( "Test Thread id" + Thread.currentThread().getId() , true );
        WebDriverManager.chromedriver().setup();
        WebDriver w = //MultiBrowserController.getSessionDriver( BROWSER_NAME );
                new ChromeDriver() ;
        w.navigate().to("https://google.com.au");
        TextInput textInput =  new TextInputImpl( w.findElement( By.name("q") ) );
        textInput.set("Abc");
        Table table =  new TableImpl( w.findElement( By.name("q") ) );
        table.getCellAtIndex( 0,0) ;
        w.quit();
    }

    @Test(dataProvider = "readExcelData")
    @BrowserMap( browser = BROWSER_NAME2, environment = env1)
    public void SearchTest(String UserName, String Password, String searchValue, String tableColumn, boolean Result) throws IllegalAccessException, InstantiationException {

        LoginPage loginPage = PageFactory.initElements( MultiBrowserController.getSessionDriver(BROWSER_NAME2), LoginPage.class );
        loginPage.login(UserName, Password);

        SearchPage searchPage = new SearchPage();
        Assert.assertEquals(searchPage.readPageTitle(), "Search Page");

        searchPage.searchForValue(searchValue);
    }

    @Test
    public void testFileConsumed() throws IOException, InterruptedException {
        String output = "C:\\test.txt";

        FileFunc.copyFile( new File( "src.txt") , new File(output) ) ;

        Assert.assertTrue( FileFunc.waitForFileToBeConsumed( new File(output) , new Period().withMinutes(2) ) ,
        "File was not consumed as expected" );
    }

    public void testStaleElement() {
        Wait<WebDriver> stubbornWait = new FluentWait<>(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(5, SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        //WebElement foo = stubbornWait.until(new Function<WebDriver, WebElement>() {
        //    public WebElement apply(WebDriver driver) {
        //         return driver.findElement(By.id("foo"));
        //     }
        //  });
    }

}