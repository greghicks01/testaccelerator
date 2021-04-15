package testNG.Example.Pages;

import org.testng.Reporter;

/**
 * Created by JKV on 20/05/2017.
 */
public class LoginPage extends PageBase {

    public void login(String a, String b) {
        Reporter.log(a,true);
        Reporter.log(b, true);
    }

    @Override
    public String readPageTitle() {
        return "Login Page";
    }
}
