package ua.itea;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Main {

	public static void main(String[] args) {
		DBConnector conection = new DBConnector();
		DBManager manager = new DBManager(conection);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the path to xml files.");
		String path = scanner.nextLine();
		Manager filemager = new Manager(path);

		System.out.println("Do you wish to clear database before usage? (Y/N)");

		String clearing;
		if (scanner.hasNext("Y") || scanner.hasNext("N")) {
			clearing = scanner.next();

			if (clearing.equals("Y")) {
				manager.createBD(true);
			} else {
				manager.createBD(false);
			}

		} else {
			System.out.println("Wrong input");
			scanner.next();
		}

		CountDownLatch waiter = new CountDownLatch(2);
		FileParser parcer1 = new FileParser("A", filemager, manager, waiter);
		FileParser parcer2 = new FileParser("B", filemager, manager, waiter);
		try {
			waiter.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Do you wish to see results (Y/N)");
		String answer;
		if (scanner.hasNext("Y") || scanner.hasNext("N")) {
			answer = scanner.next();

			if (answer.equals("Y")) {
				manager.getAllInfo();
			}

		} else {
			System.out.println("Wrong input");
			scanner.next();
		}

	}

}
