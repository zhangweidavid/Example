package demo.xpath;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class XpathTest {

    public static void main(String[] args) throws Exception {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new XpathTest().getClass().getResourceAsStream("/book.xml"));
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        //获取第一本书的价格
        String price = (String) xPath.evaluate("/bookstore/technology/book[1]/price", document, XPathConstants.STRING);
        System.out.println(price);
        //获取最后一本书的价格
        System.out.println((String) xPath.evaluate("/bookstore/technology/book[last()]/price", document, XPathConstants.STRING));
        //获取价格大于35的书
        System.out.println(xPath.evaluate("//book[price>50.00]/title", document, XPathConstants.STRING));
        //获取作者为zhangsan的书的价格
        System.out.println(xPath.evaluate("//book[@author='zhangsan']/price", document, XPathConstants.STRING));

        //获取科技书籍
        Node technology = (Node) xPath.evaluate("/bookstore/technology", document, XPathConstants.NODE);
        //获取当前节点
        System.out.println(xPath.evaluate(".", technology, XPathConstants.NODE));
        //获取父节点
        System.out.println(xPath.evaluate("..", technology, XPathConstants.NODE));

        Node node = (Node) xPath.evaluate("/bookstore/technology/self::node()", document, XPathConstants.NODE);
        System.out.println(node);




    }
}
