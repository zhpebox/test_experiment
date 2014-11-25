package test;

import java.util.ArrayList;

//QueueNode为元素
public class Queue {

	private static int Qheader = 0;
	private static int Qtail = 0;
	private static ArrayList<Integer> queueList;
	
	//无参构造
	public Queue(){
		this.queueList = new ArrayList<Integer>();
	}
	//带参构造函数
	public Queue( ArrayList<Integer> queueList, int header, int tail){
		this.queueList = queueList;
		this.Qheader = header;
		this.Qtail = tail;
	}
	
	//入队列
	public void EnQueue(Integer one){
		queueList.add(one);
		Qtail++;
	}
	
	//出队列
	public Integer DeQueue(){
		Integer result = queueList.get(Qheader);
	//	不remove了，便于以后节点的查询	
	//	queueList.remove(Qheader);
		Qheader++;
		return result;
	}
	
	//队列是否为空
	public boolean isEmpty(){
		if(Qheader==Qtail){
			return true;
		}
		return false;
	}
	
	//AddLineMark
	public void addLineMark(){
		int size = queueList.size();
		int temp = queueList.get(size-1);
		queueList.remove(size-1);
		queueList.add(0-temp);
	}
	
	//查询队列中是否出现过相同的元素
	public boolean isExistTheSameOne(int one){
		boolean result = false;
		for(Integer e : queueList){
			if(Math.abs(one)==Math.abs(e))	
				result = true;
		}
		return result;
	}
}
