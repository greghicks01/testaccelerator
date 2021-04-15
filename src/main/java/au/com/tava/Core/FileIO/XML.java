package au.com.tava.Core.FileIO;

/**
 * Created by Greg on 22/11/2016.
 */

import au.com.tava.Core.InProgress.CoreBase;
import org.testng.log4testng.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;

public class XML extends CoreBase {

    private Logger log = Logger.getLogger(this.getClass());

    private Document XMLDom = null;
    private Node currentNode;

    String systemValue = "";

    /**
     * c'tor
     */
    public XML() {
        log.info("New XML class instantiated");
    }

    /**
     *
     * @param file
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public XML(Path file) throws ParserConfigurationException, SAXException, IOException {
        this();
        setXMLFromFile(file.toString());
    }

    /**
     *
     * @param xmldata
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public XML(String xmldata) throws IOException, SAXException, ParserConfigurationException {
        this();
        setXMLFromString( xmldata);
    }

    /**
     * @return
     */
    public Document getDocument() {
        return XMLDom;
    }

    /**
     * @return
     */
    public String getTextFromXML() throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        //transformer.setOutputProperty(OutputKeys.INDENT, "no");
        //transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        //transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, systemValue);

        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(XMLDom), new StreamResult(writer));
        return writer.getBuffer().toString();
    }

    /**
     * @param fqfn fully qualified file name
     */
    public void setXMLFromFile(String fqfn) throws IOException, SAXException, ParserConfigurationException {
        XMLDom = getDocFromXML(fqfn);
        if (XMLDom.getDoctype()!= null)
            systemValue = (new File (XMLDom.getDoctype().getSystemId())).getName();
    }

    /**
     * @param value
     */
    public void setXMLFromString(String value) throws ParserConfigurationException, IOException, SAXException {
        XMLDom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(value)));
        if (XMLDom.getDoctype()!= null)
            systemValue = (new File (
                    XMLDom.getDoctype().getSystemId())
            ).getName();
    }

    /**
     * retrieves file from disc
     *
     * @param filename
     * @return the parsed document
     */
    private Document getDocFromXML(String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(filename));
        doc.getDocumentElement().normalize();
        return doc;

    }

    /**
     *
     * @param name
     * @return
     */
    public XML selectNodeWithTagName(String name) {
        currentNode = XMLDom.getDocumentElement().getElementsByTagName(name).item(0);
        return this;
    }

    /**
     *
     * @return
     */
    public String getNodeValue(){
        return currentNode.getFirstChild().getNodeValue();
    }

    /**
     *
     * @param attribName
     * @return
     */
    public String getAttributeValue(String attribName) {
        NamedNodeMap nodeList = currentNode.getAttributes();
        return nodeList.getNamedItem(attribName).getNodeValue();
    }

    /**
     *
     * @param value
     */
    public void setNodeValue(String value) {
        currentNode.getFirstChild().setNodeValue(value);
    }

    /**
     *
     * @param name
     * @param value
     */
    public void setAttributeToValue(String name, String value){
        NamedNodeMap nodeList = currentNode.getAttributes();
        nodeList.getNamedItem(name).setNodeValue(value);
    }

    /**
     *
     * @param file
     * @throws TransformerException
     */
    public void writeXmlToFile(File file) throws TransformerException {

        Result output = new StreamResult( file );
        Source input = new DOMSource( XMLDom );

        TransformerFactory.newInstance().newTransformer().transform(input, output);
    }

    /**
     * Created by Greg on 4/06/2017.
     * URL:  https://examples.javacodegeeks.com/enterprise-java/testng/testng-listeners-example/
     *       http://toolsqa.com/selenium-webdriver/testng-listeners/
     *       http://www.360logica.com/blog/different-types-of-listeners-in-testng/
     *       http://learn-automation.com/what-is-testng-listener-and-how-to-implement-in-selenium/
     */


}
