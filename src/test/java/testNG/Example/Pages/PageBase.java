package testNG.Example.Pages;

import org.openqa.selenium.WebDriver;

/**
 * Created by JKV on 20/05/2017.
 */
public class PageBase {
    protected WebDriver driver;
    public String readPageTitle() {
        return "Search Page";
    }
}
