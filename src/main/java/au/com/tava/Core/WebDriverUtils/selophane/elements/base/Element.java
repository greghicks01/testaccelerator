package au.com.tava.Core.WebDriverUtils.selophane.elements.base;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

/**
 * wraps a web element interface with extra functionality. Anything added here will be added to all descendants.
 */
@ImplementedBy( )
public interface Element extends WebElement, WrapsElement, Locatable {
    /**
     * Returns true when the inner element is ready to be used.
     *
     * @return boolean true for an initialized WebElement, or false if we were somehow passed a null WebElement.
     */
    boolean elementWired();

    /**
     * catches a stale element error and returns true
     * @return
     */
    boolean isElementStale();
}
