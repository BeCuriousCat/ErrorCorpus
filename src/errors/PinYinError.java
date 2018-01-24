package errors;

import java.util.ArrayList;

public class PinYinError extends Error{

	public PinYinError(String name, double rate) {
		super(name, rate);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean process(ArrayList<StringBuffer> corpus_bf,
			ArrayList<Error> errors) {
		// TODO Auto-generated method stub
		
		return false;
	}
	
}
