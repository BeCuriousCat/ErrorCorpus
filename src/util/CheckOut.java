package util;

import java.io.File;
import java.util.ArrayList;

public class CheckOut {
	ArrayList<Error> errors = null; 
	/**
	 * ����Error_conf�ļ���json
	 * @param path conf�ļ�·��
	 */
	public void readJson(String path){
		//TODO ����json����
		File file = new File(path);
		if(!file.exists()){
			System.out.println("�ļ������ڣ�");
			System.exit(0);
		}
		System.out.println("����û��д��");
	}
	/**
	 * ��ѯ��paragraph_index�ε�indexλ�õ�predict���Ƿ���ȷ
	 * @param paragraph_index
	 * @param index
	 * @param predict
	 * @return 0 ��ʾ ��λ�ò����ڴ���  1��ʾ��λ�ô��ڴ��󣬵���predictԤ����� 2��ʾ��λ�ô��ڴ�����Ԥ����ȷ
	 */
	public int check(int paragraph_index,int index,String predict){
		//TODO
		if( errors == null ){
			System.out.println("���������ȡһ��conf�ļ���");
		}
		System.out.println("����û��д��");
		return 0;
	}

}

