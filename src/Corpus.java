import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;

public class Corpus {

	private String relativelyPath = System.getProperty("user.dir");
	private String path = "";
	private ArrayList<StringBuffer> text = null;
	private int corpus_size = 0;
	private ArrayList<Error> errors = null;
	private double error_rate = 0;
	private int errs_number = 0;

	public Corpus(String path, int corpus_size) {
		super();
		this.corpus_size = corpus_size;
		this.errors = new ArrayList<Error>();
		this.path = path;
		try {
			this.text = getText(path, corpus_size);
		} catch (IOException e) {
			System.out.println("语料库：初始化文本失败！");
			e.printStackTrace();
		}

	}

	private ArrayList<StringBuffer> getText(String path, int corpus_size)
			throws IOException {
		File file = new File(path);
		ArrayList<StringBuffer> bfs = new ArrayList<StringBuffer>();
		if (!file.exists()) {
			System.err.println("Can't Find " + file);
		}
		BufferedReader in = null;
		int count = 0;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "UTF-8"));
			String tempString = null;
			int line_count = 0;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = in.readLine()) != null) {
				// 显示行号
				StringBuffer bf = new StringBuffer();
				bf.append(tempString);
				line_count = bf.length();
				if (count + line_count < corpus_size) {
					count += line_count;
					bfs.add(bf);
				} else {
					bf.setLength(corpus_size - count);
					bfs.add(bf);
				}
			}
		} catch (Exception e) {
			System.out.println("读取文件出错！");
			e.printStackTrace();
		} finally {
			in.close();
		}
		return bfs;
	}

	public void setCorpus_size(int corpus_size) {
		this.corpus_size = corpus_size;
	}

	public ArrayList<StringBuffer> getText() {
		return text;
	}

	public void setText(ArrayList<StringBuffer> text) {
		this.text = text;
	}

	public int getCorpus_size() {
		return corpus_size;
	}

	public ArrayList<Error> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<Error> errors) {
		// 在错误传入后，根据错误比例设置好错误数量
		for (Error err : errors) {
			err.setError_size((int) Math.ceil(corpus_size * err.getError_rate()));
			this.error_rate += err.getError_rate();
		}
		this.errors = errors;
		try {
			for (Error error : errors) {
				this.errs_number += error.getError_size();
			}
		} catch (Exception e) {
			System.out.println("init corpus wraning: errors is null");
		}
	}

	public void addErrors(Error err) {
		// 在错误传入后，根据错误比例设置好错误数量
		err.setError_size(this.corpus_size);
		this.errors.add(err);

		try {
			for (Error error : errors) {
				this.errs_number += error.getError_size();
				this.error_rate += error.getError_rate();
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
		String str = "Corpus total errors rate: " + error_rate * 100
				+ "% errors number: " + errs_number + "\n";
		for (Error error : errors) {
			str += "error type:" + error.getName() + " rate:"
					+ error.getError_rate() + " number:"
					+ error.getError_size();
		}
		return str;
	}

	/**
	 * 把数据写入到文件
	 * 
	 * @throws IOException
	 */
	public void write(String filename) throws IOException {
		// TODO
		String path = relativelyPath + "\\data\\";
		String filepath = path + filename + ".txt";
		String confpath = path + filename + "_conf.txt";
		File file = new File(filepath);
		File conf_file = new File(confpath);
		FileWriter fw = null;
		FileWriter conf_fw = null;
		BufferedWriter bw = null;

		// TODO
		System.out.println(filepath);

		try {
			file.createNewFile();
			System.out.println("success create file,the file is " + filepath);
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			for (StringBuffer sb : text) {
				bw.write(sb.toString());
				bw.newLine();
			}
			fw.flush();

			conf_file.createNewFile();
			System.out.println("success create file,the file is " + confpath);
			conf_fw = new FileWriter(conf_file);
			String content = "";
			String enter = "\n";
			// 语料库大小
			content += "corpus_size:" + this.corpus_size;
			content += enter;
			// 获得错误类型及数量
			content += getErrorsNumber();
			// 生成时间
			String temp_str = "";
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss aa");
			temp_str = sdf.format(dt);
			content += "risen time:" + temp_str;
			content += enter;

			String json = JSON.toJSONString(errors);

			// 输出
			conf_fw.write(content, 0, content.length());
			conf_fw.write(json, 0, json.length());
			conf_fw.flush();

		} catch (Exception e) {
			System.out.println("Error: write file error!");
			e.printStackTrace();
		} finally {
			bw.close();
			fw.close();
			conf_fw.close();
		}

	}

	public void run() {
		for (Error error : errors) {
			error.process(text, errors);
		}
	}

	public static void main(String[] args) {
		StringBuffer str = new StringBuffer("12345");
		StringBuffer s = str.replace(1, 2, "|");
		System.out.println(s);

	}
}
