package test;

/**
 * �������BDF�е����ڵ�ı�����Ϣ
 * @author ZHP
 * 2014��11��26��
 */
public class BDFResultNode {
	
	private int nodeIndex;
	private int nodeBranch;
	private int nodeDepth;
	private String nodeResult;
	
	//�չ��캯��
	public BDFResultNode(){}
	/**
	 * (�ڵ���ţ�������֧������������������������)
	 * @param nodeIndex
	 * @param nodeBranch
	 * @param nodeDepth
	 * @param nodeReString
	 */
	public BDFResultNode(int nodeIndex,int nodeBranch,int nodeDepth,String nodeReString){
		this.setNodeIndex(nodeIndex);
		this.nodeBranch  = nodeBranch;
		this.nodeDepth = nodeDepth;
		this.nodeResult = nodeReString;
	}
	
	public int getNodeBranch() {
		return nodeBranch;
	}
	public void setNodeBranch(int nodeBranch) {
		this.nodeBranch = nodeBranch;
	}
	public int getNodeDepth() {
		return nodeDepth;
	}
	public void setNodeDepth(int nodeDepth) {
		this.nodeDepth = nodeDepth;
	}
	public String getNodeResult() {
		return nodeResult;
	}
	public void setNodeResult(String nodeResult) {
		this.nodeResult = nodeResult;
	}
	public int getNodeIndex() {
		return nodeIndex;
	}
	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}
	
}
