package KNNpackage;

import java.util.Map;

public class Knode {
	
	private int nodeIndex;
	private String nodeName;
	private String categoryName = "X";//Ĭ����δ���ֵĽڵ�ķ���ΪX
	
	//��дequals����
	public boolean equals(Object obj){
		if(obj instanceof Knode){
			Knode u = (Knode) obj;
			return this.getNodeIndex() == u.nodeIndex;
		}
		return super.equals(obj);
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getNodeIndex() {
		return nodeIndex;
	}
	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}
	
}
