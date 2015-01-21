package KNNpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import AdjList.AdjArrayObject;
import test.edge;
import test.node;
import util.testQFunction;
import util.testQunweight;

/**
 * ���ģ����ڽӱ�adjListofNode �滻Ϊ inDegreeList,outDegreeList
 * @author ZHP
 * 2014��12��7��
 */
public class KNNalog {
	
	private static ArrayList<node> Nodes ;//�洢�ڵ�Node
	private static ArrayList<edge> Edges;//�洢��Edges
	//�̶�sourcedata���б��е�ֵ��������ִ�й��̸ı�
	private ArrayList<Knode> finalSourcedata;
	//���нڵ�#,�ɱ�ʶ��ǰδ�������Ľڵ㣬���ŵ�ǰ�ڵ㱻���࣬�ڵ���sourcedata��remove
	private ArrayList<Knode> sourcedata;
	//����node���ٽ�㼰����Ȩ����Ϣ#<node,<neighbour,weight>>
	private HashMap<String,HashMap<String,Integer>> adjListofNode;
	private ArrayList<AdjArrayObject>[] inDegreeList;
	private ArrayList<AdjArrayObject>[] outDegreeList;
	//����ڵ�
	private ArrayList<Knode> categorynode;
	//��ʼ�����list<node<category,weight>>
	private HashMap<String,HashMap<String,String>> resultMap;
	//����洢�����
	private HashMap<String,ArrayList<AdjArrayObject>> categoryResultList;
	
	
	public ArrayList<node> getNodes() {
		return Nodes;
	}

	public void setNodes(ArrayList<node> nodes) {
		Nodes = nodes;
	}

	public ArrayList<edge> getEdges() {
		return Edges;
	}

	public void setEdges(ArrayList<edge> edges) {
		Edges = edges;
	}

	/**
	 * ���ݷ���ڵ㣬��ʼ��������������Ϊ���õ�ѵ����
	 * author ZHP
	 * 2014��12��8��
	 */
	public void initKNNdata(){
		
		resultMap = new HashMap<String,HashMap<String,String>>();
		//����core�ڵ�
		for(Knode node : categorynode){
			
			System.out.println("\nnode is "+node.getNodeIndex());
			//node�����������,��������sourcedata������remove
			HashMap<String,String> value = new HashMap<String,String>();
			value.put("category","C_"+node.getNodeIndex());
			value.put("weight", "0");
			resultMap.put(node.getNodeIndex()+"", value);
			//�϶��ǰ����ģ���ò�Ե��ж�һ�¹�
			if(sourcedata.contains(node)){
				System.out.print(" length: "+sourcedata.size());
				sourcedata.remove(node);
				System.out.println(" to  length "+sourcedata.size()+"   delete node��"+node.getNodeIndex());
			}
			
		}
		//�����ھӽڵ�
		for(Knode node : categorynode){
			System.out.println("\nnode is "+node.getNodeIndex());
			//���������ھ�����ı���
			ArrayList<AdjArrayObject> adjNodes = outDegreeList[node.getNodeIndex()];
			
			if(adjNodes==null || adjNodes.size()==0) continue;
			//�������ĵ��ھӽڵ�
			for(AdjArrayObject adj : adjNodes){
				System.out.println("cNode is "+adj.getNodeIndex());
				initAdjResult(node, adj);
			}
		}
	}
	
