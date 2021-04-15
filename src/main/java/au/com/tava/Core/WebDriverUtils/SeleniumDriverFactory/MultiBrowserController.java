package au.com.tava.Core.WebDriverUtils.SeleniumDriverFactory;

import au.com.tava.Core.CustomExceptions.NoSuchKeyException;
import org.openqa.selenium.WebDriver;

import java.util.*;

/**
 * Created by Greg on 9/07/2017.
 */
public class MultiBrowserController {

    private static HashMap<String, WebDriver> driverSessions = new HashMap<>();

    private static  String focusedSessionName ;

    public static WebDriver addSessionDriver(String sessionName, String target) throws Exception {

        if(!driverSessions.containsKey(sessionName)) {
            SeleniumDriverFactory session = new SeleniumDriverFactory( target ,"", "", "", "" , "", "");
            driverSessions.putIfAbsent(sessionName, session.getNewDriver());
        }
        return getSessionDriver(sessionName);
    }

    public static WebDriver getSessionDriver(String sessionName) throws InstantiationException, IllegalAccessException {
        return driverSessions.get( sessionName );
    }

    public static List<String> getSessionNames( ){
        return new ArrayList<>( driverSessions.keySet() );
    }

    public static void quitSession(final String sessionName) throws IllegalAccessException, InstantiationException {
        if( driverSessions.containsKey( sessionName ) ) {
            getSessionDriver( sessionName ).quit();
            driverSessions.remove( sessionName );
            return;
        }
        throw new NoSuchKeyException( sessionName ) ;
    }

    public static void quitAllSessions() throws InstantiationException, IllegalAccessException {

        List<String> sessionNames = getSessionNames();
        for(String sessionName : sessionNames ){
            quitSession(sessionName);
        }
    }

    public static void setFocusedDriverTo( String namedSession ){
        focusedSessionName = namedSession ;
    }

    public static WebDriver getFocusedDriver(){
        return driverSessions.get( focusedSessionName ) ;
    }
}
