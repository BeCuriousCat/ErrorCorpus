package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/*
 * 该类用于处理易错词表，将易错词表映射为  以正确词为键，易错词组成的列表为值的HashMap
 */

public class EasyWrongTableProcessing {
	private File file;
	
	public EasyWrongTableProcessing(){
		//默认构造函数，确定要处理的易错词表
		this.file = new File(System.getProperty("user.dir")+"/corpusLib/易错词表.txt");
	}
	
	EasyWrongTableProcessing(File file){
		this.file =file;
	}
	
	public void fileSetter(File file){
		this.file =file;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String,ArrayList> easyWrongTableProcessing(){
		HashMap<String,ArrayList> map = new HashMap<String,ArrayList>();
		InputStreamReader reader = null;
		BufferedReader br = null;
		try{
			reader = new InputStreamReader(new FileInputStream(file),"utf8"); // 建立一个输入流对象reader
			br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言    
            String line =null;  
            line = br.readLine(); 
            while (line != null) { 
            	String[] splitString = line.split(",");
            	if(splitString.length == 2){
            		if(map.containsKey(splitString[0])){
                		map.get(splitString[0]).add(splitString[1].trim());
                	}else{
                		ArrayList<String> valueList = new ArrayList<String>();
                		valueList.add(splitString[1].trim());
                		map.put(splitString[0].trim(),valueList);
                	}
            	}
            	
                line = br.readLine(); // 一次读入一行数据  
            } 
		}catch(Exception e){
			e.printStackTrace();
		}finally {  
            if (reader != null) {  
                try { 
                    br.close();
                	reader.close();  
                }catch (IOException e1) {
                	e1.printStackTrace();
                }  
            }   
		}
		
		/*
		Set<Entry<String,ArrayList>> entryset = map.entrySet();
		for(Entry<String,ArrayList> e :entryset){
			System.out.println("Key:"+e.getKey()+"-----Value:"+e.getValue());
		}
		*/
		return map;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new EasyWrongTableProcessing().easyWrongTableProcessing();
	}

}
