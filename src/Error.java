import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public abstract class Error {
	/**
	 * @param name describe the error name
	 * @param rate the rate of error in the whole corpus
	 * @param error_size the number of this error
	 * @param describe a error with position, wrong word and correct word
	 */
	private String name = "";
	private int error_size = 0;
	private double error_rate = 0;
	private ArrayList<Sign> signs = null;
	
	public Error(String name, double rate) {
		super();
		this.name = name;
		this.error_rate = rate;
	}
	
	public String getName() {
		return name;
	}

	public double getError_rate() {
		return error_rate;
	}

	public void setError_rate(double error_rate) {
		this.error_rate = error_rate;
	}

	public ArrayList<Sign> sign() {
		return signs;
	}

	public void setSigns(ArrayList<Sign> signs) {
		this.signs = signs;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getError_size() {
		return error_size;
	}
	public void setError_size(int corpus_size) {
		this.error_size = (int) (corpus_size * this.error_rate);
	}
	
	public ArrayList<Sign> getSigns() {
		return signs;
	}

	public String toString(){
		String str = "Error: "+name+"\n ";
		for (Sign sign : signs) {
			str += "µÚ"+sign.getParagraph()+"¶Î£¬µÚ"+sign.getIndex()+"¸ö´Ê£º"
					+sign.getCorrect_word()+"¡ª>"+sign.getWrong_word()+"\n";
		}
		return str;
	}
	
	/**
	 * 
	 * @return perform success or not
	 */
	public abstract boolean process(ArrayList<StringBuffer> corpus_bf,ArrayList<Error> errors);
 }
