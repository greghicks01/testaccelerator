package au.com.tava.Core.FileIO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import au.com.tava.Core.InProgress.CoreBase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import org.joda.time.DateTime;
import org.joda.time.Period;

import org.testng.Reporter;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Greg on 27/05/2017.
 */
public class FileFunc extends CoreBase {

    /**
     *
     * @param file
     * @param newExtension
     * @return
     */
    public static File changeFileNameExtension(File file, String newExtension){
        String oldNameWithoutExtension = FilenameUtils.removeExtension(file.toString());
        String newNameExtension = FilenameUtils.getExtension(newExtension);
        return new File ( oldNameWithoutExtension + FilenameUtils.EXTENSION_SEPARATOR + newNameExtension );
    }

    /**
     *
     * @param file
     * @param timeoutPeriod
     * @return
     * @throws InterruptedException
     */
    public static boolean waitForFileToBeConsumed(File file, Period timeoutPeriod) throws InterruptedException {

        DateTime start = new DateTime();
        do{
            Thread.sleep(1000);
        }while(Files.exists(file.toPath()) && start.plus(timeoutPeriod).isAfterNow());

        return !Files.exists(file.toPath());
    }

    /**
     *
     * @param fullpath
     * @param pattern
     * @param value
     * @param timeoutPeriod
     * @return
     * @throws FileNotFoundException
     */
    public static File getFileNameLikePatternAndContainsValue(Path fullpath, String pattern, String value, Period timeoutPeriod) throws FileNotFoundException {

        DateTime start = new DateTime();
        File file = null;
        do {
            try {
                file = getFileNameLikePatternAndContainsValue(fullpath, pattern, value);
            }catch(FileNotFoundException e){} //swallow error for time out
        }while(file == null && start.plus(timeoutPeriod).isAfterNow());

        return file;
    }

    /**
     *
     * @param fullpath
     * @param pattern
     * @param value
     * @return
     * @throws FileNotFoundException
     */
    public static File getFileNameLikePatternAndContainsValue(Path fullpath, String pattern, String value) throws FileNotFoundException {

        List<File> files = getAllFilesLikePattern(fullpath, pattern);

        for(File file : files){
            if(scanTextFileForValue(file, value))
                return file;
        }
        return null;
    }

    /**
     * URL:  http://javapapers.com/java/glob-with-java-nio/
     *       https://stackoverflow.com/questions/30088245/java-7-nio-list-directory-with-wildcard
     *       https://stackoverflow.com/questions/794381/how-to-find-files-that-match-a-wildcard-string-in-java
     * Date: 27May17
     * @param fullpath
     * @param pattern
     * @return
     */
    public static List<File> getAllFilesLikePattern(Path fullpath, String pattern){

        FileFilter fileFilter = new WildcardFileFilter(pattern);
        return Arrays.asList(fullpath.toFile().listFiles(fileFilter)); //TODO may cause null pointer - improve handing of that case
    }

    /**
     *
     * @param fullPath
     * @param fileName
     * @param period
     * @return
     * @throws InterruptedException
     */
    public static File getFileNamed(Path fullPath, String fileName, Period period) throws InterruptedException {
        DateTime start = new DateTime();
        File file;
        final int sleepInterval = 1000; //milliseconds

        do{
            Thread.sleep( sleepInterval );
            file = getFileNamed( fullPath, fileName );
        }while ( file == null && start.plus(period).isAfterNow() );

        return file;
    }

    /**
     *
     * @param inFile
     * @param period
     * @return
     */
    public static File getFileNamed(File inFile, Period period){
        DateTime start = new DateTime();
        File file;
        do{
            file = getFileNamed(inFile);
        }while(file == null && start.plus(period).isAfterNow());
        return file;
    }

    /**
     *
     * @param fullPath
     * @param fileName
     * @return
     */
    public static File getFileNamed(Path fullPath, String fileName){

        Path path = Paths.get(fullPath.toString(), fileName.toString());
        return getFileNamed( path.toFile() );
    }

    /**
     *
     * @param file
     * @return
     */
    public static File getFileNamed (File file){
        if(Files.exists(file.toPath())){
            return file;
        }
        return null;
    }

    /**
     * URL:  https://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html
     * Date: 27May17
     * @param file
     * @param value
     * @return
     * @throws FileNotFoundException
     */
    public static boolean scanTextFileForValue(File file, String value) throws FileNotFoundException {

        if(!Files.exists(file.toPath())) return false;

        Scanner scanner = new Scanner(file);
        boolean found = false;
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            if (line.contains(value)) {
                found = true;
                break;
            }
        }
        scanner.close();
        return found;
    }

    /**
     *
     *  URL:  https://stackoverflow.com/questions/1146153/copying-files-from-one-directory-to-another-in-java
     *        http://www.journaldev.com/861/java-copy-file
     *        https://docs.oracle.com/javase/tutorial/essential/io/copy.html
     *
     * @param source
     * @param destination
     * @return
     * @throws IOException
     */
    public static boolean copyFile(File source, File destination) throws IOException {
        Path response = Files.copy(source.toPath(), destination.toPath(), REPLACE_EXISTING);
        Reporter.log("File copy succeeded. Copied " + source.toString() + " to " + response.toString() ,true);
        return true;
    }

    /**
     *
     * @param target
     * @return
     * @throws IOException
     */
    public static boolean deleteFile(File target) throws IOException {
        return Files.deleteIfExists(target.toPath());
    }

    /**
     *
     * @param folder
     * @return
     */
    public static boolean createFolderIfNotExist(Path folder) throws IOException {
        if (Files.notExists(folder)) {
            FileUtils.forceMkdir(folder.toFile());
            return true;
        }
        return false;
    }

}
