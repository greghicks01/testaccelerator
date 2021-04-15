package au.com.tava.Core.WebDriverUtils.selophane.elements.widget;

import au.com.tava.Core.WebDriverUtils.selophane.elements.base.ElementImpl;
import org.openqa.selenium.WebElement;

public class RadioButtonImpl extends ElementImpl implements RadioButton {
    /**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    public RadioButtonImpl(WebElement element) {
        super(element);
    }

    @Override
    public String getCurrentValue() {
        return null;
    }

    @Override
    public void setNewValue(String newValue) {

    }
}
