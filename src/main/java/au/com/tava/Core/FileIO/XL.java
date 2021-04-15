package au.com.tava.Core.FileIO;

import au.com.tava.Core.InProgress.CoreBase;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.testng.Reporter;

import java.io.*;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by Greg on 22/11/2016.
 *
 * URL: http://viralpatel.net/blogs/java-read-write-excel-file-apache-poi/
 * Date: 10/10/15
 */
public class XL extends CoreBase{

    private final String pairDelimiter = ",";
    private final int maxSplitSize = 2;
    private final String lineDelimiter = "\n";

    private String CurrentFileName;

    private Workbook workbook;
    private Sheet currentSheet;
    public boolean hasHeaderRow = true;

    public List<String> skipHeaders = new ArrayList<>();

    private Method method = null;
    private Class<?>[] parameterTypes = null;

    //=========================================================================================================

    public void setSkipHeaders(List<String> skipHeaders){
        this.skipHeaders = skipHeaders;
    }

    //=========================================================================================================

    /**
     * No parameter constructor
     */
    public XL() {
        Reporter.log("New XL ctor", true);
    }

    /**
     * @param fileName fully qualified filename
     * @throws IOException
     */
    public XL(String fileName) throws IOException {
        this();
        this.openFile(fileName);

        String log = MessageFormat.format("Opened file {0} successfully", fileName);
        Reporter.log(log, true);
    }

    /**
     * Overloaded constructor takes file and Sheet name
     *
     * @param fileName fully qualified filename
     * @param sheetName name of the sheet (or native name eg: 'Sheet1')
     * @throws IOException
     */
    public XL(String fileName, String sheetName) throws IOException, IllegalAccessException {
        this(fileName);
        this.activateSheetByName(sheetName);
    }

    /**
     * Overloaded constructor with sheet name and reflection method
     *
     * @param fileName fully qualified filename
     * @param testNgMethod method being called by TestNg
     * @throws IOException
     */
    public XL(String fileName, Method testNgMethod) throws IOException, IllegalAccessException {
        this(fileName, testNgMethod.getName());
        this.method = testNgMethod;

        parameterTypes = method.getParameterTypes();

        String log = MessageFormat.format("Used method name {0}", fileName, method.getName());
        Reporter.log(log, true);
    }

    /**
     * Overloaded constructor takes file, Sheet name and header row flag
     *
     * @param fileName fully qualified filename
     * @param sheetName name of the sheet (or native name eg: 'Sheet1')
     * @param hasHeader used to control header row logic
     * @throws IOException
     */
    public XL(String fileName, String sheetName, boolean hasHeader) throws IOException, IllegalAccessException {
        this(fileName, sheetName);
        hasHeaderRow = hasHeader;

        String log = MessageFormat.format("Opened file {0} and currentSheet {1} with header {2}", fileName, sheetName, hasHeader);
        Reporter.log(log, true);
    }

    /**
     *
     * @throws IOException
     */
    protected void finalise() throws IOException {
        workbook.close();
    }

    //=========================================================================================================

    /**
     * Opens the file manually
     *
     * @param filename fully qualified filename
     * @return XL object
     * @throws IOException
     */
    public XL openFile(String filename) throws IOException {

        //Get the workbook instance for XLS file - file ext is required for two types
        workbook = getWorkBook(filename);
        Reporter.log("openFile succeeded opening " + filename, true);
        return this;
    }

    /**
     * @param sheetname fully qualified filename
     */
    public XL activateSheetByName(String sheetname) throws IllegalAccessException {

        currentSheet = workbook.getSheet(sheetname);
        if (currentSheet == null)
            throw new NullPointerException("Sheet named " + sheetname + " does not exist in this spreadsheet");

        Reporter.log("Sheet named " + sheetname + " selected successfully", true);
        return this;
    }

    /**
     * get all the sheets in the workbook
     * @return
     */
    public List<Sheet> getSheets() {

        List<Sheet> sheets = new ArrayList<>();
        for (int i=0; i<workbook.getNumberOfSheets(); i++) {
            sheets.add( workbook.getSheetAt(i) );
        }
        return sheets;
    }

    //=========================================================================================================

    /**
     *
     * @param value the cells value as an object
     * @param type parameter type from the method
     * @return
     */
    private Object convertToType(Object value, Class<?> type) {

        String typeName = type.getSimpleName();

        switch (typeName) {
            case "List":
                return ListTransform(value);

            case "HashMap":
            case "LinkedHashMap":
                List<String> temp = ListTransform(value);
                return HashMapTransform(temp);

            case "int":
                return (int) (double) value;

            case "String":
                return String.valueOf(value);

            default:
                return value;
        }
    }

    /**
     * Transform a single string into a list of string
     * @param value
     * @return
     */
    private List<String> ListTransform(Object value) {

        if (value.toString().contains(lineDelimiter))
            return Arrays.asList(value.toString().split(lineDelimiter));

        List<String> lines = new ArrayList<>();
        if (!value.toString().equals(""))
            lines.add(value.toString());

        return lines;
    }

