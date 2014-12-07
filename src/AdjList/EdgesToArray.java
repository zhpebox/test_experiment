package AdjList;

import java.util.ArrayList;

import test.edge;
import test.node;

public class EdgesToArray {
	
	private ArrayList<AdjArrayObject>[] indegreeArray;
	private ArrayList<AdjArrayObject>[] outdegreeArray;
	

	/**
	 * ���սڵ�ĳ���������ڽӱ�
	 * author ZHP
	 * 2014��12��7��
	 * @param Edges
	 * @param maxNodeIndex
	 */
	public void EdgeToAdjList(ArrayList<edge> Edges,int maxNodeIndex){
		
		System.out.println(maxNodeIndex);
		//��ʼ������1 Ϊ�˱�֤���һ���ڵ������������±���ͬ
		indegreeArray = new ArrayList[maxNodeIndex+1];
		outdegreeArray = new ArrayList[maxNodeIndex+1];
		
		for(edge e : Edges){
			
			//����
			System.out.println("Out "+e.getS_node());
			AdjArrayObject outTemp = new AdjArrayObject();
			outTemp.setNodeIndex(e.getE_node());
			outTemp.setNodeWeight(e.getWeight());
			if(outdegreeArray[e.getS_node()]==null) 
				outdegreeArray[e.getS_node()] = new ArrayList<AdjArrayObject>();
			outdegreeArray[e.getS_node()].add(outTemp);
			
			//���
			System.out.println("Ins "+e.getE_node());
			AdjArrayObject inTemp = new AdjArrayObject();
			inTemp.setNodeIndex(e.getS_node());
			inTemp.setNodeWeight(e.getWeight());
			if(indegreeArray[e.getE_node()]==null) 
				indegreeArray[e.getE_node()] = new ArrayList<AdjArrayObject>();
			indegreeArray[e.getE_node()].add(inTemp);
			
		}
		
		//��ӡ���
		printResult();
		
	}
	
	/**
	 * ��ӡ����Ƚ������
	 * author ZHP
	 * 2014��12��7��
	 */
	public void printResult(){
		System.out.println("��ӡ\"���\"�ڽӱ�");
		int is = 0;	//is�����±��ʶ��ǰ�ڵ�
		for(ArrayList<AdjArrayObject> one  : indegreeArray){
			System.out.print("\n current node is "+is);
			if(one == null){
				System.out.print(" the line  is null");
			}else{
				for(AdjArrayObject e:one){
					System.out.print(" "+e.getNodeIndex()+"_"+e.getNodeWeight()+"  ");
				}
			}
			is++;
		}
		System.out.println("��ӡ\"����\"�ڽӱ�");
		is = 0;
		for(ArrayList<AdjArrayObject> one  : outdegreeArray){
			System.out.print("\n current node is "+is);
			if(one == null){
				System.out.print(" the line  is null");
			}else{
				for(AdjArrayObject e:one){
					System.out.print(" "+e.getNodeIndex()+"_"+e.getNodeWeight()+"  ");
				}
			}
			is++;
		}
	}
	
	public ArrayList<AdjArrayObject>[] getIndegreeArray() {
		return indegreeArray;
	}
	
	public ArrayList<AdjArrayObject>[] getOutdegreeArray() {
		return outdegreeArray;
	}
}
