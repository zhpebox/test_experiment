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
	 * 处理邻接表，广度优先遍历树
	 * @param 图中各个节点的邻接表 List<ArrayList<Integer>>
	 * 修改：2014.12.7 改变参数类型，按照出度来遍历网络，参数改为数组类型，
	 * 方便查找，直接按照节点的index对应数组下标
	 */
	public ArrayList<BDFResultNode> IterateTheArrayList(ArrayList<AdjArrayObject>[] outDegreeList,ArrayList<node> nodeList) {
		
		ArrayList<BDFResultNode> BDFResult = new ArrayList<BDFResultNode>(); //结果集
		// 遍历nodeList，作为当前节点
		for (node nodeListObj : nodeList) {
			//每个节点用自己的队列，可以便于每次入队列时候，查询该节点是否已经被遍历过
			CurrentQueue = new ArrayList<Integer>();
			//Queue是一个处理方法的对象，真正的队列模型是 CurrentQueue，下述初始化CurrentQueue
			Queue seqQueue = new Queue(CurrentQueue,0,0);
			int currentNode = nodeListObj.getIndex();
			//初始化广度和广度
			breadth = 1;
			depth = 0;
			outDegree = outDegreeList[nodeListObj.getIndex()]==null?0:outDegreeList[nodeListObj.getIndex()].size();
			
			// 加结束标记。根节点currentNode EnQueue,取负作为行结束标志
			seqQueue.EnQueue(0 -currentNode);
			System.out.println("currentNode is "+currentNode);
			
			//初始化遍历结果集
			String nodeResult = "";
			
			// 开始遍历当前节点的子树，计算depth和breadth
			// 2,队列不空，对头出队列
			while (!seqQueue.isEmpty()) {
				// 对头出队列，孩子入队列
				int temp = this.BDF_DeQueue(seqQueue, outDegreeList);
				System.out.print(" " + temp);
				nodeResult = nodeResult+temp+",";
			}
			System.out.println("\nNode "+currentNode+": breadth is " + breadth + " ; depth is " + depth);
			//当前节点的遍历结果存入结果集
			BDFResultNode currentResult = new BDFResultNode(currentNode,breadth,depth,outDegree,nodeResult);
			BDFResult.add(currentResult);
		}
		return BDFResult;
	}
	
	/**
	 * 队列对头节点出对列，孩子节点如队列，如当前节点是层次尾节点，深度+1，并在队尾设置新的层次结束标识。
	 * author ZHP
	 * 2014年11月26日
	 * @param seqQueue
	 * @param outDegreeList
	 * @return
	 * 2014.12.7 修改了参数类型 从 List 转为数组，增加速度
	 */
	public Integer BDF_DeQueue(Queue seqQueue,ArrayList<AdjArrayObject>[] outDegreeList ){
		//lineMark判断当前的是否行结束标志
		int lineMark = 0;
		int temp = seqQueue.DeQueue();
		if(temp<0) {
			temp = 0-temp;
			lineMark = 1;
			depth++;
		}
		//孩子入队列，遍历邻接表集合
			
		//暂存当前的邻接表
		ArrayList<AdjArrayObject> currentLine = outDegreeList[temp];
		if(currentLine!=null && currentLine.size()!=0){
			for (int i = 0; i < currentLine.size(); i++) {
				//保证孩子节点数大于2，广度才变化
				if(i>0) breadth++;
				if(seqQueue.isExistTheSameOne(currentLine.get(i).getNodeIndex())){
					continue;
				}else{
					seqQueue.EnQueue(currentLine.get(i).getNodeIndex());
				}
			}
		}
		
		//孩子节点为空的情况下，也将行终结表示加入队列，除非队列为空
		if(lineMark==1 && !seqQueue.isEmpty()){
			seqQueue.addLineMark();
		}
		return temp;
	}
}
