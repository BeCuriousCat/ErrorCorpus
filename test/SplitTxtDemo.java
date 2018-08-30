import java.io.File;
import java.util.ArrayList;

import util.FileUtil;
import util.SplitTxt;



public class SplitTxtDemo {
	public static void main(String[] args) {
		
		String root = System.getProperty("user.dir")+"/data/source";
		String targetPath = System.getProperty("user.dir")+"/data/target";
		
		ArrayList<File> files = FileUtil.getAllFile(root);
		for (File file : files) {
			System.out.println(file.getName());
			String Fname = file.getName();
			String FType = Fname.substring(file.getName().lastIndexOf(".") + 1);
			String name = Fname.substring(0, file.getName().lastIndexOf("."));
			System.out.println(Fname);
			// 如果找到的不是txt，就跳过。
			if (!FType.equals("txt")) {
				System.out.println("没有找到文件"+Fname+".txt");
				continue;
			}
			
			SplitTxt.cutToMoreFile(file.getAbsolutePath(), targetPath, name+"-", 500);
		}

		
		
		
	}
}
