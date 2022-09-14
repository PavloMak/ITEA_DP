package ua.itea;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Manager {

	private String currentDir;
	private List<File> files;
	private boolean nofiles = true;

	public Manager(File dir) {

		files = new ArrayList<File>();
		File[] content = dir.listFiles();
		if (!content.equals(null)) {
			for (int i = 0; i < content.length; i++) {
				if (content[i].isFile()) {
					files.add(content[i]);
				}
			}
			nofiles = false;
			cleanList();
		}

	}

	public synchronized File getFile() {
		File toreturn = null;
		if (!nofiles) {
			if (files.size() != 0) {
				toreturn = files.get(0);
				files.remove(0);
			}
		}

		return toreturn;
	};

	private void cleanList() {
		File temp = null;
		int i = 0;
		while (i < files.size()) {
			temp = files.get(i);
			if (temp.getName().endsWith(".xml")) {
				i++;
			} else {
				files.remove(i);
			}
		}
		System.out.println(files.size());
	}

}
