package salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Base Salesforce page, has code for about 90% of the pages used by salesforce
 *
 * a) Common web element
 * b) recursive iframe navigation
 */
public class SalesforceBase  {

    // Refreshing in and out of a frame breaks the @FindBy model
    // this code base uses code techniques to manage this

    // Object Repo
    final By ByLabel = By.className( "label" ) ;
    final By ByDataElement = By.className( "datacol" ) ;

    // Class fields
    protected WebDriver driver ;
    protected String rootContext;
    // Common objects
    List<String> labels = new ArrayList<>(); // All labels on a form
    List<WebElement> dataElements; // usually double click to edit
    // Exception to this is a double click that opens a dialog

    public SalesforceBase( WebDriver driver ){
        this.driver = driver ;
    }

    /**
     * Use this for most pages with a "table"
     * override for other page layouts
     */
    public void refresh(){
        // finds the elements then extracts them as a List<String>
        driver.findElements( ByLabel )
        .stream()
        .map( WebElement::getText )
        .forEach( labels::add );

        dataElements = driver.findElements( ByDataElement ) ;
    }

    /**
     * sets the element data to a new value (does not click the save button)
     * This may benefit from calling a series of smaller functions from the switch statements
     * @param label
     * @param newValue
     */
    public void setDataElement( String label, String newValue ){

        // The data element is at labels index in the lists
        WebElement clickableElement = dataElements.get( labels.indexOf( label ) );

        // open the element nominated by the label
        Actions action = new Actions( driver ) ;
        action.doubleClick( clickableElement ).perform();

        // with a few exceptions most are a single interactable web element
        WebElement editableElement = driver.findElement( By.xpath( "first-child::node()")) ;

        // this is a catch all switch - adjust to the types you find in SF
        switch ( editableElement.getTagName()){

            case "textarea":
                editableElement.clear(); // clear before setting
                editableElement.sendKeys();
                return ;

            case "input":
                switch( editableElement.getAttribute( "type" ) ) {
                    case "text":
                    case "password":
                        editableElement.clear();
                        editableElement.sendKeys( newValue );
                        return ;

                    case "submit":
                        editableElement.click();
                        return ;

                    case "radio":
                        // special case
                        return ;

                    case "checkbox":
                        if ( ( ! editableElement.isSelected() &&   Boolean.parseBoolean( newValue ) ) ||
                             (   editableElement.isSelected() && ! Boolean.parseBoolean( newValue ) ) ) {
                            editableElement.click() ;
                        }
                        return ;

                    default:
                        return ;

                }

            case "select":
                Select select = new Select( editableElement ) ;
                select.deselectByVisibleText( newValue );
                return ;

            case "button":
                editableElement.click() ; // this looks a little illogical in here
                return ;

            default:
                return ;
        }

    }

    public String getElementValue( String label ){

        // The data element is at labels index in the lists
        WebElement dataElement = dataElements.get( labels.indexOf( label ) );

        return dataElement.getText() ;
    }

    /**
     * Version of frame searhcing by frame id, could be modified to use alternate identifier
     * (maybe we can make this injectable per project in the future)
     * @param name
     * @return
     */
    public WebElement findFrameWithId( String name ){

        driver.switchTo().window( rootContext ) ; //always switch to the root context then find the element

        List<WebElement> rootFrames = driver.findElements( By.tagName( "iframe" ) ) ;

        for ( WebElement rootFrame : rootFrames ){

            WebElement frameRef = findFrameWithId( rootFrame, name) ;
            if ( ! isNull( frameRef ) ){
                driver.switchTo().frame( frameRef ) ;
                return frameRef ;
            }
        }
        return null ;
    }

    /**
     * This logic may need some debugging/tuning on saleforce
     * @param frame
     * @param name
     * @return
     */
    private WebElement findFrameWithId( WebElement frame , String name ){

        if ( frame.getAttribute("id").equals( name ) ){
            return frame ;
        }

        driver.switchTo().frame( frame ) ;

        List<WebElement> frames = driver.findElements( By.tagName( "iframe" ) ) ;

        for( WebElement lowerFrame : frames){
            WebElement e = findFrameWithId( lowerFrame , name ) ;
            if ( ! isNull( e ) ){
                return e ;
            }
        }
        return null ;
    }
}
