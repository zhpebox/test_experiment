package compare;

import java.util.ArrayList;
import java.util.HashMap;
import test.node;
import util.utilBean;
import AdjList.AdjArrayObject;
import KNNpackage.Knode;

public class algorithmOne {

		private ArrayList<Knode> sourcedata;
		//����node���ٽ�㼰����Ȩ����Ϣ#<node,<neighbour,weight>>
		private HashMap<String,HashMap<String,Integer>> adjListofNode;
		private ArrayList<AdjArrayObject>[] inDegreeList;
		private ArrayList<AdjArrayObject>[] outDegreeList;
		//����ڵ�
		//��ʼ�����list<node<category,weight>>
		private HashMap<String,HashMap<String,String>> resultMap;
		//����洢�����
		private HashMap<String,ArrayList<AdjArrayObject>> categoryResultList;
		
		/**
		 * �Ա��㷨1
		 * author ZHP
		 * 2014��12��15��
		 */
		public void computeAlgorithm(){
			
			ArrayList<Knode> tempSourcedata = (ArrayList<Knode>)sourcedata.clone();
			ArrayList<AdjArrayObject>[] tempOutDegreeList = (ArrayList<AdjArrayObject>[])outDegreeList.clone();
			
			//ȡ�����Ϊ1�Ľڵ㣬���ķ������Ƚڵ���ͬ
    			for(int index = 0 ;index<inDegreeList.length;index++){
				ArrayList<AdjArrayObject> nodeInList = inDegreeList[index];
				ArrayList<AdjArrayObject> nodeOutList = tempOutDegreeList[index];
				if((nodeInList==null || nodeInList.size()==1) && (nodeOutList==null || nodeOutList.size()==0) ) {
					Knode remOne = new Knode();
					remOne.setNodeIndex(index);
					tempSourcedata.remove(remOne);
					//�ӳ��ȱ���ɾ���ýڵ�ĳ��Ƚڵ���Ϣ
					if(nodeInList!=null){
						int outNode = nodeInList.get(0).getNodeIndex();
						AdjArrayObject remoutNode = new AdjArrayObject();
						remoutNode.setNodeIndex(index);
						outDegreeList[outNode].remove(remoutNode);
						System.out.println("   "+index+"  is remove  ");
					}
				}
					
			}
			
			System.out.println("��ӡ\"�µĳ���\"�ڽӱ�");
			int is = 0;	//is�����±��ʶ��ǰ�ڵ�
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
			
			
 			System.out.println("\nʣ�໮�ֵĽڵ�"); 
			System.out.println("****************************************************************");
			for(Knode e : tempSourcedata){
				System.out.print("   "+ e.getNodeIndex());
				if(inDegreeList[e.getNodeIndex()]!=null)
					System.out.println("   ����ھ��� ��  "+inDegreeList[e.getNodeIndex()].size());
			}
			System.out.println("****************************************************************");
			//��ǰ�ڵ����������ھ��ھ������Ƚϣ����ǵ��������磬�ڵ���ھ�ѡ�����������ص�����ھӣ������ǳ����ھӣ�
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
					//�����ϴ�С������������
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
