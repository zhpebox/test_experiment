package util;

import java.util.ArrayList;
import java.util.HashMap;
import test.edge;
import test.node;
import AdjList.AdjArrayObject;
import KNNpackage.Knode;

/**
 * 测试模块度
 * 
 * @author ZHP 2014年12月14日
 */
public class testQunweight {

	private HashMap<String, HashMap<String, String>> resultMap;
	private ArrayList<Knode> sourcedata;
	private ArrayList<node> Nodes;// 存储节点Node
	private ArrayList<edge> Edges;// 存储边Edges
	private ArrayList<AdjArrayObject>[] inDegreeList;
	private ArrayList<AdjArrayObject>[] outDegreeList;

	private int[][] adjMatrix;
	private int totalWeight = 1; // 所有边的权重之和

	public testQunweight(
			HashMap<String, HashMap<String, String>> resultMap,
			ArrayList<Knode> sourcedata, ArrayList<node> nodes,
			ArrayList<edge> edges,
			ArrayList<AdjArrayObject>[] inDegreeList,
			ArrayList<AdjArrayObject>[] outDegreeList) {
		this.resultMap = resultMap;
		this.sourcedata = sourcedata;
		this.Nodes = nodes;
		this.Edges = edges;
		this.inDegreeList = inDegreeList;
		this.outDegreeList = outDegreeList;
		int total = 0;
		this.totalWeight = edges.size();
		this.genMatrix();
	}

	/**
	 * 根据点边信息生成邻接矩阵 author ZHP 2015年1月19日
	 */
	public void genMatrix() {
		adjMatrix = new int[Nodes.size()][Nodes.size()];
		// 矩阵初始化为0；
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix[i].length; j++) {
				adjMatrix[i][j] = 0;
			}
		}
		// 更新已有边信息
		for (edge e : Edges) {
			int start = e.getS_node();
			int end = e.getE_node();
			int weight = e.getWeight()!=0?1:0;
			adjMatrix[start - 1][end - 1] = weight;
		}
	}

	/**
	 * 克洛内克函数,比较两个节点的类别相同与否，相同得1，不同得0 author ZHP 2015年1月19日
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public int KroneckerFun(Knode one, Knode two) {
		String oneCategory = "";
		String twoCategory = "";
		if (resultMap.containsKey(one.getNodeIndex() + "")) {
			oneCategory = resultMap.get(one.getNodeIndex() + "")
					.get("category");
		}
		if (resultMap.containsKey(two.getNodeIndex() + "")) {
			twoCategory = resultMap.get(two.getNodeIndex() + "")
					.get("category");
		}
		if (oneCategory.equals(twoCategory)) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 计算模块化Q函数（权值有向图） author ZHP 2015年1月19日
	 * 
	 * @param inDegreeList
	 * @param outDegreeList
	 * @return
	 */
	public float softwareQFunction() {
		float Q_gen = 0; // 最终模块度

		for (Knode start : sourcedata) { // 双层循环遍历，逐个求
			for (Knode end : sourcedata) {
				int out = outDegreeList[start.getNodeIndex()] == null?0:outDegreeList[start.getNodeIndex()].size();
				int in = inDegreeList[end.getNodeIndex()] == null?0:inDegreeList[end.getNodeIndex()].size();
				int existWeight = adjMatrix[start.getNodeIndex() - 1][end.getNodeIndex() - 1];
				System.out.println(out+"   "+in+"  "+existWeight);
				System.out.println((existWeight - (float)out * in / totalWeight) * KroneckerFun(start, end));
				Q_gen += (existWeight - (float)out * in / totalWeight) * KroneckerFun(start, end);
			}
		}
		Q_gen = Q_gen / totalWeight;
		return Q_gen;
	}
}
