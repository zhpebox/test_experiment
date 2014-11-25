package test;

import java.util.ArrayList;
import java.util.List;

public class BDFclass {

	private static int breadth = 1;
	private static int depth;
	/**
	 * �����ڽӱ�������ȱ�����
	 */
	public void IterateTheArrayList(List<ArrayList<Integer>> result) {
		// �Լ������Ĺ��
		int currentNode = 0;
		// ����ÿ���ڽӱ��ͷ��㣬��Ϊ��ǰ�ڵ�
		for (ArrayList<Integer> tableLine : result) {
			//ÿ���ڵ����Լ��Ķ��У����Ա���ÿ�������ʱ�򣬲�ѯ�ýڵ��Ƿ��Ѿ���������
			ArrayList<Integer> CurrentQueue = new ArrayList<Integer>();
			Queue seqQueue = new Queue(CurrentQueue,0,0);
			if (tableLine != null && tableLine.size()!=0) {
				breadth = 0;
				depth = 0;
				// 1,���ڵ�currentNode EnQueue,ȡ����Ϊ�н�����־
				seqQueue.EnQueue(0 - tableLine.get(0).intValue());
				currentNode = tableLine.get(0);
				System.out.println("currentNode is "+currentNode);
				// ��ʼ������ǰ�ڵ������������depth��breadth
				// 2,���в��գ���ͷ������
				while (!seqQueue.isEmpty()) {
					// ��ͷ�����У����������
					int temp = this.BDF_DeQueue(seqQueue, result);
					System.out.print(" " + temp);
				}
			}
			System.out.println("\n Node "+currentNode+": breadth is " + breadth + " ; depth is " + depth);
		}
	}
	
	public Integer BDF_DeQueue(Queue seqQueue,List<ArrayList<Integer>> result){
		int lineend = 0;
		int temp = seqQueue.DeQueue();
		if(temp<0) {
			temp = 0-temp;
			lineend = 1;
			depth++;
		}
		//���������
		for (ArrayList<Integer> tableLine : result) {
			if (tableLine != null && tableLine.size() != 0) {
				int getOne = tableLine.get(0);
				if (temp == getOne) {
					for (int i = 1; i < tableLine.size(); i++) {
						if(seqQueue.isExistTheSameOne(tableLine.get(i))){
							continue;
						}else{
							seqQueue.EnQueue(tableLine.get(i));
							breadth++;
						}
					}
				}
			}
		}
		//���ӽڵ�Ϊ�յ�����£�Ҳ�����ս��ʾ������У����Ƕ���Ϊ��
		if(lineend==1 && !seqQueue.isEmpty()){
			seqQueue.addLineMark();
		}
		return temp;
	}
}
