package errors;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import util.GeneratePY2HanZiLib;
//pinyin4j entry point 
import net.sourceforge.pinyin4j.PinyinHelper;

//only when you need formating the output 
import net.sourceforge.pinyin4j.format.*; 
import net.sourceforge.pinyin4j.format.exception.*;

public class PinYinError extends Error {

	private String root = System.getProperty("user.dir");
	private HashMap<String, HashSet<Character>> map = null;

	public PinYinError(String name, double rate) {
		super(name, rate);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean process(ArrayList<StringBuffer> corpus_bfs,
			ArrayList<Error> errors) {
		// TODO Auto-generated method stub
		initPinYinLib();
		
		
		//��ʼ��pinyin4j
		HanyuPinyinOutputFormat deFormat = new HanyuPinyinOutputFormat();
		deFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		deFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		//���ɴ���Ķ����������������
		int paragraph_index = 0; 
		int index = 0;
		
		//Ҫ�ĵ���
		char charAtText;
		//�Ƿ��ظ�
		boolean repeat = false; 
		StringBuffer paragraph = null;
		//ƴ��
		String[] pinyin ;
		// ����ν��ʴ���
		ArrayList<Sign> signs = new ArrayList<Sign>();
		System.out.println("��ʼ��������ִ���");
		int count = 0; //������������ѡ��set�е����һ���ַ�
		char correct_word;
		char wrong_word;
		//TODO
		for (int i = 0; i < 3; i++) {
			//�������
			paragraph_index = getRondParagraphIndex(corpus_bfs);
			index = getRondIndex(corpus_bfs, paragraph_index);
			while(true){
				paragraph = corpus_bfs.get(paragraph_index);
				charAtText = paragraph.charAt(index);
				System.out.println("======"+charAtText);
				try {
					pinyin = PinyinHelper.toHanyuPinyinStringArray(charAtText,deFormat);
					if(pinyin.length > 1){
						System.out.println(pinyin[0]);
					}
					
					if(!map.containsKey(pinyin[0])){
						continue;
					}else{
						repeat = isRepeated(errors, signs, index, paragraph_index, repeat);	
						if(repeat){
							continue;
						}
						//System.out.println(map.get(pinyin[0]));
						HashSet<Character> set = map.get(pinyin[0]);
						int max = set.size();
						int rand = (int) (Math.random() * max);
						Iterator<Character> iter = set.iterator();
						count = 0;
						while(iter.hasNext()){
							if(rand == count){
								correct_word = iter.next();
							}
						}
					}
				} catch (Exception e) {
					System.out.println(charAtText+":ת����ƴ������");
					e.printStackTrace();
				}
				
			}
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		
		sb.append("ƴ�����͸��yԇ�棬ƴ�����ͼ����d");
		sb1.append("�⼸�����ڽ�С��ѧ����ƴ������������Ϊ���Ǹ��򵥵Ĺ����������ظ��Ͷ�����");
		sb2.append("�� tony ʾ��, loƴ���Ŀ����ƺܶ��˲�֪����lo��luo ���в���");
		ArrayList<StringBuffer> bfs = new ArrayList<StringBuffer>();
		
		bfs.add(sb);
		bfs.add(sb1);
		bfs.add(sb2);
		
		PinYinError er = new PinYinError("err", 0.01);
		er.process(bfs, new ArrayList<Error>());
 	}
	//��ʼ��ƴ�����ұ�
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