	/**
	 * ����core�ڵ���ھӣ���Ҫ����core�ڵ�node Ϊ�ο�
	 * author ZHP
	 * 2014��12��8��
	 * @param node   core node
	 * @param adj	neighbors node
	 */
	public void initAdjResult(Knode node,AdjArrayObject adj){
		
		//�ڵ��������ֳ�ͻ���ж�ԭ���������Ȩ�أ���סȨֵ��ķ���
		if(resultMap.containsKey( adj.getNodeIndex()+"") ){
			HashMap<String,String> currentValue = resultMap.get(adj.getNodeIndex()+"");
			if((adj.getNodeWeight() >Integer.parseInt(currentValue.get("weight")) && !currentValue.get("weight").equals("0"))
			    || adj.getNodeWeight()==0){
				currentValue.put("category",node.getCategoryName());
				currentValue.put("weight", adj.getNodeWeight()+"");
			//����ýڵ�Ϊ����core���ھ���Ȩֵ��ͬ���ֲ��������Ȼ���ܷŵ���������������ڵ�ο���������category,
			//����������bakCategory�洢��ǰȨֵ���ķ��༯��
			}else if(adj.getNodeWeight() ==Integer.parseInt(currentValue.get("weight"))){
				String category = "bak "+currentValue.get("category");
				currentValue.put("category","");
		//		currentValue.put("weight", adj.getNodeWeight()+"");
				if(currentValue.get("bakCategory")!=null && !currentValue.get("bakCategory").equals(""))
					category = category +","+ currentValue.get("bakCategory");
				category = category +","+ node.getCategoryName();
				currentValue.put("bakCategory", category);
				//��source�л�ԭ�õ㣬
				Knode temp = new Knode();
				temp.setNodeIndex(adj.getNodeIndex());
				
				System.out.print(" length: "+sourcedata.size());
				sourcedata.add(temp);
				System.out.println(" to  length "+sourcedata.size()+"  add node��"+temp.getNodeIndex());
			}
			System.out.println("***********************************"+adj.getNodeIndex()+"  C "+node.getCategoryName()+"   wei "+adj.getNodeWeight());
		}else{
			HashMap<String,String> valueTemp = new HashMap<String,String>();
			valueTemp.put("category",node.getCategoryName());
			valueTemp.put("weight", adj.getNodeWeight()+"");
			resultMap.put(adj.getNodeIndex()+"", valueTemp);
			System.out.println("***********************************"+adj.getNodeIndex()+"  C "+node.getCategoryName()+"   wei "+adj.getNodeWeight());
			//ɾ��source�еĵ�ǰ�ڵ㣬��Ȼ�ǳ��μ���������ʱ�򣬷���ڶ��Σ���Ȼ�������ʵҲû�£����ǲ��Ǵ����ж�����𡭡�
			Knode temp = new Knode();
	//		temp.setNodeName("");
			temp.setNodeIndex(adj.getNodeIndex());
			if(sourcedata.contains(temp)){
				System.out.print(" length: "+sourcedata.size());
				sourcedata.remove(temp);
				System.out.println(" to  length "+sourcedata.size()+"   delete node��"+temp.getNodeIndex());
			}
		}
	}
	
	
	/**
	 * function ����sourcedata�е�ʣ��ڵ㣬������������С��������ԣ��ڵ�֮���·���϶̣�
	 * 		����KNN�㷨�����ǵ�ǰ�ڵ���ھӣ������ھӵķ��࣬ȷ����ǰ�ڵ�ķ��࣬��Ȩ
	 * inputData :    ArrayList<Knode> sourcedata;
	 * author ZHP
	 * 2014��11��23��
	 * ���� 2014.12.08( ���ھӽڵ�Ĳ�ѯ�����е��ھӱ䵽������ǰ�ڵ�����)\
	 *  remain�Ƿ���Ȩ��
	 */
	public void knnOtherNode(int remain){
		//����sourcedata����ǰδ������Ľڵ㣬���ڳ�ͻ�Ľڵ�����ֱ����ص�source�����ڽ�������ϴ���
		System.out.println("��ǰʣ��ڵ�ĳ��ȣ� "+sourcedata.size());
		//��õ�ǰ�ڵ���ھӽڵ�,��������½ڵ���ھӼ����в�������������ģ���InitKNNdata���Ѿ���
		//���Ľڵ���ھ�ȫ���ֺ��࣬�Ƴ���sourcedata������Ҫ�Ƴ�sourcedataԪ�ؽ�Ӱ���������sourcedata��ֵ��һ���µı������ϣ�
		ArrayList<Knode> tempsourcedata = (ArrayList<Knode>)sourcedata.clone();
		for(Knode currentNode : tempsourcedata){
			System.out.println("knode is "+currentNode.getNodeIndex());

			String finalCategory = "";
			int finalWeight = 0;
			//���͡�Ȩֵ�ݴ�
			HashMap<String,Integer> tempResult = new HashMap<String,Integer>();
			
			//������ǰ�ڵ������ھ�
			ArrayList<AdjArrayObject> indegreeAdj = inDegreeList[currentNode.getNodeIndex()];
			//���ھ�������һ���ڵ�
			if(indegreeAdj==null || indegreeAdj.size()==0) continue;
		
			//�����ж����������Աߵ�Ȩֵ�����Ժ�ֲ�����ʱ�����Ȩֵ�ж�
			int indegreeAdjAllNum = 0;
			int AdjHasCategory = 0;
			//��¼�ھ��������з�����ھ�Ȩ��
			int CategoryWeight = 0;
			
			for(AdjArrayObject adj : indegreeAdj){
				//ѭ����ȡ��ǰ���ھ�
				int tempAdjIndex = adj.getNodeIndex();
				String adjIndexStr = tempAdjIndex+"";
				int tempAdjValue  = 1;
				int tempWeight = adj.getNodeWeight();
				
				//�ھ�����1
				indegreeAdjAllNum += 1;
				System.out.print(" node "+tempAdjIndex+"  value "+tempAdjValue +"  |  ");
				
				//�ӽ��������ȡ����ǰ�ھӵ������������������category��Ϊ��(���಻��ȷ�Ľڵ�������)
				if(resultMap.containsKey(adjIndexStr) && !resultMap.get(adjIndexStr).get("category").equals("")){
					//��resultMap�л�ȡ�ھӵ����
					HashMap<String,String> adjNode = resultMap.get(adjIndexStr);
					//�ж����Ȩ�صı������޸�����У�ȡ����ӣ� �ޣ�ֱ�Ӽ���
					if(tempResult.containsKey(adjNode.get("category"))){
						//init�����Ⱥ���Ȩ�أ������ձ���
						//��ͬ������ھ���+1
						tempAdjValue += tempResult.get(adjNode.get("category"));
						if(tempResult.containsKey(adjNode.get("category")+"Weight"))
							tempWeight += tempResult.get(adjNode.get("category")+"Weight");
					}
					tempResult.put(adjNode.get("category"), tempAdjValue);
					tempResult.put(adjNode.get("category")+"Weight", tempWeight);
					System.out.println("\n"+adjNode.get("category")+"  "+tempAdjValue+" W "+tempWeight);
					//�ѷ���Ľڵ���+1
					AdjHasCategory += 1;
				}else{
					continue;
				}
			}
			
			if(remain==0){
				//�ж����д�Ȩֵ�ı���ռ�����Ƿ�ﵽ��ռ����
				for(Entry<String,Integer> e : tempResult.entrySet()){
					if(e.getKey().contains("Weight")) continue;
					//��������Ȩ����ռ���� ���� 0.8*0.8 (���ٱ�֤�˻�����0.5����������)
					System.out.println("/"+((float)e.getValue()/AdjHasCategory) +"/");
					System.out.println(" "+(float)e.getValue()/indegreeAdjAllNum);
					if(((float)e.getValue()/indegreeAdjAllNum) > 0.5 && !e.getKey().contains("Weight")){
						finalCategory = e.getKey();
						finalWeight = e.getValue();
						break;
					}
				}
			}else if(remain==1){
				//�ж����д�Ȩֵ�ı���ռ�����Ƿ�ﵽ��ռ����
				int tempMaxWeight = 0;
				for(Entry<String,Integer> e : tempResult.entrySet()){
					if(!e.getKey().contains("Weight")) continue;
					if(e.getValue() > tempMaxWeight){
						finalCategory = e.getKey().substring(0,e.getKey().indexOf('W'));
						tempMaxWeight = e.getValue();
						//��Ȩֵ����Ϊ�������ڵ���ͬ�����
					}else if(e.getValue() == tempMaxWeight){
						finalCategory = "X";
					}
				}
			}
			
			//�жϽ���������������Ƴ�source��
			if(!finalCategory.equals("")){
				System.out.println(" Category is "+finalCategory);
				HashMap<String,String> value = new HashMap<String,String>();
				value.put("category",finalCategory);
				value.put("weight",finalWeight+"");
				resultMap.put(currentNode.getNodeIndex()+"", value);
				
				//��Ȼ�ǳ��μ���������ʱ�򣬷���ڶ��Σ���Ȼ�������ʵҲû�£����ǲ��Ǵ����ж�����𡭡�
				if(sourcedata.contains(currentNode)){
					System.out.println("length "+sourcedata.size());
					sourcedata.remove(currentNode );
					System.out.println("length "+sourcedata.size());
				}
			}
			System.out.println();
		}
	}
	
