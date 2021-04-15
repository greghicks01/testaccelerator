package au.com.tava.Core.InProgress;

import au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory.MultiBrowserController;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greg on 13/07/2017.
 */
public class MultiBrowserTest {

    MultiBrowserController multiBrowserController;
    List<String> sessionNames;

    @BeforeTest
    public void BeforeTest(){
        multiBrowserController = new MultiBrowserController();
        sessionNames = new ArrayList<>();
    }

    @Test
    public void localTest() throws Exception {
        List<String> browsers = new ArrayList<String>() { { add( "chrome" ); add( "Internet Explorer" ); } };

        for( String browser : browsers ){
            multiBrowserController.addSessionDriver( browser , browser );
        }

        sessionNames.addAll(multiBrowserController.getSessionNames());
        Reporter.log( sessionNames.toString(), true);

        multiBrowserController.quitSession(sessionNames.get(0));
        sessionNames.addAll(multiBrowserController.getSessionNames());
        Reporter.log( sessionNames.toString(), true);

    }

    @AfterTest
    public void AfterTest() throws IllegalAccessException, InstantiationException {
        multiBrowserController.quitAllSessions();
        sessionNames.addAll(multiBrowserController.getSessionNames());
        Reporter.log( sessionNames.toString(), true);
    }
}
