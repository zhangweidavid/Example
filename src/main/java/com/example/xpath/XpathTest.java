package com.example.xpath;


import org.apache.commons.io.IOUtils;
import org.apache.xalan.xsltc.compiler.util.NodeType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.InputStream;

public class XpathTest {

    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream inputStream = new XpathTest().getClass().getClassLoader().getResourceAsStream("book.xml");
        Document doc = builder.parse(inputStream);
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        Object bookList = xPath.evaluate("/bookstore/book", doc, XPathConstants.NODESET);
        System.out.println("book length:" + ((NodeList) bookList).getLength());
        //获取根结点bookstore
        Object result = xPath.evaluate("/bookstore/book[1]/price", doc, XPathConstants.STRING);

        System.out.println("/bookstore/book[1]/price: " + result);

        Object langs = xPath.evaluate("//title[@lang]", doc, XPathConstants.NODESET);
        System.out.println(langs);
    }
}
