package au.com.tava.Core;

import au.com.tava.Core.WebDriverUtils.CustomAnnotations.BrowserMap;
import au.com.tava.Core.WebDriverUtils.CustomAnnotations.WebInterfaces;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greg on 7/05/2017.
 */
public class WebTable extends TestBase {


    @Test
    @BrowserMap(browser = "test" , environment = WebInterfaces.env1 )
    public void testWebTables() throws IllegalAccessException, InstantiationException, IOException {
        multiBrowserController.get().getSessionDriver("test").navigate().to("https://www.w3schools.com/html/html_tables.asp");

        //find table and hand to tables
        List<String> header = new ArrayList<>();
        List<WebElement> cells = new ArrayList<>();

        WebElement anchor = multiBrowserController.get().getSessionDriver("test").findElement(By.id("customers"));
        cells = anchor.findElements(By.tagName("td"));
        List<WebElement> headers  = anchor.findElements(By.tagName("th"));
        for(WebElement w : headers){
            header.add(w.getText());
        }

        WebTables table = new WebTables();

        table.setHeaders( header );
        table.setTableCells( cells );

        Assert.assertTrue( table.searchTableColumnForValue( "Company" , "Centro comercial Moctezuma" ) ) ;
        Reporter.log("'Centro comercial Moctezuma' found in table column 'Company'",true);
        Assert.assertFalse( table.searchTableColumnForValue( "Contact" , "Roland Mendel",2 ) );
        Reporter.log( "2 occurance of 'Roland Mendel' not found in table column 'Contact'",true ) ;
        Assert.assertFalse( table.searchTableColumnForValue( "Country" , "England" ) ) ;
        Reporter.log("'England' not found in table column 'Country'",true);

        takeScreenShot( multiBrowserController.get().getSessionDriver("test") , "Success" );

    }
}
