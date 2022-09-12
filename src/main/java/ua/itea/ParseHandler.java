package ua.itea;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParseHandler extends DefaultHandler {

	private Shop shop = null;
	private List<Phone> phones = new ArrayList<Phone>();
	private Phone phone;
	private final static String SHOP = "shop";
	private final static String PHONE = "phone";
	private final static String PHONE_PRICE = "price";
	private final static String PHONE_NAME = "name";
	private final static String PHONE_BRAND = "brand";
	private final static String PHONE_SYSTEM = "system";
	private final static String PHONE_RAM = "ram";
	private final static String PHONE_MEMORY = "memory";
	private final static String PHONE_PROCESSOR = "processor";
	private final static String PHONE_CORES = "cores";
	private final static String PHONE_DIAGONAL = "diagonal";

	private boolean isShop;
	private boolean isPrice;
	private boolean isName;
	private boolean isBrand;
	private boolean isSystem;
	private boolean isRam;
	private boolean isMemory;
	private boolean isProcessor;
	private boolean isCores;
	private boolean isDiagonal;

	@Override
	public void startDocument() throws SAXException {
		shop = new Shop();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch (qName) {
		case SHOP:
			isShop = true;
			break;
		case PHONE:
			phone = new Phone();
			break;
		case PHONE_PRICE:
			isPrice = true;
			break;
		case PHONE_NAME:
			isName = true;
			break;
		case PHONE_BRAND:
			isBrand = true;
			break;
		case PHONE_SYSTEM:
			isSystem = true;
			break;
		case PHONE_RAM:
			isRam = true;
			break;
		case PHONE_MEMORY:
			isMemory = true;
			break;
		case PHONE_PROCESSOR:
			isProcessor = true;
			break;
		case PHONE_CORES:
			isCores = true;
			break;
		case PHONE_DIAGONAL:
			isDiagonal = true;
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
		case SHOP:
			isShop = false;
			break;
		case PHONE:
			phones.add(phone);
			break;
		case PHONE_PRICE:
			isPrice = false;
			break;
		case PHONE_NAME:
			isName = false;
			break;
		case PHONE_BRAND:
			isBrand = false;
			break;
		case PHONE_SYSTEM:
			isSystem = false;
			break;
		case PHONE_RAM:
			isRam = false;
			break;
		case PHONE_MEMORY:
			isMemory = false;
			break;
		case PHONE_PROCESSOR:
			isProcessor = false;
			break;
		case PHONE_CORES:
			isCores = false;
			break;
		case PHONE_DIAGONAL:
			isDiagonal = false;
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (isShop) {
			shop.setName(getValue(ch, start, length));
		} else if (isPrice) {
			phone.setPrice(Integer.valueOf(getValue(ch, start, length)));
		} else if (isName) {
			phone.setName(getValue(ch, start, length));
		} else if (isBrand) {
			phone.setBrand(getValue(ch, start, length));
		} else if (isSystem) {
			phone.setSystem(getValue(ch, start, length));
		} else if (isRam) {
			phone.setRam(Integer.valueOf(getValue(ch, start, length)));
		} else if (isMemory) {
			phone.setMemory(Integer.valueOf(getValue(ch, start, length)));
		} else if (isProcessor) {
			phone.setProcessor(getValue(ch, start, length));
		} else if (isCores) {
			phone.setCores(Integer.valueOf(getValue(ch, start, length)));
		} else if (isDiagonal) {
			phone.setDiagonal(Double.valueOf(getValue(ch, start, length)));
		}

	}

	private String getValue(char[] ch, int start, int length) {
		String value = "";
		for (int i = start; i < start + length; i++) {
			value += ch[i];
		}
		return value.trim().isEmpty() ? "" : value.trim();
	}

	@Override
	public void endDocument() throws SAXException {
		shop.setPhones(phones);
	}

	public Shop getShop() {
		return shop;
	}

}
