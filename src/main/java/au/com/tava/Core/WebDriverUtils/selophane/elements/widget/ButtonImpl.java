package au.com.tava.Core.WebDriverUtils.selophane.elements.widget;

import au.com.tava.Core.WebDriverUtils.selophane.elements.base.ElementImpl;
import org.openqa.selenium.WebElement;

public class ButtonImpl extends ElementImpl implements Button {
    /**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    public ButtonImpl(WebElement element) {
        super(element);
    }
}