	public void doKnn(){
		int preRemain = 0;
		while(sourcedata.size()>0 && sourcedata.size()!=preRemain){
			preRemain = sourcedata.size();
			System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss "+sourcedata.size());
			this.knnOtherNode(0);
			System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss "+sourcedata.size());
			for(Knode e: sourcedata){
				System.out.print("node "+e.getNodeIndex()+"  name "+e.getNodeName()+" | ");
			}
		}
		//ʣ���������������Ȩ�ķ��������ˣ����Ͽ��Ǳߵ�Ȩֵ���з���
		preRemain = 0;
		while(sourcedata.size()>0 && sourcedata.size()!=preRemain){
			preRemain = sourcedata.size();
			System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss "+sourcedata.size());
			this.knnOtherNode(1);
			System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss "+sourcedata.size());
			for(Knode e: sourcedata){
				System.out.print("node "+e.getNodeIndex()+"  name "+e.getNodeName()+" | ");
			}
		}
	}
	
	/**
	 * �����ǰ�����reusultMap�Ľ��
	 * author ZHP
	 * 2014��12��8��
	 * @param outStyle   1�������������
	 */
	public void outResultSet(int outStyle){
		
		Iterator<Entry<String,HashMap<String,String>>> it = resultMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,HashMap<String,String>> entry = it.next();
			System.out.print("\ncurrentNode is "+entry.getKey());
			HashMap<String,String> resultInfo = entry.getValue();
			for(Entry<String,String> e : resultInfo.entrySet()){
				System.out.print(" ->"+e.getKey()+"  : "+e.getValue()+"    ");
			}
		}
		//������������
		if(outStyle !=1){
			System.out.println("\n\n��������������");
			categoryResultList = new HashMap<String, ArrayList<AdjArrayObject>>();
			for(Knode currentCategory : categorynode){
				//�ݴ浱ǰ���Ľ����
				ArrayList<AdjArrayObject> currentList = new ArrayList<AdjArrayObject>();
				it = resultMap.entrySet().iterator();
				int num = 0;
				while(it.hasNext()){
					Entry<String,HashMap<String,String>> entry = it.next();
					HashMap<String,String> adjnode = entry.getValue();
					if(entry.getValue().get("category").equals(currentCategory.getCategoryName())){
						System.out.print("\ncurrentNode is "+entry.getKey());
						num++;
						for(Entry<String,String> e : adjnode.entrySet()){
							System.out.print(" ->"+e.getKey()+"  : "+e.getValue()+"    ");
						}
						//���ɽڵ���뵱ǰList
						AdjArrayObject cateOne = new AdjArrayObject();
						cateOne.setNodeIndex(Integer.parseInt(entry.getKey()));
						cateOne.setNodeWeight(Integer.parseInt(adjnode.get("weight")));
						currentList.add(cateOne);
					}
				}
				System.out.println("\nZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ   Catagory : "
							+currentCategory.getCategoryName()+" number =  "+num);
				categoryResultList.put(currentCategory.getCategoryName(), currentList);
			}
			//ʣ��δ����Ľڵ�
			it = resultMap.entrySet().iterator();
			int num = 0;
			while(it.hasNext()){
				Entry<String,HashMap<String,String>> entry = it.next();
				HashMap<String,String> adjnode = entry.getValue();
				if(entry.getValue().get("category").equals("X")){
					System.out.print("\ncurrentNode is "+entry.getKey());
					num++;
					for(Entry<String,String> e : adjnode.entrySet()){
						System.out.print(" ->"+e.getKey()+"  : "+e.getValue()+"    ");
					}
				}
			}
			System.out.println("\nZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ   Catagory : "
						+" X    number =  "+num);
			
		}
	}
	
	public float computeModel(){
		
		testQFunction Q = new testQFunction(resultMap, finalSourcedata, Nodes, Edges, inDegreeList, outDegreeList);
		testQunweight UQ = new testQunweight(resultMap, finalSourcedata, Nodes, Edges, inDegreeList, outDegreeList);
//		return UQ.softwareQFunction();
		return Q.softwareQFunction();
	}
	
	//getters  and setters
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
		this.finalSourcedata = (ArrayList<Knode>)sdata.clone();
	}
	public ArrayList<Knode> getCategorynode() {
		return categorynode;
	}
	public void setCategorynode(ArrayList<node> Nodes) {
		
		ArrayList<Knode>   cdata = new ArrayList<Knode>();
		for(node  e: Nodes){
			Knode one = new Knode();
			one.setNodeIndex(e.getIndex());
			one.setNodeName("");
			one.setCategoryName("C_"+e.getIndex()+"");
			cdata.add(one);
		}
		this.categorynode = cdata;
	}
	public HashMap<String, HashMap<String, String>> getResultMap() {
		return resultMap;
	}
	public void setResultMap(HashMap<String, HashMap<String, String>> resultMap) {
		this.resultMap = resultMap;
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

	
	
}
