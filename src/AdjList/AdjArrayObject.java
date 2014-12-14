package AdjList;

import KNNpackage.Knode;

public class AdjArrayObject {
	//出入度邻接表中的节点对象，记录节点的Index,和源节点到当前节点的边的权重
	int nodeIndex;
	int nodeWeight;
	//重写equals方法
	public boolean equals(Object obj){
		if(obj instanceof AdjArrayObject){
			AdjArrayObject u = (AdjArrayObject) obj;
			return this.getNodeIndex() == u.getNodeIndex();
		}
		return super.equals(obj);
	}
	
	public int getNodeIndex() {
		return nodeIndex;
	}
	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}
	public int getNodeWeight() {
		return nodeWeight;
	}
	public void setNodeWeight(int nodeWeight) {
		this.nodeWeight = nodeWeight;
	}
	
	
}
