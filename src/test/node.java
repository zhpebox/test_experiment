package test;

import KNNpackage.Knode;

public class node {

	private int index;
	private String Nname;

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof node){
			node u = (node) obj;
			return this.index == u.getIndex();
		}
		return super.equals(obj);
	}

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
