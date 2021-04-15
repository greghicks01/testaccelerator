package testNG.Example.Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import au.com.tava.Core.WebDriverUtils.selophane.elements.widget.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JKV on 20/05/2017.
 */
public class SearchPage extends PageBase {

    @FindBy( id = "container" )
    private Button container ;

    public void searchForValue(String value) {
        List<TextInput> texts = container.findElements( By.className( "text" ) ).stream()
                .map( TextInputImpl::new ).collect( Collectors.toList() );
        for( TextInput text : texts ){
            if( text.getAttribute( "id").equals( "someidenfier")){
                text.set( "new value") ;
                return ;
            }
        }
    }

    @Override
    public String readPageTitle() {
        return "Search Page";
    }
}
