package errors;

import java.util.ArrayList;

public class Error {
	/**
	 * @param name
	 *            describe the error name
	 * @param rate
	 *            the rate of error in the whole corpus
	 * @param error_size
	 *            the number of this error
	 * @param describe
	 *            a error with position, wrong word and correct word
	 */
	private String name = "";
	private int errorSize = 0;
	private double errorRate = 0;
	private ArrayList<Sign> signs = new ArrayList<Sign>();
	public static String SimiliarError = "similiar";
	public static String PinYinError = "pinyin";
	public static String EasyWrongWords = "easyWrongWords";
	public static String NumbersError = "numbersError";
	public static String PunctuationError = "punctuationError";

	public Error() {
		super();
	}

	public Error(String name, double rate) {
		super();
		this.name = name;
		this.errorRate = rate;
	}

	public String getName() {
		return name;
	}

	public double getErrorRate() {
		return errorRate;
	}

	public void setErrorRate(double errorRate) {
		this.errorRate = errorRate;
	}

	public ArrayList<Sign> getSigns() {
		return signs;
	}

	public void setSigns(ArrayList<Sign> signs) {
		this.signs = signs;
	}

	public void addSign(Sign sign){
		signs.add(sign);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getErrorSize() {
		return errorSize;
	}

	public void setErrorSizeByCorpus_size(int corpus_size) {
		this.errorSize = (int) (corpus_size * this.errorRate);
	}
	public void setErrorSize(int error_size) {
		this.errorSize = error_size;
	}
	

	public String toString() {
		String str = "Error: " + name + " 错误数量: "+ errorRate +"\n ";
		for (Sign sign : signs) {
			str += "第" + sign.getParagraph() + "段，第" + sign.getIndex() + "个词："
					+ sign.getCorrect_word() + "—>" + sign.getWrong_word()
					+ "\n";
		}
		return str;
	}

	/**
	 * 这个易错字在整个语料库生成的错误中是否重复
	 * 
	 * @param errors
	 * @param signs
	 * @param index
	 * @param paragraph_index
	 * @param repeat
	 * @return
	 */
	protected boolean isRepeated(ArrayList<Error> errors,
			 int index, int paragraph_index, boolean repeat) {
		for (Error err : errors) {
			for (Sign sign : err.signs) {
				if (sign.getIndex() == index && sign.getParagraph() == paragraph_index) {
					repeat = true;
				}
			}
		}
		return repeat;
	}

	/**
	 * 获得随机的段落索引
	 * 
	 * @param corpus_bfs
	 * @return
	 */
	protected int getRondParagraphIndex(ArrayList<StringBuffer> corpus_bfs) {
		int paragraph_index;
		paragraph_index = (int) (Math.random() * (corpus_bfs.size() - 1));
		return paragraph_index;
	}

	/**
	 * 在段内获得一个随机索引
	 * 
	 * @param corpus_bfs
	 * @param paragraph_index
	 * @return
	 */
	protected int getRondIndex(ArrayList<StringBuffer> corpus_bfs,
			int paragraph_index) {
		int index;
		index = (int) (Math.random() * (corpus_bfs.get(paragraph_index)
				.length() - 1));
		return index;
	}

	/**
	 * 
	 * @return perform success or not
	 */
	public boolean process(ArrayList<StringBuffer> corpus_bf,
			ArrayList<Error> errors) {
		System.out.println("需要重写process()");
		return false;
	}
}