package KNNpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import test.node;

public class KNNalog {
	
	//所有节点#,可标识当前未被检索的节点
	private ArrayList<Knode> sourcedata;
	//所有node的临界点及关联权重信息#<node,<neighbour,weight>>
	private HashMap<String,HashMap<String,Integer>> adjListofNode;
	//分类节点#
	private ArrayList<Knode> categorynode;
	//初始化结果list<node<category,weight>>
	private HashMap<String,HashMap<String,String>> resultMap;
	
	//根据分类节点，初始化分类结果集，作为备用的训练集
	public void initKNNdata(){
		
		resultMap = new HashMap<String,HashMap<String,String>>();
		for(Knode node : categorynode){
			
			System.out.println("\nnode is "+node.getNodeName());
			//node加入分类结果集,并将它从sourcedata集合中remove
			HashMap<String,String> value = new HashMap<String,String>();
			value.put("category",node.getCategoryName());
			value.put("weight", "0");
			resultMap.put(node.getNodeName(), value);
			//肯定是包涵的，礼貌性的判断一下哈
			if(sourcedata.contains(node)){
				System.out.println("length "+sourcedata.size());
				sourcedata.remove(node);
				System.out.println("length "+sourcedata.size());
			}
			
			HashMap<String,Integer> adjnodes = adjListofNode.get(node.getNodeName());
			if(adjnodes==null || adjnodes.size()==0) continue;
			//遍历类心的邻居节点
			for(Entry<String,Integer> e : adjnodes.entrySet()){
				
				if(resultMap.containsKey( e.getKey() ) ){
					//节点所属出现冲突，判断原来所属类的权重，留住权值大的分类
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
					
					//当然是初次加入结果集的时候，否则第二次，当然会出错，其实也没事，我们不是存在判断语句吗……
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
	 * function 处理sourcedata中的剩余节点，基于软件网络的小世界的特性，节点之间的路径较短，
	 * 		按照KNN算法，考虑当前节点的邻居，根据邻居的分类，确定当前节点的分类，带权
	 * inputData :    ArrayList<Knode> sourcedata;
	 * author ZHP
	 * 2014年11月23日
	 */
	public void knnOtherNode(){
		//遍历sourcedata处理当前未被分类的节点
		System.out.println("当前剩余节点的长度： "+sourcedata.size());
		//获得当前节点的邻居节点,正常情况下节点的邻居集合中不会包涵分类中心，在InitKNNdata中已经将
		//中心节点的邻居全部分好类，移除了sourcedata（由于要移除sourcedata元素将影响遍历，将sourcedata赋值给一个新的遍历集合）
		ArrayList<Knode> tempsourcedata = (ArrayList<Knode>)sourcedata.clone();
		for(Knode currentNode : tempsourcedata){
			String finalCategory = "";
			//类型、权值暂存
			HashMap<String,Integer> tempResult = new HashMap<String,Integer>();
			System.out.println("knode is "+currentNode.getNodeName());
			HashMap<String,Integer> adjnodes = adjListofNode.get(currentNode.getNodeName());
			if(adjnodes==null || adjnodes.size()==0) continue;
			//记录邻居数和已有分类的邻居权重
			int adjAllNum = 0;
			int adjhasCategory = 0;
			for(Entry<String,Integer> e : adjnodes.entrySet()){
				//初始化
				//循环获取当前的邻居
				String tempAdjName = e.getKey();
				int  tempAdjValue  = e.getValue();
				adjAllNum += tempAdjValue;
				System.out.print(" node "+tempAdjName+"  value "+tempAdjValue +"  |  ");
				//从结果集合中取出当前邻居的所属类别
				if(resultMap.containsKey(tempAdjName)){
					//从resultMap中获取邻居的类别
					HashMap<String,String> adj = resultMap.get(tempAdjName);
					//判断类别权重的表中有无该类别，有：取出相加； 无：直接加入
					if(tempResult.containsKey(adj.get("category"))){
						tempAdjValue += tempResult.get(adj.get("category"));
					}
					tempResult.put(adj.get("category"), tempAdjValue);
					//不用判断tempResult有没有，因为他的结果集ArrayList允许重复
					adjhasCategory += tempAdjValue;
				}else{
					continue;
				}
			}
			//判断已有带权值的边所占比例是否达到多占比例
			System.out.println("/"+((float)adjhasCategory/adjAllNum) +"/");
			if((float)adjhasCategory/adjAllNum >= 0.5){
				for(Entry<String,Integer> e : tempResult.entrySet()){
					//如果该类别权重所占比例 大于 0.8*0.8 (至少保证乘积大于0.5即可做决定)
					System.out.println("/"+((float)e.getValue()/adjhasCategory) +"/");
					if((float)e.getValue()/adjhasCategory> 0.8){
						finalCategory = e.getKey();
						break;
					}
				}
			}
			//判断结果，加入结果集，移除source集
			if(!finalCategory.equals("")){
				HashMap<String,String> value = new HashMap<String,String>();
				value.put("category",finalCategory);
				value.put("weight","*");
				resultMap.put(currentNode.getNodeName(), value);
				
				//当然是初次加入结果集的时候，否则第二次，当然会出错，其实也没事，我们不是存在判断语句吗……
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
