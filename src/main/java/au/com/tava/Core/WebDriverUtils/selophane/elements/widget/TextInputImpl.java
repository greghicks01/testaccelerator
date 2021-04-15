package au.com.tava.Core.WebDriverUtils.selophane.elements.widget;

import au.com.tava.Core.WebDriverUtils.selophane.elements.base.ElementImpl;
import org.openqa.selenium.WebElement;

/**
 * TextInput  wrapper.
 */
public class TextInputImpl extends ElementImpl implements TextInput {
    /**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    public TextInputImpl( WebElement element ) {
        super( element ) ;
    }

    @Override
    public void clear() {
        getWrappedElement().clear() ;
    }

    @Override
    public void set( CharSequence text ) {
        WebElement element = getWrappedElement();
        element.clear();
        element.sendKeys( text );
    }

    /**
     * Gets the value of an input field.
     * @return String with the value of the field.
     */
    @Override
    public String getText() {
        return getWrappedElement().getAttribute("value");
    }
}
