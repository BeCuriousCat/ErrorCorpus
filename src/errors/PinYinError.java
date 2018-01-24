package errors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import util.GeneratePY2HanZiLib;

public class PinYinError extends Error {

	private String root = System.getProperty("user.dir");
	private HashMap<String, HashSet<Character>> map = null;

	public PinYinError(String name, double rate) {
		super(name, rate);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean process(ArrayList<StringBuffer> corpus_bf,
			ArrayList<Error> errors) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < this.getError_size(); i++) {
			
		}
		
		
		return false;
	}
	
	//初始化拼音查找表
	private void initPinYinLib() {
		GeneratePY2HanZiLib PYLib = new GeneratePY2HanZiLib();
		try {
			map = PYLib.readText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("init PinYinLib defeat!");
			e.printStackTrace();
		}
	}

}
