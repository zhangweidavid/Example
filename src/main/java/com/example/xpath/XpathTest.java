package com.example.xpath;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
public class XpathTest {

    public static void main(String[] args) throws Exception  {
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        DocumentBuilder builder=factory.newDocumentBuilder();
        Document doc=builder.parse("C:\\Users\\wei.zw\\IdeaProjects\\Example\\src\\main\\java\\com\\example\\xpath\\book.xml");
        System.out.println(doc.getChildNodes().getLength());
        XPathFactory xPathFactory=XPathFactory.newInstance();
        XPath xPath=xPathFactory.newXPath();
        XPathExpression exper=xPath.compile("//name/text()");
        Object result=exper.evaluate(doc,XPathConstants.NODESET);
        NodeList nodeList=(NodeList)result;
        for (int i=0;i<nodeList.getLength();i++) {
            System.out.println(nodeList.item(i).getNodeValue());
        }

    }
}
