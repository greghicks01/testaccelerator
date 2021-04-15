package au.com.tava.Core.selophane.widget;

import au.com.tava.Core.WebDriverUtils.selophane.elements.base.Element;
import au.com.tava.Core.WebDriverUtils.selophane.elements.base.ImplementedBy;

@ImplementedBy(ButtonImpl.class)
public interface Button extends Element {

    String getLabel();

    void click();

    boolean waitWhileBusy();

}
