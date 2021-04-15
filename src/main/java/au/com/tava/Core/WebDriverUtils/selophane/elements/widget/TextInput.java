package au.com.tava.Core.WebDriverUtils.selophane.elements.widget;


import au.com.tava.Core.WebDriverUtils.selophane.elements.base.Element;
import au.com.tava.Core.WebDriverUtils.selophane.elements.base.ImplementedBy;

/**
 * Text field functionality.
 */
@ImplementedBy( TextInputImpl.class )
public interface TextInput extends Element {
    /**
     * @param text The text to type into the field.
     */
    void set(CharSequence text);
}
