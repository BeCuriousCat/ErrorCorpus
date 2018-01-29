import java.io.IOException;

import errors.Error;
import errors.PinYinError; 
import errors.EasyWrongWords;
import errors.NumbersError;
import errors.PunctuationError;
import errors.SimiliarError;


public class Demo {
	public static void main(String[] args) {
		
		String path = System.getProperty("user.dir")+"/data/201604.txt";
		int corpus_size = -1;
		float rate = 0.0005f;
		for(;rate<=0.01;rate= rate+0.0005f){
			//���ô��������
			Error easyWrongWords = new EasyWrongWords(Error.EasyWrongWords,rate*0.2);
			Error simError = new SimiliarError(Error.SimiliarError,rate*0.4);
			Error pinyinError = new PinYinError(Error.PinYinError,rate*0.4);
			//Error numbersError = new NumbersError(Error.NumbersError,0.001);
			//Error punctuationError = new PunctuationError(Error.PunctuationError,0.001);
			
			
			//����һ�����Ͽ⣬����ԭʼ���Ͽ��·�����������Ͽ�Ĵ�С
			Corpus cor = new Corpus(path, corpus_size);
			System.out.println(cor.getCorpus_size());
			//��������ӵ����Ͽ���
			cor.addErrors(easyWrongWords);
		
			cor.addErrors(simError);
			
			cor.addErrors(pinyinError);
			
			//cor.addErrors(numbersError);
			//cor.addErrors(punctuationError); 
			//���и�������ӵ��߼�����Ӵ���
			
			long sTime = System.currentTimeMillis();
			cor.run();
			long eTime = System.currentTimeMillis();
			
			System.out.println("run time: " + (eTime - sTime) / 1000.0);
			
			
			//���ɵ��ļ�������		
			String filename = String.valueOf(rate);
			//conf�ļ��Ƿ���Ҫ�����json����
			boolean withConfHeader = false;
			try {
				cor.write(filename,withConfHeader);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}