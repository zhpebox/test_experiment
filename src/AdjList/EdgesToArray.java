package AdjList;

import java.util.ArrayList;

import test.edge;
import test.node;

public class EdgesToArray {
	
	private ArrayList<AdjArrayObject>[] indegreeArray;
	private ArrayList<AdjArrayObject>[] outdegreeArray;
	

	/**
	 * 按照节点的出入度生成邻接表
	 * author ZHP
	 * 2014年12月7日
	 * @param Edges
	 * @param maxNodeIndex
	 */
	public void EdgeToAdjList(ArrayList<edge> Edges,int maxNodeIndex){
		
		System.out.println(maxNodeIndex);
		//初始化，加1 为了保证最后一个节点在数组中有下标相同
		indegreeArray = new ArrayList[maxNodeIndex+1];
		outdegreeArray = new ArrayList[maxNodeIndex+1];
		
		for(edge e : Edges){
			
			//出度
			System.out.println("Out "+e.getS_node());
			AdjArrayObject outTemp = new AdjArrayObject();
			outTemp.setNodeIndex(e.getE_node());
			outTemp.setNodeWeight(e.getWeight());
			if(outdegreeArray[e.getS_node()]==null) 
				outdegreeArray[e.getS_node()] = new ArrayList<AdjArrayObject>();
			outdegreeArray[e.getS_node()].add(outTemp);
			
			//入度
			System.out.println("Ins "+e.getE_node());
			AdjArrayObject inTemp = new AdjArrayObject();
			inTemp.setNodeIndex(e.getS_node());
			inTemp.setNodeWeight(e.getWeight());
			if(indegreeArray[e.getE_node()]==null) 
				indegreeArray[e.getE_node()] = new ArrayList<AdjArrayObject>();
			indegreeArray[e.getE_node()].add(inTemp);
			
		}
		
		//打印结果
		printResult();
		
	}
	
	/**
	 * 打印出入度结果集合
	 * author ZHP
	 * 2014年12月7日
	 */
	public void printResult(){
		System.out.println("打印\"入度\"邻接表");
		int is = 0;	//is根据下标标识当前节点
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
		System.out.println("打印\"出度\"邻接表");
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