    /**
     * Converts a list to hash map
     * @param lines List of String to convert into map
     * @return
     */
    private HashMap<Object, Object> HashMapTransform(List<String> lines) {

        HashMap<Object, Object> keypair = new HashMap<>();

        for (String line : lines) {
            if (line.contains(pairDelimiter)) {
                String[] intermediate = line.split(pairDelimiter, maxSplitSize);
                keypair.put(intermediate[0], intermediate[1]);
            }
        }
        return keypair;
    }

    //=========================================================================================================

    /**
     * retrieve the cell data with the right format
     * @param cell
     * @return
     */
    private Object getCellData(Cell cell) {

        if (cell == null)
            return "";
        else
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    return cell.getStringCellValue();

                case BOOLEAN:
                    return cell.getBooleanCellValue();

                case NUMERIC:
                    return cell.getNumericCellValue();

                //case _NONE:
                //    return cell.getDateCellValue();

                default:
                    return "";
            }
    }

    /**
     * retrieve one row of data on the current sheet
     *
     * Handles empty cells, sheet width exceeding parameter count
     * and conversion of data to match the method parameter
     *
     * @return
     */
    public Object[] getRowData(int currentRowIndex) {

        int width;
        try {
            width = currentSheet.getRow(0).getLastCellNum();
        }catch(Exception e){
            throw new IllegalStateException("The current sheet referenced in " + currentSheet.getSheetName() + " is empty and is not usable.");
        }

        List<Object> rows = new ArrayList<>();
        Cell cell;

        for (int currentCell = 0; currentCell < width; currentCell++) {

            // empty cell catch
            try {
                cell = currentSheet.getRow(currentRowIndex).getCell(currentCell);
            } catch (Exception e) {
                cell = null;
            }

            // cell data retrieval
            Object value = getCellData(cell);

            // conversion with width catch
            if (parameterTypes != null && currentCell < parameterTypes.length)
                value = convertToType(value, parameterTypes[currentCell]);

            // final value added
            rows.add(value);
        }

        return rows.toArray();
    }

    //=========================================================================================================

    /**
     * remove the extra columns by using the header names
     *
     * @param originalRows the rows of the original data
     * @return
     */
    private Object[][] removeSkippedHeaderColumns(List<Object[]> originalRows){

        if( ! hasHeaderRow )
            return originalRows.toArray(new Object[originalRows.size()][]);

        List<Object> temp = new ArrayList<>();
        List<Integer> headerIndices = convertHeadersToReverseSortIndices();

        for( Object[] originalRow : originalRows ){

            List<Object> copiedRow = new ArrayList<>(Arrays.asList(originalRow));

            for(int headerIndice : headerIndices ){
                copiedRow.remove(headerIndice);
            }

            temp.add(copiedRow.toArray(new Object[copiedRow.size()]));
        }
            return temp.toArray(new Object[temp.size()][]);
    }

    /**
     * Sets up a reversed sort order of the skipped header columns
     * @return
     */
    private List<Integer> convertHeadersToReverseSortIndices(){

        List<Integer> headerIndices = new ArrayList<>();

        for(Object o :  Arrays.asList(getRowData(0))){
            int result = skipHeaders.indexOf( o );
            if( result != -1)
                headerIndices.add( result );
        }
        Collections.reverse( headerIndices );
        return headerIndices;
    }

    //=========================================================================================================

    /**
     * gets all rows, skips header if has Header is true
     *
     * @return
     */
    public Object[][] getSheetData() {

        List<Object[]> rows = new ArrayList<>();

        int rowCount = currentSheet.getPhysicalNumberOfRows();

        for (int rowIdx = hasHeaderRow ? 1 : 0; rowIdx < rowCount; rowIdx++)
            rows.add(getRowData(rowIdx));

        return removeSkippedHeaderColumns( rows );

    }

    /**
     * Extracts all the rows nominated by the incoming list of "rows"
     * @param rows
     * @return
     */
    public Object[][] getSheetData(List<Integer> rows) {

        List<Object[]> rowData = new ArrayList<>();
        for (Integer row : rows) {
            rowData.add(getRowData(row));
        }

        return removeSkippedHeaderColumns( rowData );
    }

    //=========================================================================================================

    /**
     * Opens an excel workbook
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    private Workbook getWorkBook(String filename) throws java.io.IOException {
        CurrentFileName = filename;
        FileInputStream file = new FileInputStream(new File(filename));

        if (filename.endsWith(".xlsx") || filename.endsWith(".xlsm")) {
            return new XSSFWorkbook(file);

        } else if (filename.endsWith(".xls")) {
            return new HSSFWorkbook(file);
        }

        throw new IllegalArgumentException(filename + " is not an Excel file");

    }

    /**
     * Writes work book out to disc
     *
     * @throws IOException
     */
    public void writeToFile() throws IOException {
        if(CurrentFileName != null) {
            FileOutputStream outputStream = new FileOutputStream(CurrentFileName);
            workbook.write(outputStream);
        }
    }

    //=========================================================================================================
}
