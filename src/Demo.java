import java.io.IOException;

public class Demo {
	public static void main(String[] args) {
		
		String path = "D:/QQfile/1454787565/FileRecv/201602.txt";
		int corpus_size = 20000;
		
		Error simError = new SimiliarError("SimiliarError", 0.0001);
		
		Corpus cor = new Corpus(path, corpus_size);
		
		cor.run();
		
		String filename = "similar_corpus";
		
		try {
			cor.write(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
