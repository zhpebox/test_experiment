package test;

import java.util.ArrayList;

//QueueNodeΪԪ��
public class Queue {

	private static int Qheader = 0;
	private static int Qtail = 0;
	private static ArrayList<Integer> queueList;
	
	//�޲ι���
	public Queue(){
		this.queueList = new ArrayList<Integer>();
	}
	//���ι��캯��
	public Queue( ArrayList<Integer> queueList, int header, int tail){
		this.queueList = queueList;
		this.Qheader = header;
		this.Qtail = tail;
	}
	
	//�����
	public void EnQueue(Integer one){
		queueList.add(one);
		Qtail++;
	}
	
	//������
	public Integer DeQueue(){
		Integer result = queueList.get(Qheader);
	//	��remove�ˣ������Ժ�ڵ�Ĳ�ѯ	
	//	queueList.remove(Qheader);
		Qheader++;
		return result;
	}
	
	//�����Ƿ�Ϊ��
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
	
	//��ѯ�������Ƿ���ֹ���ͬ��Ԫ��
	public boolean isExistTheSameOne(int one){
		boolean result = false;
		for(Integer e : queueList){
			if(Math.abs(one)==Math.abs(e))	
				result = true;
		}
		return result;
	}
}
