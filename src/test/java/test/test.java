package test;

import au.com.tava.Core.WebDriverUtils.CustomAnnotations.BrowserMap;
import au.com.tava.Core.WebDriverUtils.CustomAnnotations.WebInterfaces;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class test extends basetest {

    @Test
    @BrowserMap( browser = "test1" , environment = WebInterfaces.env1 )
    public void test1(){
        Reporter.log( "Test Thread test1 id" + Thread.currentThread().getId() , true ) ;
    }

}
