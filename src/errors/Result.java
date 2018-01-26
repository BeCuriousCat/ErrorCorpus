package corpusgeneration;

public class Result{
	int location;
	String value;
	
	public Result(int location, String value){
		this.location = location;
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}
}
