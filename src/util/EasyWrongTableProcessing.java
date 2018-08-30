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
 * �������ڴ����״�ʱ����״�ʱ�ӳ��Ϊ  ����ȷ��Ϊ�����״����ɵ��б�Ϊֵ��HashMap
 */

public class EasyWrongTableProcessing {
	private File file;
	
	public EasyWrongTableProcessing(){
		//Ĭ�Ϲ��캯����ȷ��Ҫ������״�ʱ�
		this.file = new File(System.getProperty("user.dir")+"/corpusLib/�״�ʱ�.txt");
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
			reader = new InputStreamReader(new FileInputStream(file),"utf8"); // ����һ������������reader
			br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������    
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
            	
                line = br.readLine(); // һ�ζ���һ������  
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
