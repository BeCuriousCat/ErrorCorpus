import java.io.IOException;

import errors.Error;
import errors.PinYinError;
import errors.SimiliarError;

public class Demo {
	public static void main(String[] args) {
		
		String path = System.getProperty("user.dir")+"/data/201602.txt";
		int corpus_size = 200000;
		//设置错误的名称
		Error simError = new SimiliarError(Error.SimiliarError, 0.001);
		Error pinyinError = new PinYinError(Error.PinYinError,0.001);
		
		//生成一个语料库，设置原始语料库的路径和生成语料库的大小
		Corpus cor = new Corpus(path, corpus_size);
		System.out.println(cor.getCorpus_size());
		//将错误添加到语料库中
		cor.addErrors(simError);
		cor.addErrors(pinyinError);
		//运行各错误添加的逻辑，添加错误
		cor.run();
		//生成的文件的名称
		String filename = "similar_corpus";
		//conf文件是否需要输出非json部分
		boolean withConfHeader = false;
		try {
			cor.write(filename,withConfHeader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
