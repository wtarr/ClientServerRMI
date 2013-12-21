/**
 * @author William
 *  
 * This will "hide" but also allow the address and port details be updated if necessary
 */

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;


public class SystemInformation {
    
    private String address;

    public String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    private void setPort(int port) {
        this.port = port;
    }
    private int port;
        
    public SystemInformation(InputStream in) {
        
        FetchDetailsFromXML(in);
    }
    
    private void FetchDetailsFromXML(InputStream in)
    {        
        // Modified version from - http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
        try 
        {
            //File fXMLfile = file;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(in);
            
            doc.getDocumentElement().normalize();
            
            Node node = doc.getElementsByTagName("serverinfo").item(0);
            
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element)node;                
                setAddress(element.getElementsByTagName("address").item(0).getTextContent());
                setPort(Integer.parseInt(element.getElementsByTagName("port").item(0).getTextContent()));
            }
        } 
        catch (ParserConfigurationException | SAXException | IOException | DOMException e) 
        {
            e.printStackTrace();
        }
    }
    
}
