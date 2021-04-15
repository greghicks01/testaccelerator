package au.com.tava.Core;

import au.com.tava.Core.FileIO.XL;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.reflect.Method;

import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static au.com.tava.Core.Globals.FQPath;
import static au.com.tava.Core.Globals.projectTestDataFolder;
import static org.testng.AssertJUnit.fail;

/**
 * Created by Greg on 29/01/2017.
 */
public class XLTest extends TestBase{

    final String path = Paths.get(projectTestDataFolder, "JKV", "Core").toString();
    String testFile = Paths.get(path, "empty.xlsx").toString();


    @Test(groups = "JKV.Core.Test.XL")
    public void xlNoParamCtor() throws IOException {

        XL xl = new XL();
        Assert.assertNotNull(xl);
        Assert.assertTrue(xl.hasHeaderRow);
    }

    @Test(groups = "JKV.Core.Test.XL")
    public void xlFileParamCtor() throws IOException {

        XL xl = new XL(testFile);
        Assert.assertNotNull(xl);
        Assert.assertTrue(xl.hasHeaderRow);
    }

    @Test(groups = "JKV.Core.Test.XL")
    public void xlFileParamXlsmCtor() throws IOException, IllegalAccessException {

        XL xl = new XL(Paths.get(Globals.projectTestDataFolder, "testmacro.xlsm").toString());
        Assert.assertNotNull(xl);
        Assert.assertTrue(xl.hasHeaderRow);
        xl.activateSheetByName("Sheet1");
    }


    @Test(groups = "JKV.Core.Test.XL")
    public void xlFileSheetCtor() throws IOException, IllegalAccessException {

        XL xl = new XL(testFile, "Sheet1");
        Assert.assertNotNull(xl);
        Assert.assertTrue(xl.hasHeaderRow);
    }

    @Test(groups = "JKV.Core.Test.XL")
    public void xlFileSheetHeaderCtor() throws IOException, IllegalAccessException {

        XL xl = new XL(testFile, "Sheet1", false);
        Assert.assertNotNull(xl);
        Assert.assertFalse(xl.hasHeaderRow);
    }

    @Test(groups = "JKV.Core.Test.XL")
    public void xlFileMethodCtor() throws IOException, IllegalAccessException {

        Method method = new Object() {
        }.getClass().getEnclosingMethod();

        XL xl = new XL(testFile, method);
        Assert.assertNotNull(xl);
        Assert.assertTrue(xl.hasHeaderRow);

    }

    @Test(groups = {"JKV.Core.Test.XL"},
            expectedExceptions = {NullPointerException.class})
    public void xlFileMissingMethodCtor() throws IOException, IllegalAccessException {

        Method method = new Object() {
        }.getClass().getEnclosingMethod();
        XL xl = new XL(testFile, method);
        fail();
    }

    @Test(groups = {"JKV.Core.Test.XL"},
            expectedExceptions = {NullPointerException.class})
    public void xlFileMissingSheetCtor() throws IOException, IllegalAccessException {

        XL xl = new XL(testFile, "Sheet99");
        fail();
    }

    @Test(groups = {"JKV.Core.Test.XL"})
    public void openCorrectFile() throws IOException {

        XL xl = new XL();

        //expected file types
        xl.openFile(FQPath(path, "empty.xlsx"));

        xl.openFile(FQPath(path, "empty.xls"));
    }

    @Test(groups = {"JKV.Core.Test.XL"},
            expectedExceptions = IllegalArgumentException.class)
    public void openBadFile() throws IOException {

        XL xl = new XL();
        // wrong file type\
        xl.openFile(FQPath(path, "test.txt"));
        fail();
    }

    @Test(groups = {"JKV.Core.Test.XL"},
            expectedExceptions = FileNotFoundException.class)
    public void openMissingFile() throws IOException {

        XL xl = new XL();
        // non-existent file
        xl.openFile(FQPath(path, "filenotfound.xls"));
        fail();
    }

    @Test(groups = {"JKV.Core.Test.XL"})
    public void testNominatedRowRetrieval() throws IOException, IllegalAccessException {

        XL xl = new XL();

        xl.openFile(FQPath(path, "testdata.xlsx"));
        xl.activateSheetByName("Sheet1");

        Object o[] = null;
        o = xl.getRowData(2);
        for (Object i : o) {
            System.out.println(i);
        }
    }

    @Test(groups = "JKV.Core.Test.XL")
    public void testSheetData() throws IOException, IllegalAccessException {

        XL xl = new XL(FQPath(path, "testdata.xlsx"), "Sheet1");

        Object[][] objAll = null;

        objAll = xl.getSheetData();

        for (Object[] obj : objAll) {
            for (Object o : obj) {
                System.out.println(o);
            }
        }

    }

    @Test(groups = "JKV.Core.Test.XL")
    public void testListOfRowsRead() throws IOException, IllegalAccessException {

        XL xl = new XL(FQPath(path, "testdata.xlsx"), "Sheet1");

        Object[][] objAll = null;

        /**
         * Note this retreives the rows with the assumption of header (0)
         */
        List<Integer> rows = new ArrayList<Integer>() {{
            add(1);
            add(3);
        }};

        objAll = xl.getSheetData(rows);

        //List<String>
        for (Object[] obj : objAll) {
            for (Object o : obj) {
                System.out.println(o);
            }
        }

        xl = null;

    }

    @DataProvider
    public Object[][] testData(Method method) throws IOException, IllegalAccessException {

        XL xl = new XL(FQPath(path, "testdata.xlsx"), method);
        return xl.getSheetData();
    }

    @Test(dataProvider = "testData",groups = "JKV.Core.Test.XL")
    public void dataDriving(String colA, String colB, String colC, List<String> data, HashMap<String, String> hash) {

        Reporter.log(colA, true);
        Reporter.log(colB, true);
        Reporter.log(colC, true);
        Reporter.log(data.toString(), true);
        Reporter.log(hash.toString(), true);

    }

}
