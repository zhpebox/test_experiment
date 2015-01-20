package clearData;

public class nodeBean {
	private int nindex;
	private String nname;
	
	
	public nodeBean(int nindex, String nname){
		this.nindex = nindex;
		this.nname = nname;
	}
 
	public int getNindex() {
		return nindex;
	}
	public void setNindex(int nindex) {
		this.nindex = nindex;
	}
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	
}
