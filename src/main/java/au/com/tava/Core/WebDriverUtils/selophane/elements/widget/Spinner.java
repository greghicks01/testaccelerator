package au.com.tava.Core.WebDriverUtils.selophane.elements.widget;

import au.com.tava.Core.WebDriverUtils.selophane.elements.base.Element;
import au.com.tava.Core.WebDriverUtils.selophane.elements.base.ImplementedBy;

@ImplementedBy( SpinnerImpl.class )
public interface Spinner extends Element {

    Boolean isVisible();

}
