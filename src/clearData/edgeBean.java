package clearData;

import KNNpackage.Knode;

public class edgeBean {
	
	private String startNode;
	private String endNode;
	private Integer weight;
	
	public edgeBean(String startNode, String endNode, Integer weight) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.weight = weight;
	}

	//÷ÿ–¥equals∑Ω∑®
		public boolean equals(Object obj){
			if(obj instanceof edgeBean){
				edgeBean u = (edgeBean) obj;
				if(this.getStartNode().equals(u.getStartNode()) && this.getEndNode().equals(u.getEndNode()))
					return true;
			}
			return super.equals(obj);
		}
	
	
	public String getStartNode() {
		return startNode;
	}

	public void setStartNode(String startNode) {
		this.startNode = startNode;
	}

	public String getEndNode() {
		return endNode;
	}

	public void setEndNode(String endNode) {
		this.endNode = endNode;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	
	
	
}
