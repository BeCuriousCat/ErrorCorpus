package errors;

public class Sign {
	private int paragraph_index;
	private int index;
	private String correct_word;
	private String wrong_word;
	public int getParagraph() {
		return paragraph_index;
	}
	public void setParagraph(int paragraph) {
		this.paragraph_index = paragraph;
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
		this.paragraph_index = paragraph;
		this.index = index;
		this.correct_word = correct_word;
		this.wrong_word = wrong_word;
	}
	@Override
	public String toString() {
		return "Sign [paragraph_index=" + paragraph_index + ", index=" + index
				+ ", correct_word=" + correct_word + ", wrong_word="
				+ wrong_word + "]";
	}
	
	
}