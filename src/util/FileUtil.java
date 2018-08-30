package util;

import java.io.File;
import java.util.ArrayList;

public class FileUtil {
	public static ArrayList<File> getAllFile(String path) {
		ArrayList<File> flist = new ArrayList<File>();

		File file = new File(path);

		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				flist.add(f);
			}
		}
		return flist;
	}
}
