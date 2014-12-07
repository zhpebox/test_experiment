package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 将边集合，生成邻接表
 * @author Administrator
 */
public class ListToAdjacencyList {
	
	/**
	 * 本次邻接表将只考率入度邻居(只根据被依赖的关系分模块)
	 * @param EdgeList File
	 * @return AdjacencyList
	 * （更改，考虑direction ,  遍历方式从针对当前节点循环遍历边，改为一次遍历边集合，给node数据赋值，幅度大大降低）
	 */
	public List<ArrayList<Integer>> changToAdjList(ArrayList<edge> edgesListSource,ArrayList<node> nodeList){
		
		ArrayList<edge> EdgesList =(ArrayList<edge>) edgesListSource.clone();
		Collections.sort(EdgesList, comparator);//排序没成功
		List<ArrayList<Integer>> resultList = new ArrayList<ArrayList<Integer>>();
		
		//暂存StartNode
		int temp = 0;
		//临时Node链表，存储当前节点的邻接点
		ArrayList<Integer> tempList = new ArrayList<Integer>();
 		//遍历边集合
		for(edge e: EdgesList){
			//读取当前边的起始点
			int startNode = e.getS_node();
			int endNode = e.getE_node();
			//是 头结点=temp    	终结点加入临时Node链表
			if(startNode==temp){
				tempList.add(endNode);
			//否    	1.临时Node链表加入到结果List,
			//		2.临时Node链表重建，加入当前节点作为头结点
			}else{
				resultList.add(tempList);
				tempList = new ArrayList<Integer>();
				tempList.add(startNode);
				tempList.add(endNode);
			}
			temp = startNode;
		}
		//最后一行入ResultSet
		resultList.add(tempList);
		tempList = new ArrayList<Integer>();
		return resultList;
	}
	/**
	 * Output The AdjacencyList's data
	 * @param resultList
	 */
	public void outPutTheResultArray(List<ArrayList<Integer>> resultList){
		for(ArrayList<Integer> oneList : resultList){
			for(Integer node : oneList){
				System.out.print(node+" -> ");
			}
			System.out.println();
		}
	}
	/**
	 *设置边集合排序，方便邻接表的生成
	 */
	private Comparator<edge> comparator = new Comparator<edge>() {
		public int compare(edge o1, edge o2) {
			if (o1.getS_node() >= o2.getS_node()) {
				return 1;
			} else {
				return 0;
			}
		}
	};
}
