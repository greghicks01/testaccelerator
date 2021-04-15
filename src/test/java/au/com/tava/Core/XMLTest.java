package au.com.tava.Core;

import au.com.tava.Core.FileIO.XML;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Paths;

import static org.testng.AssertJUnit.fail;


/**
 * Created by Greg on 29/01/2017.
 */
public class XMLTest extends TestBase{

    //URL:  https://www.aemo.com.au/Datasource/Archives/Archive132
    //Date: 13/04/17
    String xmlR34GoodSample = "<?xml version=\"1.0\"?>\r\n" +
            "<ase:aseXML xsi:schemaLocation=\"urn:aseXML:r34 http://www.nemmco.com.au/aseXML/schemas/r34/aseXML_r34.xsd \" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ase=\"urn:aseXML:r34\">\r\n" +
            "<Header>\r\n" +
            "<From description=\"Energex Retail Pty Ltd\">ENERGEX</From>\r\n" +
            "<To description=\"National Electricity Market Management Company\">NEMMCO</To>\r\n" +
            "<MessageID>ENERGEX-MSG-11234569</MessageID>\r\n" +
            "<MessageDate>2001-10-31T13:20:10.000+10:00</MessageDate>\r\n" +
            "<TransactionGroup>NMID</TransactionGroup>\r\n" +
            "<Priority>High</Priority>\r\n" +
            "<SecurityContext>zz023</SecurityContext>\r\n" +
            "<Market>NEM</Market>\r\n" +
            "</Header>\r\n" +
            "<Transactions>\r\n" +
            "<Transaction transactionDate=\"2001-10-31T13:20:09.900+10:00\" transactionID=\"ENERGEX-TNS-12348990\">\r\n" +
            "<NMIStandingDataRequest version=\"r31\">\r\n" +
            "<NMI checksum=\"5\">1234567890</NMI>\r\n" +
            "</NMIStandingDataRequest>\r\n" +
            "</Transaction>\r\n" +
            "</Transactions>\r\n" +
            "</ase:aseXML>\r\n";

    String xmlR34BadSample = "<?xml version=\"1.0\"?>\r\n" +
            "<ase:aseXML xsi:schemaLocation=\"urn:aseXML:r34 http://www.nemmco.com.au/aseXML/schemas/r34/aseXML_r34.xsd \" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ase=\"urn:aseXML:r34\">\r\n" +
            "<Header>\r\n" +
            "<From description=\"Energex Retail Pty Ltd\">ENERGEX</From>\r\n" +
            "<To description=\"National Electricity Market Management Company\">NEMMCO</To>\r\n" +
            "<MessageID>ENERGEX-MSG-11234569</MessageID>\r\n" +
            "<MessageDate>2001-10-31T13:20:10.000+10:00</MessageDate>\r\n" +
            "<TransactionGroup>NMID</TransactionGroup>\r\n" +
            "<Priority>High</Priority>\r\n" +
            "<SecurityContext>zz023</SecuritContext>\r\n" +
            "<Market>NEM</Market>\r\n" +
            "</Header>\r\n" +
            "<Transactions>\r\n" +
            "<Transaction transactionDate=\"2001-10-31T13:20:09.900+10:00\" transactionID=\"ENERGEX-TNS-12348990\">\r\n" +
            "<NMIStandingDataRequest version=\"r31\">\r\n" +
            "<NMI checksum=\"5\">1234567890</NMI>\r\n" +
            "</NMIStandingDataRequest>\r\n" +
            "</Transaction>\r\n" +
            "</Transactions>\r\n" +
            "</ase:aseXML>";

    @Test
    public void readGoodFileTests() throws ParserConfigurationException, SAXException, IOException {
        XML xml = new XML();
        xml.setXMLFromFile(Paths.get(Globals.projectTestDataFolder, "JKV", "Core", "R34.xml").toString());

    }

    @Test(expectedExceptions = SAXException.class)
    public void readBadFileTests() throws ParserConfigurationException, SAXException, IOException {

        XML xml = new XML();
        xml.setXMLFromFile(Paths.get(Globals.projectTestDataFolder, "JKV", "Core", "badxml.xml").toString());

    }

    @Test(expectedExceptions = SAXException.class)
    public void readBlankFileTests() throws ParserConfigurationException, SAXException, IOException {

        XML xml = new XML();
        xml.setXMLFromFile(Paths.get(Globals.projectTestDataFolder, "JKV", "Core", "blank.xml").toString());
        fail();
    }

    @Test
    public void readGoodStringTests() throws IOException, SAXException, ParserConfigurationException {

        XML xml = new XML();
        xml.setXMLFromString(xmlR34GoodSample);
    }

    @Test(expectedExceptions = SAXException.class)
    public void readBadStringTests() throws IOException, SAXException, ParserConfigurationException {

        XML xml = new XML();
        xml.setXMLFromString(xmlR34BadSample);
        fail();
    }

    @Test //this is not really a good test
    public void selectNodeTest() throws IOException, SAXException, ParserConfigurationException {
        XML xml = new XML();
        xml.setXMLFromString(xmlR34GoodSample);
        Assert.assertNotNull(xml.selectNodeWithTagName("MessageID"));
    }

    @Test
    public void getNodeValue() throws IOException, SAXException, ParserConfigurationException {

        XML xml = new XML();
        xml.setXMLFromString(xmlR34GoodSample);
        Assert.assertEquals(xml.selectNodeWithTagName("MessageID").getNodeValue(),"ENERGEX-MSG-11234569", "Node value is not reading correctly");
    }

    @Test
    public void changeNodeValue() throws IOException, SAXException, ParserConfigurationException {
        XML xml = new XML();
        xml.setXMLFromString(xmlR34GoodSample);
        String txID = xml.selectNodeWithTagName("Transaction").getNodeValue();
        Reporter.log(txID + " original value.",true);
        xml.setNodeValue("test");
        Assert.assertEquals(xml.getNodeValue(),"test","Node value not set as excpected");
    }

    @Test
    public void readNodeAttributeValue() throws IOException, SAXException, ParserConfigurationException {
        XML xml = new XML();
        xml.setXMLFromString(xmlR34GoodSample);
        xml.selectNodeWithTagName("Transaction");
        String value = xml.getAttributeValue("transactionID");
        Assert.assertEquals(value,"ENERGEX-TNS-12348990","Node attribute value not read as expected");
    }

    @Test
    public void setNodeAttributeValue() throws IOException, SAXException, ParserConfigurationException {
        XML xml = new XML();
        xml.setXMLFromString(xmlR34GoodSample);
        xml.selectNodeWithTagName("Transaction");
        String value = xml.getAttributeValue("transactionID");
        Reporter.log(value + " original value.",true);

        xml.setAttributeToValue("transactionID","newvalue");
        Assert.assertEquals(xml.getAttributeValue("transactionID"),"newvalue","Node value not set as excpected");
    }

    @Test
    public void testXMLDomObject() throws IOException, SAXException, ParserConfigurationException {
        XML xml = new XML();
        xml.setXMLFromString(xmlR34GoodSample);
        Assert.assertNotNull(xml.getDocument());
    }

}
