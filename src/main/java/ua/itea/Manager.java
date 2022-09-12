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

	public Manager(String path) {
		File dir = new File(path);
		files = new ArrayList<File>();
		File[] content = dir.listFiles();
		for (int i = 0; i < content.length; i++) {
			if (content[i].isFile()) {
				files.add(content[i]);
			}
		}
	}

	public synchronized File getFile() {
		File toreturn = null;
		if (files.size() != 0) {
			toreturn = files.get(0);
			files.remove(0);
		}
		return toreturn;
	};

}
