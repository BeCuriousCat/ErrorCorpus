import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Corpus {
	
	private String relativelyPath = System.getProperty("user.dir");
	private String path = "";
	private String text = null;
	private int corpus_size = 0;
	private ArrayList<Error> errors = null;
	private double error_rate = 0;
	private int errs_number = 0;

	public Corpus(String path, int corpus_size) {
		super();
		this.corpus_size = corpus_size;
		this.errors = new ArrayList<Error>();
		this.path = path;
		this.text = getText(path,corpus_size);
	}

	private String getText(String path, int corpus_size) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setText(String text) {
		// int corpus_size = this.getCorpus_size();
		this.text = text;
	}

	public void setCorpus_size(int corpus_size) {
		this.corpus_size = corpus_size;
	}

	public String getText() {
		return text;
	}

	public int getCorpus_size() {
		return corpus_size;
	}

	public ArrayList<Error> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<Error> errors) {
		this.errors = errors;
		
		try {
			for (Error error : errors) {
				this.errs_number += error.getError_size();
			}
		} catch (Exception e) {
			System.out.println("init corpus wraning: errors is null");
		}
	}
	
	public void addErrors(Error err){
		
		err.setError_size(this.corpus_size);
		this.errors.add(err);
		
		try {
			for (Error error : errors) {
				this.errs_number += error.getError_size();
			}
		} catch (Exception e) {
			System.out.println("init corpus wraning: errors is null");
		}
	}
	/**
	 * 返回错误类型和数目
	 * 
	 * @return
	 */
	public String getErrorsNumber() {
		//TODO
		String str = "";
		for (Error error : errors) {
		//TODO JSON
		}
		return str;
	}
	
	/**
	 * 把数据库写入到文件
	 * 
	 * @throws IOException
	 */
	public void write(String filename) throws IOException {
		String path = "G:\\Myeclipse10.0\\ErrorCorpus\\";
		String filepath = path + filename + ".txt";
		String confpath = path + filename + "_conf.txt";
		File file = new File(filepath);
		File conf_file = new File(confpath);
		FileWriter fw = null;
		FileWriter conf_fw = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("success create file,the file is "
						+ filepath);
				fw = new FileWriter(file);
				fw.write(this.getText(), 0, this.getCorpus_size());
				fw.flush();
			}

			if (!conf_file.exists()) {
				conf_file.createNewFile();
				System.out.println("success create file,the file is "
						+ confpath);
				conf_fw = new FileWriter(conf_file);
				String content = "";
				String enter = "\n";
				//语料库大小
				content += "corpus_size:" + this.corpus_size;
				content += enter;
				//错误率和错误个数
				content += "error_rate:" + this.error_rate +"   error_number:"+ this.errs_number;
				content += enter;
				// 生成时间
				String temp_str = "";
				Date dt = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss aa");
				temp_str = sdf.format(dt);
				content += "risen time:" + temp_str;
				content += enter;
				
				//获得错误类型及数量
				content += getErrorsNumber();
				
				//输出
				conf_fw.write(content, 0, content.length());
				conf_fw.flush();
				
			}
		} catch (Exception e) {
			System.out.println("Error: write file error!");
			e.printStackTrace();
		} finally {
			fw.close();
			conf_fw.close();
		}

	}

	public static void main(String[] args) {
		StringBuffer str = new StringBuffer("12345");
		StringBuffer s = str.replace(1,2,"|");
		System.out.println(s);
		
	}
}
