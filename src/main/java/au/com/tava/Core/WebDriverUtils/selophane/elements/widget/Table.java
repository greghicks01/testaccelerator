package au.com.tava.Core.WebDriverUtils.selophane.elements.widget;

import au.com.tava.Core.WebDriverUtils.selophane.elements.base.Element;
import au.com.tava.Core.WebDriverUtils.selophane.elements.base.ImplementedBy;
import org.openqa.selenium.WebElement;

/**
 * Table functionality.
 */
@ImplementedBy( TableImpl.class )
public interface Table extends Element {

    /**
     * Gets the number of rows in the table
     * @return int equal to the number of rows in the table
     */
    int getRowCount();

    /**
     * Gets the number of columns in the table
     * @return int equal to the number of rows in the table
     */
    int getColumnCount();

    /**
     * Gets the WebElement of the cell at the specified index
     * @param rowIdx The zero based index of the row
     * @param colIdx The zero based index of the column
     * @return the WebElement of the cell at the specified index
     */
    WebElement getCellAtIndex(int rowIdx, int colIdx);
}

class TableValueNotFoundInColumnException extends RuntimeException {

    public TableValueNotFoundInColumnException( final String value, final String Column ){
        final String error = String
                .format("Could not find value %s in column: %s",
                        value, Column);
        throw new RuntimeException( error ) ;
    }

    public TableValueNotFoundInColumnException( final int rowIdx , final int colIdx ){
        final String error = String
                .format("Could not find cell at row: %s column: %s",
                        rowIdx, colIdx);
        throw new RuntimeException( error ) ;
    }

    public TableValueNotFoundInColumnException( final String error ){
        throw new RuntimeException( error ) ;
    }
}
