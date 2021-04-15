package au.com.tava.Core;

import au.com.tava.Core.FileIO.FileFunc;
import au.com.tava.Core.FileIO.XL;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greg on 27/05/2017.
 */
public class EncodePasswords {

    @Test ( enabled = false )
    public void encodeAllXLDataSources() throws IOException, IllegalAccessException {
        XL xl = new XL();
        FileFunc filefunc = new FileFunc();
        boolean decryption = true;

        for(File file : filefunc.getAllFilesLikePattern(Paths.get(Globals.projectTestDataFolder), "*" + Globals.dataFileExtension)){
            xl.openFile(file.toString());
            List<Sheet> sheets = xl.getSheets();
            for (Sheet sheet : sheets) {

                List<String> headers = new ArrayList<>();
                try {
                    for (int width = 0; width < sheet.getRow(0).getLastCellNum(); width++)
                        headers.add(sheet.getRow(0).getCell(width).getStringCellValue());
                } catch (Exception e) {
                }

                List<Integer> pwordHeaders = new ArrayList<>();
                int headerMatch = 0;
                for (String header : headers) {
                    if (header.toLowerCase().equals("password"))
                        pwordHeaders.add(headerMatch);
                    headerMatch++;
                }

                Encryptor encryptor = new Encryptor();
                for (int pwordHeader : pwordHeaders)
                    for (int columnCount = 1; columnCount < sheet.getPhysicalNumberOfRows(); columnCount++) {
                        Cell temp = sheet.getRow(columnCount).getCell(pwordHeader);
                        String value = decryption ?
                                encryptor.decode(temp.getStringCellValue()) :
                                encryptor.encode(temp.getStringCellValue());
                        temp.setCellValue(value);
                    }
            }
            xl.writeToFile();
        }
    }
}
