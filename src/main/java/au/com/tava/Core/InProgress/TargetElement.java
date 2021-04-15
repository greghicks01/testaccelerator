package au.com.tava.Core.InProgress;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greg on 6/12/2016.
 */
public class TargetElement {

    static private WebElement target;
    private Logger logger = Logger.getLogger(this.getClass());

    public static void setTarget(WebElement e) {
        target = e;
    }

    public static void set(String value) {
        //put "smart object code here"
        if (!target.isEnabled()) throw new WebDriverException("Element is not enabled");

        switch (target.getTagName()) {
            case "input":
                //include checkboxes
                break;
            case "button":
                break;
            case "select":
                setSelect(value);
        }

    }

    private static void setSelect(String value) {

        Select se = new Select(target);
        se.selectByVisibleText(value);

    }

    public static String get() {
        //put "smart object code here"
        return "";
    }

    public static void click() {
        //put "smart object code here"
    }

    public List<String> getDropListValues() {
        ArrayList<String> values = new ArrayList<String>();
        Select se = new Select(target);
        for (WebElement option : se.getOptions())
            values.add(option.getText());

        return values;
    }
}
