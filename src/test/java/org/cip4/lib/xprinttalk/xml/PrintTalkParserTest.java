/**
 * All rights reserved by
 * 
 * flyeralarm GmbH
 * Alfred-Nobel-Straße 18
 * 97080 Würzburg
 *
 * Email: info@flyeralarm.com
 * Website: http://www.flyeralarm.com
 */
package org.cip4.lib.xprinttalk.xml;

import java.io.ByteArrayOutputStream;

import org.cip4.lib.xjdf.XJdfNodeFactory;
import org.cip4.lib.xjdf.builder.ProductBuilder;
import org.cip4.lib.xjdf.builder.XJdfBuilder;
import org.cip4.lib.xjdf.schema.GeneralID;
import org.cip4.lib.xjdf.schema.RunList;
import org.cip4.lib.xprinttalk.PrintTalkNodeFactory;
import org.cip4.lib.xprinttalk.builder.PrintTalkBuilder;
import org.cip4.lib.xprinttalk.schema.PrintTalk;
import org.cip4.lib.xprinttalk.schema.PurchaseOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test case for PrintTalkParser
 * @author stefan.meissner
 * @date 23.10.2012
 */
public class PrintTalkParserTest {

	PrintTalkParser ptkParser;

	/**
	 * Set up unit test.
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		// new Instance
		ptkParser = PrintTalkParser.newInstance();
	}

	/**
	 * Tear down unit test.
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		// set null
		ptkParser = null;
	}

	/**
	 * Test method for
	 * {@link org.cip4.lib.xprinttalk.xml.PrintTalkParser#parsePrintTalk(org.cip4.lib.xprinttalk.schema.PrintTalk, java.io.OutputStream, boolean)}.
	 */
	@Test
	public void testParsePrintTalkXmlHeader() throws Exception {

		// arrange
		final String PAYLOAD_ID = "PL_200";
		final String BUSINESS_ID = "BS_12345";
		final String JOB_ID = "JB_1234";
		final String AMOUNT = "1500";

		PrintTalkNodeFactory ptkNodeFactory = new PrintTalkNodeFactory();
		XJdfNodeFactory xJdfNodeFactory = new XJdfNodeFactory();

		PrintTalkBuilder builder = new PrintTalkBuilder(PAYLOAD_ID);

		PurchaseOrder purchaseOrder = ptkNodeFactory.createPurchaseOrder(BUSINESS_ID, "EUR");
		builder.addRequest(purchaseOrder);

		XJdfBuilder xJdfBuilder = new XJdfBuilder(JOB_ID);

		GeneralID generalId = xJdfNodeFactory.createGeneralID("CatalogID", "49");
		xJdfBuilder.addGeneralID(generalId);

		ProductBuilder productBuilder = new ProductBuilder(Integer.valueOf(AMOUNT).intValue());
		xJdfBuilder.addProduct(productBuilder.build());

		RunList runList = xJdfNodeFactory.createRunList("http://www.example.org:8080/artwork.pdf");
		xJdfBuilder.addParameter(runList);

		purchaseOrder.setXJDF(xJdfBuilder.build());
		PrintTalk printTalk = builder.build();

		// act
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ptkParser.parsePrintTalk(printTalk, bos, true);

		// assert
		String strXml = new String(bos.toByteArray());
		System.out.println(strXml);

		int i = strXml.indexOf("\n");
		int n = strXml.indexOf("\n", i + 1);

		String actual = strXml.substring(i + 1, n);

		// Assert.assertEquals("Result is wrong", "<!-- Generated by CIP4 xPrintTalkLib [version not specified] and CIP4 xJdfLib [version not specified] -->",
		// actual);

	}

	/**
	 * Test method for
	 * {@link org.cip4.lib.xprinttalk.xml.PrintTalkParser#parsePrintTalk(org.cip4.lib.xprinttalk.schema.PrintTalk, java.io.OutputStream, boolean)}.
	 */
	@Test
	public void testParsePrintTalkXmlHeaderByteArray() throws Exception {

		// arrange
		final String PAYLOAD_ID = "PL_200";
		final String BUSINESS_ID = "BS_12345";
		final String JOB_ID = "JB_1234";
		final String AMOUNT = "1500";

		PrintTalkNodeFactory ptkNodeFactory = new PrintTalkNodeFactory();
		XJdfNodeFactory xJdfNodeFactory = new XJdfNodeFactory();

		PrintTalkBuilder builder = new PrintTalkBuilder(PAYLOAD_ID);

		PurchaseOrder purchaseOrder = ptkNodeFactory.createPurchaseOrder(BUSINESS_ID, "EUR");
		builder.addRequest(purchaseOrder);

		XJdfBuilder xJdfBuilder = new XJdfBuilder(JOB_ID);

		GeneralID generalId = xJdfNodeFactory.createGeneralID("CatalogID", "49");
		xJdfBuilder.addGeneralID(generalId);

		ProductBuilder productBuilder = new ProductBuilder(Integer.valueOf(AMOUNT).intValue());
		xJdfBuilder.addProduct(productBuilder.build());

		RunList runList = xJdfNodeFactory.createRunList("http://www.example.org:8080/artwork.pdf");
		xJdfBuilder.addParameter(runList);

		purchaseOrder.setXJDF(xJdfBuilder.build());
		PrintTalk printTalk = builder.build();

		// act
		byte[] bytes = ptkParser.parsePrintTalk(printTalk, true);

		// assert
		String strXml = new String(bytes);
		System.out.println(strXml);

		int i = strXml.indexOf("\n");
		int n = strXml.indexOf("\n", i + 1);

		String actual = strXml.substring(i + 1, n);

		// Assert.assertEquals("Result is wrong", "<!-- Generated by CIP4 xPrintTalkLib [version not specified] and CIP4 xJdfLib [version not specified] -->",
		// actual);

	}
}
