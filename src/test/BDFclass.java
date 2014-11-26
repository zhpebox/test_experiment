package test;

import java.util.ArrayList;
import java.util.List;

public class BDFclass {

	private static int breadth;
	private static int depth;
	private ArrayList<Integer> CurrentQueue;
	/**
	 * �����ڽӱ�������ȱ�����
	 * @param ͼ�и����ڵ���ڽӱ� List<ArrayList<Integer>>
	 */
	public void IterateTheArrayList(List<ArrayList<Integer>> result) {

		// ����ÿ���ڽӱ��ͷ��㣬��Ϊ��ǰ�ڵ�
		for (ArrayList<Integer> tableLine : result) {
			//��ǰ�ڽӱ�Ϊ�������ýڵ�
			if (tableLine == null || tableLine.size()==0) continue;
			//ÿ���ڵ����Լ��Ķ��У����Ա���ÿ�������ʱ�򣬲�ѯ�ýڵ��Ƿ��Ѿ���������
			CurrentQueue = new ArrayList<Integer>();
			//Queue��һ���������ļ��ϣ������Ķ����� CurrentQueue
			Queue seqQueue = new Queue(CurrentQueue,0,0);
			int currentNode = tableLine.get(0);
			//��ʼ����Ⱥ͹��
			breadth = 1;
			depth = 0;
			
			// �ӽ�����ǡ����ڵ�currentNode EnQueue,ȡ����Ϊ�н�����־
			seqQueue.EnQueue(0 - tableLine.get(0).intValue());
			System.out.println("currentNode is "+currentNode);
			currentNode = tableLine.get(0);
			// ��ʼ������ǰ�ڵ������������depth��breadth
			// 2,���в��գ���ͷ������
			while (!seqQueue.isEmpty()) {
				// ��ͷ�����У����������
				int temp = this.BDF_DeQueue(seqQueue, result);
				System.out.print(" " + temp);
			}
			System.out.println("\n Node "+currentNode+": breadth is " + breadth + " ; depth is " + depth);
		}
	}
	
	/**
	 * ���ж�ͷ�ڵ�����У����ӽڵ�����У��統ǰ�ڵ��ǲ��β�ڵ㣬���+1�����ڶ�β�����µĲ�ν�����ʶ��
	 * author ZHP
	 * 2014��11��26��
	 * @param seqQueue
	 * @param result
	 * @return
	 */
	public Integer BDF_DeQueue(Queue seqQueue,List<ArrayList<Integer>> result){
		//lineMark�жϵ�ǰ���Ƿ��н�����־
		int lineMark = 0;
		int temp = seqQueue.DeQueue();
		if(temp<0) {
			temp = 0-temp;
			lineMark = 1;
			depth++;
		}
		//��������У������ڽӱ���
		for (ArrayList<Integer> tableLine : result) {
			int isHasAdjList = 0; //�ж��Ƿ��ҵ���ǰ�ڵ���ڽӱ����ҵ��������ֱ꣬������ѭ�������������²���(��֤ÿ���ڵ���ڽӱ�Ψһ)
			if (tableLine != null && tableLine.size() != 0) {
				int getOne = tableLine.get(0);
				if (temp == getOne) {
					for (int i = 1; i < tableLine.size(); i++) {
						//��֤���ӽڵ�������2����Ȳű仯
						if(i>1) breadth++;
						
						if(seqQueue.isExistTheSameOne(tableLine.get(i))){
							continue;
						}else{
							seqQueue.EnQueue(tableLine.get(i));
						}
					}
					isHasAdjList = 1;
				}
			}
			//�ж�����ҵ��Լ����ڽӱ�����ѭ��
			if(isHasAdjList!=0) break;
		}
		//���ӽڵ�Ϊ�յ�����£�Ҳ�����ս��ʾ������У����Ƕ���Ϊ��
		if(lineMark==1 && !seqQueue.isEmpty()){
			seqQueue.addLineMark();
		}
		return temp;
	}
}
