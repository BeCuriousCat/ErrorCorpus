import java.io.IOException;

import errors.Error;
import errors.PinYinError;
import errors.SimiliarError;

public class Demo {
	public static void main(String[] args) {
		
		String path = System.getProperty("user.dir")+"/data/201602.txt";
		int corpus_size = 20000;
		//…Ë÷√¥ÌŒÛµƒ√˚≥∆
		Error simError = new SimiliarError(Error.SimiliarError, 0.001);
		Error pinyinError = new PinYinError(Error.PinYinError,0.001);
		
		
		Corpus cor = new Corpus(path, corpus_size);
		cor.addErrors(simError);
		cor.addErrors(pinyinError);
		cor.run();
		
		String filename = "similar_corpus";
		
		boolean withConfHeader = false;
		try {
			cor.write(filename,withConfHeader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
