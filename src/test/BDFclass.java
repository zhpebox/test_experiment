package test;

import java.util.ArrayList;
import java.util.List;

public class BDFclass {

	private static int breadth;
	private static int depth;
	private ArrayList<Integer> CurrentQueue;
	/**
	 * 处理邻接表，广度优先遍历树
	 * @param 图中各个节点的邻接表 List<ArrayList<Integer>>
	 */
	public void IterateTheArrayList(List<ArrayList<Integer>> result) {

		// 遍历每个邻接表的头结点，作为当前节点
		for (ArrayList<Integer> tableLine : result) {
			//当前邻接表为空跳过该节点
			if (tableLine == null || tableLine.size()==0) continue;
			//每个节点用自己的队列，可以便于每次入队列时候，查询该节点是否已经被遍历过
			CurrentQueue = new ArrayList<Integer>();
			//Queue是一个处理方法的集合，真正的队列是 CurrentQueue
			Queue seqQueue = new Queue(CurrentQueue,0,0);
			int currentNode = tableLine.get(0);
			//初始化广度和广度
			breadth = 1;
			depth = 0;
			
			// 加结束标记。根节点currentNode EnQueue,取负作为行结束标志
			seqQueue.EnQueue(0 - tableLine.get(0).intValue());
			System.out.println("currentNode is "+currentNode);
			currentNode = tableLine.get(0);
			// 开始遍历当前节点的子树，计算depth和breadth
			// 2,队列不空，对头出队列
			while (!seqQueue.isEmpty()) {
				// 对头出队列，孩子入队列
				int temp = this.BDF_DeQueue(seqQueue, result);
				System.out.print(" " + temp);
			}
			System.out.println("\n Node "+currentNode+": breadth is " + breadth + " ; depth is " + depth);
		}
	}
	
	/**
	 * 队列对头节点出对列，孩子节点如队列，如当前节点是层次尾节点，深度+1，并在队尾设置新的层次结束标识。
	 * author ZHP
	 * 2014年11月26日
	 * @param seqQueue
	 * @param result
	 * @return
	 */
	public Integer BDF_DeQueue(Queue seqQueue,List<ArrayList<Integer>> result){
		//lineMark判断当前的是否行结束标志
		int lineMark = 0;
		int temp = seqQueue.DeQueue();
		if(temp<0) {
			temp = 0-temp;
			lineMark = 1;
			depth++;
		}
		//孩子入队列，遍历邻接表集合
		for (ArrayList<Integer> tableLine : result) {
			int isHasAdjList = 0; //判断是否找到当前节点的邻接表，如找到，处理完，直接跳出循环，不必在往下查找(保证每个节点的邻接表唯一)
			if (tableLine != null && tableLine.size() != 0) {
				int getOne = tableLine.get(0);
				if (temp == getOne) {
					for (int i = 1; i < tableLine.size(); i++) {
						//保证孩子节点数大于2，广度才变化
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
			//判断如果找到自己的邻接表，跳出循环
			if(isHasAdjList!=0) break;
		}
		//孩子节点为空的情况下，也将行终结表示加入队列，除非队列为空
		if(lineMark==1 && !seqQueue.isEmpty()){
			seqQueue.addLineMark();
		}
		return temp;
	}
}
