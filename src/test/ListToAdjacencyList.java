package test;

import java.util.ArrayList;
import java.util.List;

/**
 * ���߼��ϣ������ڽӱ�
 * @author Administrator
 */
public class ListToAdjacencyList {

	/**
	 * @param EdgeList File
	 * @return AdjacencyList
	 */
	public List<ArrayList<Integer>> changToAdjList(ArrayList<edge> EdgesList){
		List<ArrayList<Integer>> resultList = new ArrayList<ArrayList<Integer>>();

		//�ݴ�StartNode
		int temp = 0;
		//��ʱNode����
		ArrayList<Integer> tempList = new ArrayList<Integer>();
 		//�����߼���
		for(edge e: EdgesList){
			//��ȡ��ǰ�ߵ���ʼ��
			int startNode = e.getS_node();
			int endNode = e.getE_node();
			//�� ͷ���=temp    	�ս�������ʱNode����
			if(startNode==temp){
				tempList.add(endNode);
			//��    	1.��ʱNode������뵽���List,
			//		2.��ʱNode�����ؽ������뵱ǰ�ڵ���Ϊͷ���
			}else{
				resultList.add(tempList);
				tempList = new ArrayList<Integer>();
				tempList.add(startNode);
				tempList.add(endNode);
			}
			temp = startNode;
		}
		//���һ����ResultSet
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
