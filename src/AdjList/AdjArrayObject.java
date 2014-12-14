package AdjList;

import KNNpackage.Knode;

public class AdjArrayObject {
	//������ڽӱ��еĽڵ���󣬼�¼�ڵ��Index,��Դ�ڵ㵽��ǰ�ڵ�ıߵ�Ȩ��
	int nodeIndex;
	int nodeWeight;
	//��дequals����
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
