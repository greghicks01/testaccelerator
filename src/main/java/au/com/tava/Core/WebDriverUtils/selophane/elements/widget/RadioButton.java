package au.com.tava.Core.WebDriverUtils.selophane.elements.widget;

import au.com.tava.Core.WebDriverUtils.selophane.elements.base.Element;
import au.com.tava.Core.WebDriverUtils.selophane.elements.base.ImplementedBy;

@ImplementedBy( RadioButtonImpl.class )
public interface RadioButton extends Element {

    String getCurrentValue();

    void setNewValue( String newValue );

}
