package Client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

/**
 * Created by Greg on 26/01/2017.
 */
public class NIBLandingPage extends VPageBase {
    //repo
    By
            navigationHeader = By.className("nib-header-menu"),
            menuItemsBy = By.tagName("a");

    WebElement menu;

    //class fields/variables

    //class methods

    //constructor
    public NIBLandingPage(WebDriver w) {

        log = Logger.getLogger(this.getClass());
        _w = w;
        // wait and get the item reference - safer with some browsers
        menu = (new WebDriverWait(_w, 5)).until(ExpectedConditions.elementToBeClickable(navigationHeader));
        log.info("Menu successfully found");
    }

    // class methods
    public void navigationMenu(String menuItem) {
        menu.findElement(By.linkText(menuItem)).click(); //breaks because of drop down
    }

}