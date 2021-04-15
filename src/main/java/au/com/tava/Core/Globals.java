package au.com.tava.Core;

import java.nio.file.Paths;

/**
 * Created by Greg on 7/04/2017.
 */
public class Globals {

    public static final String dataFileExtension = ".xlsx";  //System.getProperty("");
    public static final String reportFileExtension = ".html";
    public static final String imageFileExtension = ".png";
    public static final String XMLFileExtension = ".xml";
    public static final String imageDateTimeFormat = "YYYYMMdd'T'HHmmssSSSS";
    public static final String datetimeFormat = "yyyyMMdd'T'HHmmsssss";

    // Project Paths
    public static final String projectRootFolder = Paths.get(System.getProperty("user.dir")).toString();
    public static final String projectSrcFolder = Paths.get(projectRootFolder, "src").toString();
    public static final String projectMainFolder = Paths.get(projectSrcFolder, "main", "java").toString();
    public static final String projectTestFolder = Paths.get(projectSrcFolder, "test", "java").toString();
    public static final String projectReportFolder = Paths.get(projectTestFolder, "Report").toString();
    public static final String projectReportImageFolder = Paths.get(projectReportFolder, "Images").toString();

    public static final String projectTestDataFolder = Paths.get(projectRootFolder, "TestData").toString();
    public static final String projectTestDataX509Folder = Paths.get(projectTestDataFolder, "X509Certs").toString();
    public static final String dataFileName = Paths.get(projectTestDataFolder, "Sample" + dataFileExtension).toString();
    public static final String projectLogFolder = Paths.get(projectTestFolder, "Log").toString();
    public static int timeout = 30; //default at 30, can be altered in code

    public static final String FQPath(String folder, String filename) {
        //use reflection to get the FQP out
        return Paths.get(folder, filename).toString();
    }

}
