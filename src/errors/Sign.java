package errors;

public class Sign {
	private int paragraph;
	private int index;
	private String correct_word;
	private String wrong_word;
	public int getParagraph() {
		return paragraph;
	}
	public void setParagraph(int paragraph) {
		this.paragraph = paragraph;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getCorrect_word() {
		return correct_word;
	}
	public void setCorrect_word(String correct_word) {
		this.correct_word = correct_word;
	}
	public String getWrong_word() {
		return wrong_word;
	}
	public void setWrong_word(String wrong_word) {
		this.wrong_word = wrong_word;
	}
	public Sign(int paragraph, int index, String correct_word, String wrong_word) {
		super();
		this.paragraph = paragraph;
		this.index = index;
		this.correct_word = correct_word;
		this.wrong_word = wrong_word;
	}
	
	
}
