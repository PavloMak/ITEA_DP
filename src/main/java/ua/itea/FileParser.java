package ua.itea;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FileParser implements Runnable {

	private String name;
	private Thread thread;
	private Manager filemanager;
	private DBManager dbmanager;
	private CountDownLatch waiter;

	public FileParser(String name, Manager filemanager, DBManager dbmanager, CountDownLatch waiter) {
		this.name = name;
		this.filemanager = filemanager;
		this.dbmanager = dbmanager;
		this.waiter = waiter;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		System.out.println("Parcer " + name + " started.");
		SAXParserFactory factory = SAXParserFactory.newInstance();
		ParseHandler handler = new ParseHandler();

		SAXParser parser = null;
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
		File file = filemanager.getFile();
		while (file != null) {
			System.out.println("Parcer " + name + " is parcing " + file.getName());
			try {
				parser.parse(file, handler);
				dbmanager.addToBD(handler.getShop());
				System.out.println("Parcer " + name + " ended parcing " + file.getName());
			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}
			file = filemanager.getFile();
		}
		System.out.println("Parcer " + name + " stopped.");
		waiter.countDown();
	}

}
