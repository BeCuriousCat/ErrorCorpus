package corpusgeneration;

import java.io.IOException;

public class Demo {
	public static void main(String[] args) {
		
		String path = System.getProperty("user.dir")+"/data/201604.txt";
		int corpus_size = 20000;
		
		Error simError = new SimiliarError("SimiliarError", 0.0001);
		Error easyWrongError = new EasyWrongWords("EasyWrongWordsError",0.0001);
		Error numsError = new NumbersError("NumbersError",0.0001);
		Error puncError = new PunctuationError("PunctuationError",0.0001);
		
		
		Corpus cor = new Corpus(path, corpus_size);
		cor.addErrors(easyWrongError);
		cor.addErrors(simError);
		cor.addErrors(numsError);
		cor.addErrors(puncError);
		
		cor.run();
		
		String filename = "201604similar_corpus";
		
		try {
			cor.write(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
