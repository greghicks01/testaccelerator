package au.com.tava.Core.WebDriverUtils.CustomAnnotations;

import java.lang.annotation.*;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD , ElementType.TYPE} )
@Repeatable( BrowserMaps.class )

public @interface BrowserMap {
    TriggerLevel trigger() default TriggerLevel.METHOD;
    String browser() default "";
    WebInterfaces environment() ;
    boolean disabled() default false;
}
