package au.com.tava.Core.WebDriverUtils.selophane.elements.widget;

import au.com.tava.Core.WebDriverUtils.selophane.elements.base.ElementImpl;
import org.openqa.selenium.WebElement;

/**
 * Wrapper class like Select that wraps basic checkbox functionality.
 */
public class CheckBoxImpl extends ElementImpl implements CheckBox {

    /**
     * Wraps a WebElement with checkbox functionality.
     *
     * @param element to wrap up
     */
    public CheckBoxImpl(WebElement element) {
        super(element);
    }

    @Override
    public void toggle() {
        getWrappedElement().click();
    }

    @Override
    public void check() {
        if (!isChecked()) {
            toggle();
        }
    }

    @Override
    public void uncheck() {
        if (isChecked()) {
            toggle();
        }
    }

    @Override
    public boolean isChecked() {
        return getWrappedElement().isSelected();
    }
}
