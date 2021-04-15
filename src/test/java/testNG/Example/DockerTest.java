package testNG.Example;

import org.junit.Test;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.text.MessageFormat;

public class DockerTest {
    String browserName = "chrome";
    String nodePrefix = "standalone-";
    String nodeSuffix = "-debug";
    String imageNameJoin = "-";
    String buildName = ":3.10.0-argon"; //"3.8.1-bohrium";

    String dockerCom = "docker run -d -p 4444:4444 -p 5900:5900 -v /dev/shm:/dev/shm selenium/{0}{1}{2}{3}";

    final String dockerlaunch = MessageFormat.format(dockerCom, nodePrefix, browserName, nodeSuffix, buildName);

    @BeforeTest
    public void docker() throws IOException {

    Process myProcess = Runtime.getRuntime().exec( dockerlaunch );
    }

    public DockerTest() throws IOException {
    }

    @Test
    public void test() {
    }
}
