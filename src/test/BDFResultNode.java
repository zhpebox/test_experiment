package test;

/**
 * 用来存放BDF中单个节点的遍历信息
 * @author ZHP
 * 2014年11月26日
 */
public class BDFResultNode {
	
	private int nodeIndex;
	private int nodeBranch;
	private int nodeDepth;
	private String nodeResult;
	
	//空构造函数
	public BDFResultNode(){}
	/**
	 * (节点序号，遍历分支数，遍历层次数，遍历结果集)
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
