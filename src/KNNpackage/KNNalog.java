package KNNpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import test.node;

public class KNNalog {
	
	//���нڵ�#,�ɱ�ʶ��ǰδ�������Ľڵ�
	private ArrayList<Knode> sourcedata;
	//����node���ٽ�㼰����Ȩ����Ϣ#<node,<neighbour,weight>>
	private HashMap<String,HashMap<String,Integer>> adjListofNode;
	//����ڵ�#
	private ArrayList<Knode> categorynode;
	//��ʼ�����list<node<category,weight>>
	private HashMap<String,HashMap<String,String>> resultMap;
	
	//���ݷ���ڵ㣬��ʼ��������������Ϊ���õ�ѵ����
	public void initKNNdata(){
		
		resultMap = new HashMap<String,HashMap<String,String>>();
		for(Knode node : categorynode){
			
			System.out.println("\nnode is "+node.getNodeName());
			//node�����������,��������sourcedata������remove
			HashMap<String,String> value = new HashMap<String,String>();
			value.put("category",node.getCategoryName());
			value.put("weight", "0");
			resultMap.put(node.getNodeName(), value);
			//�϶��ǰ����ģ���ò�Ե��ж�һ�¹�
			if(sourcedata.contains(node)){
				System.out.println("length "+sourcedata.size());
				sourcedata.remove(node);
				System.out.println("length "+sourcedata.size());
			}
			
			HashMap<String,Integer> adjnodes = adjListofNode.get(node.getNodeName());
			if(adjnodes==null || adjnodes.size()==0) continue;
			//�������ĵ��ھӽڵ�
			for(Entry<String,Integer> e : adjnodes.entrySet()){
				
				if(resultMap.containsKey( e.getKey() ) ){
					//�ڵ��������ֳ�ͻ���ж�ԭ���������Ȩ�أ���סȨֵ��ķ���
					HashMap<String,String> currentValue = resultMap.get(e.getKey());
					if(e.getValue() >Integer.parseInt(currentValue.get("weight")) && !currentValue.get("weight").equals("0")){
						currentValue.put("category",node.getCategoryName());
						currentValue.put("weight", e.getValue().toString());
					}
					System.out.println("***********************************");
				}else{
					value = new HashMap<String,String>();
					value.put("category",node.getCategoryName());
					value.put("weight", e.getValue().toString());
					resultMap.put(e.getKey(), value);
					
					//��Ȼ�ǳ��μ���������ʱ�򣬷���ڶ��Σ���Ȼ�������ʵҲû�£����ǲ��Ǵ����ж�����𡭡�
					Knode temp = new Knode();
					temp.setNodeName(e.getKey());
					if(sourcedata.contains(temp)){
						System.out.println("length "+sourcedata.size());
						sourcedata.remove(temp);
						System.out.println("length "+sourcedata.size());
					}
				}
			}
		}
		
		/*
		Iterator<Entry<String,HashMap<String,String>>> it = resultMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,HashMap<String,String>> entry = it.next();
			System.out.println("\ncurrentNode is "+entry.getKey());
			HashMap<String,String> adjnode = entry.getValue();
			for(Entry<String,String> e : adjnode.entrySet()){
				System.out.print(" ->"+e.getKey()+"  : "+e.getValue()+"    ");
			}
		}
		*/
	}
	
