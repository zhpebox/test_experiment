package compare;

import java.util.ArrayList;
import java.util.HashMap;
import test.node;
import util.utilBean;
import AdjList.AdjArrayObject;
import KNNpackage.Knode;

public class algorithmOne {

		private ArrayList<Knode> sourcedata;
		//所有node的临界点及关联权重信息#<node,<neighbour,weight>>
		private HashMap<String,HashMap<String,Integer>> adjListofNode;
		private ArrayList<AdjArrayObject>[] inDegreeList;
		private ArrayList<AdjArrayObject>[] outDegreeList;
		//分类节点
		//初始化结果list<node<category,weight>>
		private HashMap<String,HashMap<String,String>> resultMap;
		//分类存储结果集
		private HashMap<String,ArrayList<AdjArrayObject>> categoryResultList;
		
		/**
		 * 对比算法1
		 * author ZHP
		 * 2014年12月15日
		 */
		public void computeAlgorithm(){
			
			ArrayList<Knode> tempSourcedata = (ArrayList<Knode>)sourcedata.clone();
			ArrayList<AdjArrayObject>[] tempOutDegreeList = (ArrayList<AdjArrayObject>[])outDegreeList.clone();
			
			//取出入度为1的节点，他的分类和入度节点相同
    			for(int index = 0 ;index<inDegreeList.length;index++){
				ArrayList<AdjArrayObject> nodeInList = inDegreeList[index];
				ArrayList<AdjArrayObject> nodeOutList = tempOutDegreeList[index];
				if((nodeInList==null || nodeInList.size()==1) && (nodeOutList==null || nodeOutList.size()==0) ) {
					Knode remOne = new Knode();
					remOne.setNodeIndex(index);
					tempSourcedata.remove(remOne);
					//从出度表中删除该节点的出度节点信息
					if(nodeInList!=null){
						int outNode = nodeInList.get(0).getNodeIndex();
						AdjArrayObject remoutNode = new AdjArrayObject();
						remoutNode.setNodeIndex(index);
						outDegreeList[outNode].remove(remoutNode);
						System.out.println("   "+index+"  is remove  ");
					}
				}
					
			}
			
			System.out.println("打印\"新的出度\"邻接表");
			int is = 0;	//is根据下标标识当前节点
			for(ArrayList<AdjArrayObject> one  : outDegreeList){
				System.out.print("\n current node is "+is);
				if(one == null || one.size()==0){
					System.out.print(" the line  is null");
				}else{
					for(AdjArrayObject e:one){
						System.out.print(" "+e.getNodeIndex()+"_"+e.getNodeWeight()+"  ");
					}
					System.out.print("  number is "+one.size());
				}
				is++;
			}
			
			
 			System.out.println("\n剩余划分的节点"); 
			System.out.println("****************************************************************");
			for(Knode e : tempSourcedata){
				System.out.print("   "+ e.getNodeIndex());
				if(inDegreeList[e.getNodeIndex()]!=null)
					System.out.println("   入度邻居数 ：  "+inDegreeList[e.getNodeIndex()].size());
			}
			System.out.println("****************************************************************");
			//当前节点和它的入度邻居邻居依赖比较（考虑到有向网络，节点的邻居选用于其分类相关的入度邻居，而不是出度邻居）
			for(Knode one : tempSourcedata){
				String adj = "";
				float adjWeidht = 0;
			//	System.out.println("currentNode is "+ one.getNodeIndex());
				if(inDegreeList[one.getNodeIndex()]==null || inDegreeList[one.getNodeIndex()].size()==0) 
					continue;
				for(AdjArrayObject two : inDegreeList[one.getNodeIndex()]){
				//	System.out.println(one.getNodeIndex()+"                   "+two.getNodeIndex());
					int all = 0;
					int same = 0;
					//基本上从小到达的排序好了
					ArrayList<AdjArrayObject> oneList = outDegreeList[one.getNodeIndex()];
					ArrayList<AdjArrayObject> twoList = outDegreeList[two.getNodeIndex()];
					if(oneList!=null && twoList!=null){
						for(AdjArrayObject cnode : oneList){
							if(twoList.contains(cnode)){
								same++;
							}
							all++;
						}
					}
					if( (float)same/all > adjWeidht ){
						adjWeidht = (float)same/all;
						adj = two.getNodeIndex()+"";
					}else if( (float)same/all == adjWeidht ){
						adj = adj +" / "+ two.getNodeIndex();
					}
				}
				if(adjWeidht!=0)
					System.out.println("\n                  "+one.getNodeIndex()+"      "+adj+"      "+adjWeidht);
			}
			      
		}

		public ArrayList<Knode> getSourcedata() {
			return sourcedata;
		}

		public void setSourcedata(ArrayList<node> Nodes) {
			ArrayList<Knode>   sdata = new ArrayList<Knode>();
			for(node  e: Nodes){
				Knode one = new Knode();
				one.setNodeName("");
				one.setNodeIndex(e.getIndex());
				sdata.add(one);
			}
			this.sourcedata = sdata;
		}

		public HashMap<String, HashMap<String, Integer>> getAdjListofNode() {
			return adjListofNode;
		}

		public void setAdjListofNode(
				HashMap<String, HashMap<String, Integer>> adjListofNode) {
			this.adjListofNode = adjListofNode;
		}

		public ArrayList<AdjArrayObject>[] getInDegreeList() {
			return inDegreeList;
		}

		public void setInDegreeList(ArrayList<AdjArrayObject>[] inDegreeList) {
			this.inDegreeList = inDegreeList;
		}

		public ArrayList<AdjArrayObject>[] getOutDegreeList() {
			return outDegreeList;
		}

		public void setOutDegreeList(ArrayList<AdjArrayObject>[] outDegreeList) {
			this.outDegreeList = outDegreeList;
		}

		public HashMap<String, HashMap<String, String>> getResultMap() {
			return resultMap;
		}

		public void setResultMap(HashMap<String, HashMap<String, String>> resultMap) {
			this.resultMap = resultMap;
		}

		public HashMap<String, ArrayList<AdjArrayObject>> getCategoryResultList() {
			return categoryResultList;
		}

		public void setCategoryResultList(
				HashMap<String, ArrayList<AdjArrayObject>> categoryResultList) {
			this.categoryResultList = categoryResultList;
		}
		
		
		
}
