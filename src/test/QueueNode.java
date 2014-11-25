package test;

//暂时用int作为元素
public class QueueNode {
	private int value;
	private QueueNode nextObj;
	
	public QueueNode(int value){
		this.value = value;
	}
	
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public QueueNode getNextObj() {
		return nextObj;
	}
	public void setNextObj(QueueNode nextObj) {
		this.nextObj = nextObj;
	}
	
	
}
