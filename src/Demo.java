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
			//设置错误的名称
			Error easyWrongWords = new EasyWrongWords(Error.EasyWrongWords,rate*0.2);
			Error simError = new SimiliarError(Error.SimiliarError,rate*0.4);
			Error pinyinError = new PinYinError(Error.PinYinError,rate*0.4);
			//Error numbersError = new NumbersError(Error.NumbersError,0.001);
			//Error punctuationError = new PunctuationError(Error.PunctuationError,0.001);
			
			
			//生成一个语料库，设置原始语料库的路径和生成语料库的大小
			Corpus cor = new Corpus(path, corpus_size);
			System.out.println(cor.getCorpus_size());
			//将错误添加到语料库中
			cor.addErrors(easyWrongWords);
		
			cor.addErrors(simError);
			
			cor.addErrors(pinyinError);
			
			//cor.addErrors(numbersError);
			//cor.addErrors(punctuationError); 
			//运行各错误添加的逻辑，添加错误
			
			long sTime = System.currentTimeMillis();
			cor.run();
			long eTime = System.currentTimeMillis();
			
			System.out.println("run time: " + (eTime - sTime) / 1000.0);
			
			
			//生成的文件的名称		
			String filename = String.valueOf(rate);
			//conf文件是否需要输出非json部分
			boolean withConfHeader = false;
			try {
				cor.write(filename,withConfHeader);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}