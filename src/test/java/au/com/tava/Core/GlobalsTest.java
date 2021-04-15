package au.com.tava.Core;

import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.lang.reflect.Field;

/**
 * Created by Greg on 7/04/2017.
 * <p>
 * Uses reflection to printout the variables and project paths
 */
public class GlobalsTest extends TestBase{

    Logger log = Logger.getLogger(this.getClass());

    @Test
    public void GlobalTest() throws IllegalAccessException {
        //reflection of static members
        Field[] fields = Globals.class.getFields();

        for (Field field : fields) {
            String data = "Field Name: " + field + "\r\n\t Value: " + field.get(new Globals());
            Reporter.log(data,true);
        }
    }

}