	/**
	 * function ����sourcedata�е�ʣ��ڵ㣬������������С��������ԣ��ڵ�֮���·���϶̣�
	 * 		����KNN�㷨�����ǵ�ǰ�ڵ���ھӣ������ھӵķ��࣬ȷ����ǰ�ڵ�ķ��࣬��Ȩ
	 * inputData :    ArrayList<Knode> sourcedata;
	 * author ZHP
	 * 2014��11��23��
	 */
	public void knnOtherNode(){
		//����sourcedata����ǰδ������Ľڵ�
		System.out.println("��ǰʣ��ڵ�ĳ��ȣ� "+sourcedata.size());
		//��õ�ǰ�ڵ���ھӽڵ�,��������½ڵ���ھӼ����в�������������ģ���InitKNNdata���Ѿ���
		//���Ľڵ���ھ�ȫ���ֺ��࣬�Ƴ���sourcedata������Ҫ�Ƴ�sourcedataԪ�ؽ�Ӱ���������sourcedata��ֵ��һ���µı������ϣ�
		ArrayList<Knode> tempsourcedata = (ArrayList<Knode>)sourcedata.clone();
		for(Knode currentNode : tempsourcedata){
			String finalCategory = "";
			//���͡�Ȩֵ�ݴ�
			HashMap<String,Integer> tempResult = new HashMap<String,Integer>();
			System.out.println("knode is "+currentNode.getNodeName());
			HashMap<String,Integer> adjnodes = adjListofNode.get(currentNode.getNodeName());
			if(adjnodes==null || adjnodes.size()==0) continue;
			//��¼�ھ��������з�����ھ�Ȩ��
			int adjAllNum = 0;
			int adjhasCategory = 0;
			for(Entry<String,Integer> e : adjnodes.entrySet()){
				//��ʼ��
				//ѭ����ȡ��ǰ���ھ�
				String tempAdjName = e.getKey();
				int  tempAdjValue  = e.getValue();
				adjAllNum += tempAdjValue;
				System.out.print(" node "+tempAdjName+"  value "+tempAdjValue +"  |  ");
				//�ӽ��������ȡ����ǰ�ھӵ��������
				if(resultMap.containsKey(tempAdjName)){
					//��resultMap�л�ȡ�ھӵ����
					HashMap<String,String> adj = resultMap.get(tempAdjName);
					//�ж����Ȩ�صı������޸�����У�ȡ����ӣ� �ޣ�ֱ�Ӽ���
					if(tempResult.containsKey(adj.get("category"))){
						tempAdjValue += tempResult.get(adj.get("category"));
					}
					tempResult.put(adj.get("category"), tempAdjValue);
					//�����ж�tempResult��û�У���Ϊ���Ľ����ArrayList�����ظ�
					adjhasCategory += tempAdjValue;
				}else{
					continue;
				}
			}
			//�ж����д�Ȩֵ�ı���ռ�����Ƿ�ﵽ��ռ����
			System.out.println("/"+((float)adjhasCategory/adjAllNum) +"/");
			if((float)adjhasCategory/adjAllNum >= 0.5){
				for(Entry<String,Integer> e : tempResult.entrySet()){
					//��������Ȩ����ռ���� ���� 0.8*0.8 (���ٱ�֤�˻�����0.5����������)
					System.out.println("/"+((float)e.getValue()/adjhasCategory) +"/");
					if((float)e.getValue()/adjhasCategory> 0.8){
						finalCategory = e.getKey();
						break;
					}
				}
			}
			//�жϽ���������������Ƴ�source��
			if(!finalCategory.equals("")){
				HashMap<String,String> value = new HashMap<String,String>();
				value.put("category",finalCategory);
				value.put("weight","*");
				resultMap.put(currentNode.getNodeName(), value);
				
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
		while(sourcedata.size()>0){
			System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss "+sourcedata.size());
			this.knnOtherNode();
			System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss "+sourcedata.size());
			for(Knode e: sourcedata){
				System.out.print("node "+e.getNodeIndex()+"  name "+e.getNodeName()+" | ");
			}
			
		}
	}
	
	
	//getters  and setters
	public ArrayList<Knode> getSourcedata() {
		return sourcedata;
	}
	public void setSourcedata(ArrayList<node> Nodes) {
		ArrayList<Knode>   sdata = new ArrayList<Knode>();
		for(node  e: Nodes){
			Knode one = new Knode();
			one.setNodeName(e.getIndex()+"");
			sdata.add(one);
		}
		this.sourcedata = sdata;
	}
	public ArrayList<Knode> getCategorynode() {
		return categorynode;
	}
	public void setCategorynode(ArrayList<node> Nodes) {
		
		ArrayList<Knode>   cdata = new ArrayList<Knode>();
		for(node  e: Nodes){
			Knode one = new Knode();
			one.setNodeName(e.getIndex()+"");
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
	

}
