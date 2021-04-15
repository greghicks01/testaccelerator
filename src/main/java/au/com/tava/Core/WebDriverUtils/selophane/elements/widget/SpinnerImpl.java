package au.com.tava.Core.WebDriverUtils.selophane.elements.widget;

import au.com.tava.Core.WebDriverUtils.selophane.elements.base.ElementImpl;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class SpinnerImpl extends ElementImpl implements Spinner {

    /**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    public SpinnerImpl( WebElement element ) {
        super( element );
    }

    @Override
    public Boolean isVisible() {

        try {
            return getWrappedElement().isDisplayed();
        } catch ( StaleElementReferenceException e) {
            return false;
        }
    }
}
