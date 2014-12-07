package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ���߼��ϣ������ڽӱ�
 * @author Administrator
 */
public class ListToAdjacencyList {
	
	/**
	 * �����ڽӱ�ֻ��������ھ�(ֻ���ݱ������Ĺ�ϵ��ģ��)
	 * @param EdgeList File
	 * @return AdjacencyList
	 * �����ģ�����direction ,  ������ʽ����Ե�ǰ�ڵ�ѭ�������ߣ���Ϊһ�α����߼��ϣ���node���ݸ�ֵ�����ȴ�󽵵ͣ�
	 */
	public List<ArrayList<Integer>> changToAdjList(ArrayList<edge> edgesListSource,ArrayList<node> nodeList){
		
		ArrayList<edge> EdgesList =(ArrayList<edge>) edgesListSource.clone();
		Collections.sort(EdgesList, comparator);//����û�ɹ�
		List<ArrayList<Integer>> resultList = new ArrayList<ArrayList<Integer>>();
		
		//�ݴ�StartNode
		int temp = 0;
		//��ʱNode�����洢��ǰ�ڵ���ڽӵ�
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
	/**
	 *���ñ߼������򣬷����ڽӱ������
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
