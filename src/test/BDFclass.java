package test;

import java.util.ArrayList;
import java.util.List;

public class BDFclass {

	private static int breadth = 1;
	private static int depth;
	/**
	 * 处理邻接表，广度优先遍历树
	 */
	public void IterateTheArrayList(List<ArrayList<Integer>> result) {
		// 自己点数的广度
		int currentNode = 0;
		// 遍历每个邻接表的头结点，作为当前节点
		for (ArrayList<Integer> tableLine : result) {
			//每个节点用自己的队列，可以便于每次入队列时候，查询该节点是否已经被遍历过
			ArrayList<Integer> CurrentQueue = new ArrayList<Integer>();
			Queue seqQueue = new Queue(CurrentQueue,0,0);
			if (tableLine != null && tableLine.size()!=0) {
				breadth = 0;
				depth = 0;
				// 1,根节点currentNode EnQueue,取负作为行结束标志
				seqQueue.EnQueue(0 - tableLine.get(0).intValue());
				currentNode = tableLine.get(0);
				System.out.println("currentNode is "+currentNode);
				// 开始遍历当前节点的子树，计算depth和breadth
				// 2,队列不空，对头出队列
				while (!seqQueue.isEmpty()) {
					// 对头出队列，孩子入队列
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
		//孩子入队列
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
		//孩子节点为空的情况下，也将行终结表示加入队列，除非队列为空
		if(lineend==1 && !seqQueue.isEmpty()){
			seqQueue.addLineMark();
		}
		return temp;
	}
}
