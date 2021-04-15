package au.com.tava.Core;

import au.com.tava.Core.WebDriverUtils.CustomAnnotations.BrowserMap;
import au.com.tava.Core.WebDriverUtils.CustomAnnotations.WebInterfaces;
import au.com.tava.Core.FileIO.FileFunc;
import au.com.tava.Core.runner.TestNGListeners;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.Period;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import static au.com.tava.Core.Globals.projectTestDataFolder;

@Listeners(TestNGListeners.class)

/**
 * Created by Greg on 30/05/2017.
 */
public class FileTest extends TestBase{

    private final Path testFolder = Paths.get(projectTestDataFolder, "JKV", "Core");

    @Test
    @BrowserMap( environment = WebInterfaces.env1 )
    public void scanFileForValue() throws IOException, InterruptedException {
        File source = new File(Paths.get(testFolder.toString(), "erlide.org").toString());
        File destination = FileFunc.changeFileNameExtension(source, "*.txt");
        Assert.assertNull(FileFunc.getFileNamed(destination));

        FileFunc.copyFile(source,destination);
        Assert.assertNotNull(FileFunc.getFileNamed(destination,new Period().withSeconds(5)));
        Assert.assertNotNull(FileFunc.getFileNamed( Paths.get(FilenameUtils.getFullPath(destination.toString())),FilenameUtils.getName(destination.toString()),new Period().withSeconds(5)));

        Assert.assertFalse(FileFunc.waitForFileToBeConsumed(destination,new Period().withSeconds(3)));
        Assert.assertTrue(FileFunc.scanTextFileForValue(destination,"HostnameUtils"),"Failed to locate value in file");
        Assert.assertNotNull(FileFunc.getFileNameLikePatternAndContainsValue(testFolder, "*.txt","HostnameUtils" ));
        Assert.assertNotNull(FileFunc.getFileNameLikePatternAndContainsValue(testFolder, "*.txt","HostnameUtils" , new Period().withSeconds(5) ) );

        Assert.assertTrue(FileFunc.deleteFile(destination));

        Assert.assertTrue(FileFunc.waitForFileToBeConsumed(destination,new Period().withSeconds(3)));
        Assert.assertFalse(FileFunc.scanTextFileForValue(destination," HostnameUtils"),"Failed to locate value in file");
        Assert.assertNull(FileFunc.getFileNameLikePatternAndContainsValue(testFolder, "*.txt","HostnameUtils" ));
        Assert.assertNull(FileFunc.getFileNameLikePatternAndContainsValue(testFolder, "*.txt","HostnameUtils" , new Period().withSeconds(5) ) );

    }
}
