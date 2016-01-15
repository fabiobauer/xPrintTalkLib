package org.cip4.lib.xprinttalk.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import static org.junit.Assert.*;

import org.cip4.lib.xjdf.XJdfNodeFactory;
import org.cip4.lib.xjdf.builder.ProductBuilder;
import org.cip4.lib.xjdf.builder.XJdfBuilder;
import org.cip4.lib.xjdf.schema.Product;
import org.cip4.lib.xjdf.schema.XJDF;
import org.cip4.lib.xjdf.xml.internal.JAXBContextFactory;
import org.cip4.lib.xprinttalk.PrintTalkNodeFactory;
import org.cip4.lib.xprinttalk.builder.PrintTalkBuilder;
import org.cip4.lib.xprinttalk.schema.PrintTalk;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * JUnit test case for PrintTalkValidator.
 */
public class PrintTalkValidatorTest {

	private final String RES_TEST_PTK = "/org/cip4/lib/xprinttalk/simple_job.ptk";

	private PrintTalkValidator printTalkValidator;

	/**
	 * Default constructor.
	 */
	public PrintTalkValidatorTest() {
		try {
			JAXBContextFactory.init();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set up unit test.
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		// init instance variables

	}

	/**
	 * Tear down unit test.
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		printTalkValidator = null;
	}

	/**
	 * Test method for {@link org.cip4.lib.xjdf.xml.internal.AbstractXmlValidator#check(java.io.InputStream)}.
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testCheck() throws SAXException, ParserConfigurationException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

		// arrange
		InputStream is = PrintTalkValidatorTest.class.getResourceAsStream(RES_TEST_PTK);

		// act
		printTalkValidator = new PrintTalkValidator();

		// assert
		assertFalse(printTalkValidator.isValid(is));
	}

	@Test
	public void testValidateXJDF() throws Exception {

		// arrange
		XJdfNodeFactory nf = new XJdfNodeFactory();
		PrintTalkNodeFactory ptkNf = new PrintTalkNodeFactory();

		ProductBuilder productBuilder = new ProductBuilder(1000);
		Product product = productBuilder.build();

		XJdfBuilder xJdfBuilder = new XJdfBuilder("MyJobId");
		xJdfBuilder.addProduct(product);
		xJdfBuilder.addParameter(nf.createRunList("MyContent"));
		XJDF xjdf = xJdfBuilder.build();

		PrintTalkBuilder ptkBuilder = new PrintTalkBuilder();
		ptkBuilder.addRequest(ptkNf.createPurchaseOrder("MyJobId", null, xjdf));
		PrintTalk ptk = ptkBuilder.build();

		// act
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		PrintTalkParser parser = new PrintTalkParser();
		parser.parsePrintTalk(ptk, bos);

		bos.close();

		// assert
		System.out.println(new String(bos.toByteArray()));
	}
}
