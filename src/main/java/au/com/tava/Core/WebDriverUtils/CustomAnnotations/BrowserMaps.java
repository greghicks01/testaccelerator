package au.com.tava.Core.WebDriverUtils.CustomAnnotations;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface BrowserMaps {
    BrowserMap[] value();
}
