package au.com.tava.Core;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Greg on 7/05/2017.
 * <p>
 * The virtual page driver is responsible for managing errors thrown by this class.
 */
public class WebTables {

    // class instance fields
    private List<String> headers;
    private List<WebElement> tableCells;
    private int currentRow;

    //class methods

    /**
     * @param h
     */
    public void setHeaders(List<String> h) {
        headers = h;
        currentRow = -1; //reset
    }

    /**
     * @param c
     */
    public void setTableCells(List<WebElement> c) {
        tableCells = c;
    }

    /**
     * @param columnName
     * @param value
     * @param occurence
     * @return
     */
    public boolean searchTableColumnForValue(String columnName, String value, int occurence) {

        boolean found = false;
        int count = 0;
        do {
            found = searchTableColumnForValue(columnName, value);
            count++;
        } while (count < occurence);
        return found;
    }

    /**
     * @param columnName
     * @param value
     * @return
     */
    public boolean searchTableColumnForValue(String columnName, String value) {

        for (int row = currentRow + 1; row < headers.size(); row++) {
            currentRow = row;
            if (getValueInCurrentRow(columnName).equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param columnName
     * @return
     */
    public WebElement getCellInCurrentRow(String columnName) {
        return tableCells.get(currentRow * headers.size() + headers.indexOf(columnName));
    }

    /**
     * @param columnName
     * @return
     */
    public String getValueInCurrentRow(String columnName) {
        return getCellInCurrentRow(columnName).getText();
    }

    /**
     *
     * @param columnName
     * @param attribute
     * @return
     */
    public String getValueInCurrentRow(String columnName, String attribute) {
        return getCellInCurrentRow( columnName ).getAttribute( attribute );
    }

    private int cellCoord = -1;

    public void resetRow() {
        currentRow = -1;
    }

    /**
     * @param columnHeaderName
     * @param value
     * @param occurance
     * @return
     */
    public int scanTableForValueInColumn( String columnHeaderName, String value, int occurance ) {

        int foundOccurance = 0,
                rowFound = -1;
        resetRow();

        do {
            foundOccurance++;
            rowFound = scanTableForValueInColumn(columnHeaderName, value);
        } while (rowFound != -1 && foundOccurance != occurance);

        if (rowFound == -1)
            throw new NoSuchElementException( "Unable to locate occurance"
                    + occurance + " of value " + value + "in column headed " + columnHeaderName);

        return rowFound;
    }

    /**
     * scans a 'table' column for a matching value
     *
     * @param columnHeaderName
     * @param value
     * @return
     */
    protected int scanTableForValueInColumn( String columnHeaderName, String value ) {

        for ( int row = currentRow + 1; row < tableCells.size() / headers.size(); row++ ) {
            cellCoord = row * headers.size() + headers.indexOf(columnHeaderName);
            if (tableCells.get(cellCoord).getText().equalsIgnoreCase(value)) {
                currentRow = row;
                return cellCoord;
            }
        }
        throw new NoSuchElementException("Cannot find value of " + value + "in column " + columnHeaderName);
    }

    /**
     * gets the String value in the tables current row from Column
     *
     * @param headerName
     * @return
     */
    protected String getCurrentRowValueInColumn( String headerName ) {
        return tableCells.get(currentRow * headers.size() + headers.indexOf(headerName)).getText().trim();
    }
}
