package test;

public class node {

	private int index;
	private String Nname;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getNname() {
		return Nname;
	}

	public void setNname(String nname) {
		Nname = nname;
	}

	public node(int index, String Nname) {
		this.index = index;
		this.Nname = Nname;
	}
	
	public node(){}
}
