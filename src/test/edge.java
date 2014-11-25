package test;

public class edge {
	private int S_node;
	private int E_node;
	private int weight;
	
	
	
	public edge(int start,int end,String weigh){
		setS_node(start);
		setE_node(end);
		
		String[] wei = weigh.split(" ");
		setWeight(Integer.parseInt(wei[0]));
	}
	
//	public edge(String[] callEdge){
//		this.S_node = Integer.parseInt(callEdge[0]);
//		this.E_node = Integer.parseInt(callEdge[1]);
//		this.weight = Integer.parseInt(callEdge[2]);
//	}


	public int getS_node() {
		return S_node;
	}



	public void setS_node(int s_node) {
		S_node = s_node;
	}



	public int getE_node() {
		return E_node;
	}



	public void setE_node(int e_node) {
		E_node = e_node;
	}



	public int getWeight() {
		return weight;
	}



	public void setWeight(int weight) {
		this.weight = weight;
	}
}
