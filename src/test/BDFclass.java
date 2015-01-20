package test;

import java.util.ArrayList;
import java.util.List;

import AdjList.AdjArrayObject;

public class BDFclass {

	private static int breadth;
	private static int depth;
	private static int outDegree;
	private ArrayList<Integer> CurrentQueue;
	
	/**
	 * �����ڽӱ�������ȱ�����
	 * @param ͼ�и����ڵ���ڽӱ� List<ArrayList<Integer>>
	 * �޸ģ�2014.12.7 �ı�������ͣ����ճ������������磬������Ϊ�������ͣ�
	 * ������ң�ֱ�Ӱ��սڵ��index��Ӧ�����±�
	 */
	public ArrayList<BDFResultNode> IterateTheArrayList(ArrayList<AdjArrayObject>[] outDegreeList,ArrayList<node> nodeList) {
		
		ArrayList<BDFResultNode> BDFResult = new ArrayList<BDFResultNode>(); //�����
		// ����nodeList����Ϊ��ǰ�ڵ�
		for (node nodeListObj : nodeList) {
			//ÿ���ڵ����Լ��Ķ��У����Ա���ÿ�������ʱ�򣬲�ѯ�ýڵ��Ƿ��Ѿ���������
			CurrentQueue = new ArrayList<Integer>();
			//Queue��һ���������Ķ��������Ķ���ģ���� CurrentQueue��������ʼ��CurrentQueue
			Queue seqQueue = new Queue(CurrentQueue,0,0);
			int currentNode = nodeListObj.getIndex();
			//��ʼ����Ⱥ͹��
			breadth = 1;
			depth = 0;
			outDegree = outDegreeList[nodeListObj.getIndex()]==null?0:outDegreeList[nodeListObj.getIndex()].size();
			
			// �ӽ�����ǡ����ڵ�currentNode EnQueue,ȡ����Ϊ�н�����־
			seqQueue.EnQueue(0 -currentNode);
			System.out.println("currentNode is "+currentNode);
			
			//��ʼ�����������
			String nodeResult = "";
			
			// ��ʼ������ǰ�ڵ������������depth��breadth
			// 2,���в��գ���ͷ������
			while (!seqQueue.isEmpty()) {
				// ��ͷ�����У����������
				int temp = this.BDF_DeQueue(seqQueue, outDegreeList);
				System.out.print(" " + temp);
				nodeResult = nodeResult+temp+",";
			}
			System.out.println("\nNode "+currentNode+": breadth is " + breadth + " ; depth is " + depth);
			//��ǰ�ڵ�ı��������������
			BDFResultNode currentResult = new BDFResultNode(currentNode,breadth,depth,outDegree,nodeResult);
			BDFResult.add(currentResult);
		}
		return BDFResult;
	}
	
	/**
	 * ���ж�ͷ�ڵ�����У����ӽڵ�����У��統ǰ�ڵ��ǲ��β�ڵ㣬���+1�����ڶ�β�����µĲ�ν�����ʶ��
	 * author ZHP
	 * 2014��11��26��
	 * @param seqQueue
	 * @param outDegreeList
	 * @return
	 * 2014.12.7 �޸��˲������� �� List תΪ���飬�����ٶ�
	 */
	public Integer BDF_DeQueue(Queue seqQueue,ArrayList<AdjArrayObject>[] outDegreeList ){
		//lineMark�жϵ�ǰ���Ƿ��н�����־
		int lineMark = 0;
		int temp = seqQueue.DeQueue();
		if(temp<0) {
			temp = 0-temp;
			lineMark = 1;
			depth++;
		}
		//��������У������ڽӱ���
			
		//�ݴ浱ǰ���ڽӱ�
		ArrayList<AdjArrayObject> currentLine = outDegreeList[temp];
		if(currentLine!=null && currentLine.size()!=0){
			for (int i = 0; i < currentLine.size(); i++) {
				//��֤���ӽڵ�������2����Ȳű仯
				if(i>0) breadth++;
				if(seqQueue.isExistTheSameOne(currentLine.get(i).getNodeIndex())){
					continue;
				}else{
					seqQueue.EnQueue(currentLine.get(i).getNodeIndex());
				}
			}
		}
		
		//���ӽڵ�Ϊ�յ�����£�Ҳ�����ս��ʾ������У����Ƕ���Ϊ��
		if(lineMark==1 && !seqQueue.isEmpty()){
			seqQueue.addLineMark();
		}
		return temp;
	}
}
