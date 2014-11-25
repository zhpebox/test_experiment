package test;

import java.util.ArrayList;
import java.util.List;

/**
 * 将边集合，生成邻接表
 * @author Administrator
 */
public class ListToAdjacencyList {

	/**
	 * @param EdgeList File
	 * @return AdjacencyList
	 */
	public List<ArrayList<Integer>> changToAdjList(ArrayList<edge> EdgesList){
		List<ArrayList<Integer>> resultList = new ArrayList<ArrayList<Integer>>();

		//暂存StartNode
		int temp = 0;
		//临时Node链表
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
}
