package test;

import au.com.tava.Core.WebDriverUtils.CustomAnnotations.BrowserMap;
import au.com.tava.Core.WebDriverUtils.CustomAnnotations.WebInterfaces;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class test1 extends  basetest {

    @Test
    @BrowserMap( browser = "test2" ,environment = WebInterfaces.env2 )
    public void test2(){
        Reporter.log( "Test Thread test2 id" + Thread.currentThread().getId() , true );
    }
}
