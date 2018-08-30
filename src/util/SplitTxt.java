package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class SplitTxt {

	public static void cutToMoreFile(String sourceFile, String targetDirectory,
			String prefix, int size) {
		File source = new File(sourceFile);
		InputStream in = null;
		
		OutputStream out = null;
		BufferedWriter writer = null;
		int len = 0;
		int fileIndex = 1;
		byte[] buffer = new byte[2048];
		String content;
		try {
			in = new FileInputStream(source);
			while (true) {
				out = new FileOutputStream(targetDirectory + File.separator
						+ prefix + fileIndex++ + ".txt");
				writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8")); 
				for (int i = 0; i < size; i++) {
					if ((len = in.read(buffer)) != -1) {
						content = new String(buffer, 0, buffer.length, Charset.forName("utf-8")); 
						writer.append(content);
					} else {
						return;
					}
				}
				writer.flush();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
