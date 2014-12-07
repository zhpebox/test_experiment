package test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * compute the neighbour of each node 
 * @author ZHP
 * 2014��11��22��
 */
public class edgeToNeighbourList {
	//resort called node of each node once call
	private static ArrayList<ArrayList<String>> adjList = new ArrayList<ArrayList<String>>();//�洢��Edges

	public static ArrayList<ArrayList<String>> getAdjList() {
		return adjList;
	}

	public static void setAdjList(ArrayList<ArrayList<String>> adjList) {
		edgeToNeighbourList.adjList = adjList;
	}
	
	/**
	 * compute node's first adjacent node
	 * author ZHP
	 * 2014��12��6��
	 * @param Edges
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,HashMap<String,Integer>> computeAdjNode(ArrayList<edge> Edges) throws Exception{
		
		//HashMap<currentNode,HashMap<AdjNode,weight>> just not care for direction
		HashMap<String,HashMap<String,Integer>> adjResult= new HashMap<String,HashMap<String,Integer>>();
		//traverse callEdge for computing the node's neighbours to write in HashMap()
		for(edge calls : Edges){
			System.out.println("the current call is "+calls.getS_node()+" --- "+calls.getE_node()+" : "+calls.getWeight());
			String node1 = calls.getS_node()+"";
			String node2 = calls.getE_node()+"";
			Integer weight = calls.getWeight();
			//node1��hash��
			HashMap<String,Integer> values = new HashMap<String,Integer>();
			values.put(node2, weight);
			if(adjResult.containsKey(node1)){
				//Ĭ����û��ͬһ�����ù�ϵ�Ⱥ����γ���
				adjResult.get(node1).put(node2, weight);
			}else{
				adjResult.put(node1, values);
			}
			//node2��hash��
			values = new HashMap<String,Integer>();
			values.put(node1, weight);
			if(adjResult.containsKey(node2)){
				//Ĭ����û��ͬһ�����ù�ϵ�Ⱥ����γ���
				adjResult.get(node2).put(node1, weight);
			}else{
				adjResult.put(node2, values);
			}
		}
		return adjResult;
	}
	
	
	
}
