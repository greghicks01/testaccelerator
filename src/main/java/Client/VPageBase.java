package Client;

import org.openqa.selenium.*;
import org.testng.Reporter;
import org.testng.log4testng.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Greg on 26/01/2017.
 */
public abstract class VPageBase {

    static protected WebDriver _w; //used by all classes

    protected Logger log;
    protected Reporter reporter;

    private JavascriptExecutor js = (JavascriptExecutor) _w;

    public VPageBase() {
    }

    /**
     * highlights elements for proving we have an element
     *
     * @param e
     */
    public void highlightElement(WebElement e)  {

        String originalStyle = e.getAttribute("style");
        js.executeScript("arguments[0].style.border='4px groove green'", e);

    }

    /**
     * retreives the elements from the parent
     *
     * @param parent
     * @param childrenBy
     * @return
     */
    protected List<WebElement> getElementsInParent(WebElement parent, By childrenBy) {
        return parent.findElements( childrenBy ) ;
    }

    /**
     * Returns the trimmed text in the supplied elements
     *
     * @param elements
     * @return
     */
    protected List< String > getElementsText( List< WebElement > elements ) {
        List< String > strings = new ArrayList<>();

        for (WebElement element : elements) {
            strings.add(element.getText().trim());
        }
        return strings;
    }




}
