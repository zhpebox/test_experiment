package KNNpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import AdjList.AdjArrayObject;
import test.node;

/**
 * 更改，将邻接表adjListofNode 替换为 inDegreeList,outDegreeList
 * @author ZHP
 * 2014年12月7日
 */
public class KNNalog {
	
	//固定sourcedata，列表中的值不会随着执行过程改变
	private ArrayList<Knode> finalSourcedata;
	//所有节点#,可标识当前未被检索的节点，随着当前节点被分类，节点会从sourcedata中remove
	private ArrayList<Knode> sourcedata;
	//所有node的临界点及关联权重信息#<node,<neighbour,weight>>
	private HashMap<String,HashMap<String,Integer>> adjListofNode;
	private ArrayList<AdjArrayObject>[] inDegreeList;
	private ArrayList<AdjArrayObject>[] outDegreeList;
	//分类节点
	private ArrayList<Knode> categorynode;
	//初始化结果list<node<category,weight>>
	private HashMap<String,HashMap<String,String>> resultMap;
	
	/**
	 * 根据分类节点，初始化分类结果集，作为备用的训练集
	 * author ZHP
	 * 2014年12月8日
	 */
	public void initKNNdata(){
		
		resultMap = new HashMap<String,HashMap<String,String>>();
		//处理core节点
		for(Knode node : categorynode){
			
			System.out.println("\nnode is "+node.getNodeIndex());
			//node加入分类结果集,并将它从sourcedata集合中remove
			HashMap<String,String> value = new HashMap<String,String>();
			value.put("category","C_"+node.getNodeIndex());
			value.put("weight", "0");
			resultMap.put(node.getNodeIndex()+"", value);
			//肯定是包涵的，礼貌性的判断一下哈
			if(sourcedata.contains(node)){
				System.out.print(" length: "+sourcedata.size());
				sourcedata.remove(node);
				System.out.println(" to  length "+sourcedata.size()+"   delete node："+node.getNodeIndex());
			}
			
		}
		//处理邻居节点
		for(Knode node : categorynode){
			System.out.println("\nnode is "+node.getNodeIndex());
			//遍历出度邻居数组的遍历
			ArrayList<AdjArrayObject> adjNodes = outDegreeList[node.getNodeIndex()];
			
			if(adjNodes==null || adjNodes.size()==0) continue;
			//遍历类心的邻居节点
			for(AdjArrayObject adj : adjNodes){
				System.out.println("cNode is "+adj.getNodeIndex());
				initAdjResult(node, adj);
			}
		}
	}
	
	/**
	 * 处理core节点的邻居，主要依据core节点node 为参考
	 * author ZHP
	 * 2014年12月8日
	 * @param node   core node
	 * @param adj	neighbors node
	 */
	public void initAdjResult(Knode node,AdjArrayObject adj){
		
		//节点所属出现冲突，判断原来所属类的权重，留住权值大的分类
		if(resultMap.containsKey( adj.getNodeIndex()+"") ){
			HashMap<String,String> currentValue = resultMap.get(adj.getNodeIndex()+"");
			if((adj.getNodeWeight() >Integer.parseInt(currentValue.get("weight")) && !currentValue.get("weight").equals("0"))
			    || adj.getNodeWeight()==0){
				currentValue.put("category",node.getCategoryName());
				currentValue.put("weight", adj.getNodeWeight()+"");
			//如果该节点为两个core的邻居且权值相同，分不出类别，自然不能放到结果集，被其他节点参考，因此清空category,
			//增加新属性bakCategory存储当前权值最大的分类集合
			}else if(adj.getNodeWeight() ==Integer.parseInt(currentValue.get("weight"))){
				String category = "bak "+currentValue.get("category");
				currentValue.put("category","");
		//		currentValue.put("weight", adj.getNodeWeight()+"");
				if(currentValue.get("bakCategory")!=null && !currentValue.get("bakCategory").equals(""))
					category = category +","+ currentValue.get("bakCategory");
				category = category +","+ node.getCategoryName();
				currentValue.put("bakCategory", category);
				//在source中还原该点，
				Knode temp = new Knode();
				temp.setNodeIndex(adj.getNodeIndex());
				
				System.out.print(" length: "+sourcedata.size());
				sourcedata.add(temp);
				System.out.println(" to  length "+sourcedata.size()+"  add node："+temp.getNodeIndex());
			}
			System.out.println("***********************************"+adj.getNodeIndex()+"  C "+node.getCategoryName()+"   wei "+adj.getNodeWeight());
		}else{
			HashMap<String,String> valueTemp = new HashMap<String,String>();
			valueTemp.put("category",node.getCategoryName());
			valueTemp.put("weight", adj.getNodeWeight()+"");
			resultMap.put(adj.getNodeIndex()+"", valueTemp);
			System.out.println("***********************************"+adj.getNodeIndex()+"  C "+node.getCategoryName()+"   wei "+adj.getNodeWeight());
			//删除source中的当前节点，当然是初次加入结果集的时候，否则第二次，当然会出错，其实也没事，我们不是存在判断语句吗……
			Knode temp = new Knode();
	//		temp.setNodeName("");
			temp.setNodeIndex(adj.getNodeIndex());
			if(sourcedata.contains(temp)){
				System.out.print(" length: "+sourcedata.size());
				sourcedata.remove(temp);
				System.out.println(" to  length "+sourcedata.size()+"   delete node："+temp.getNodeIndex());
			}
		}
	}
	
	
	/**
	 * function 处理sourcedata中的剩余节点，基于软件网络的小世界的特性，节点之间的路径较短，
	 * 		按照KNN算法，考虑当前节点的邻居，根据邻居的分类，确定当前节点的分类，带权
	 * inputData :    ArrayList<Knode> sourcedata;
	 * author ZHP
	 * 2014年11月23日
	 * 更改 2014.12.08( 将邻居节点的查询从所有的邻居变到遍历当前节点的入度)
	 */
	public void knnOtherNode(int remain){
		//遍历sourcedata处理当前未被分类的节点，存在冲突的节点可能又被返回到source，但在结果集中上存在
		System.out.println("当前剩余节点的长度： "+sourcedata.size());
		//获得当前节点的邻居节点,正常情况下节点的邻居集合中不会包涵分类中心，在InitKNNdata中已经将
		//中心节点的邻居全部分好类，移除了sourcedata（由于要移除sourcedata元素将影响遍历，将sourcedata赋值给一个新的遍历集合）
		ArrayList<Knode> tempsourcedata = (ArrayList<Knode>)sourcedata.clone();
		for(Knode currentNode : tempsourcedata){
			System.out.println("knode is "+currentNode.getNodeIndex());

			String finalCategory = "";
			//类型、权值暂存
			HashMap<String,Integer> tempResult = new HashMap<String,Integer>();
			
			//遍历当前节点的入度邻居
			ArrayList<AdjArrayObject> indegreeAdj = inDegreeList[currentNode.getNodeIndex()];
			//无邻居跳到下一个节点
			if(indegreeAdj==null || indegreeAdj.size()==0) continue;
		
			//更新判断条件，忽略边的权值，等以后分不开的时候，添加权值判断
			int indegreeAdjAllNum = 0;
			int AdjHasCategory = 0;
			//记录邻居数和已有分类的邻居权重
			int CategoryWeight = 0;
			
			for(AdjArrayObject adj : indegreeAdj){
				//循环获取当前的邻居
				int tempAdjIndex = adj.getNodeIndex();
				String adjIndexStr = tempAdjIndex+"";
				int tempAdjValue  = 1;
				int tempWeight = adj.getNodeWeight();
				
				//邻居数加1
				indegreeAdjAllNum += 1;
				System.out.print(" node "+tempAdjIndex+"  value "+tempAdjValue +"  |  ");
				
				//从结果集合中取出当前邻居的所属类别，若包含并且category不为空(分类不明确的节点类别被清空)
				if(resultMap.containsKey(adjIndexStr) && !resultMap.get(adjIndexStr).get("category").equals("")){
					//从resultMap中获取邻居的类别
					HashMap<String,String> adjNode = resultMap.get(adjIndexStr);
					//判断类别权重的表中有无该类别，有：取出相加； 无：直接加入
					if(tempResult.containsKey(adjNode.get("category"))){
						//init过程先忽略权重，仅按照边数
						//相同分类的邻居数+1
						tempAdjValue += 1;
						if(tempResult.containsKey(adjNode.get("category")+"Weight"))
							tempWeight += tempResult.get(adjNode.get("category")+"Weight");
					}
					tempResult.put(adjNode.get("category"), tempAdjValue);
					tempResult.put(adjNode.get("category")+"Weight", tempWeight);
					System.out.println("\n"+adjNode.get("category")+"  "+tempAdjValue+" W "+tempWeight);
					//已分类的节点数+1
					AdjHasCategory += 1;
				}else{
					continue;
				}
			}
			
			if(remain==0){
				//判断已有带权值的边所占比例是否达到多占比例
				for(Entry<String,Integer> e : tempResult.entrySet()){
					if(e.getKey().contains("Weight")) continue;
					//如果该类别权重所占比例 大于 0.8*0.8 (至少保证乘积大于0.5即可做决定)
					System.out.println("/"+((float)e.getValue()/AdjHasCategory) +"/");
					System.out.println(" "+(float)((e.getValue()/AdjHasCategory)*(AdjHasCategory/indegreeAdjAllNum)));
					if(((float)e.getValue()/indegreeAdjAllNum) > 0.5 && !e.getKey().contains("Weight")){
						finalCategory = e.getKey();
						break;
					}
				}
			}else if(remain==1){
				//判断已有带权值的边所占比例是否达到多占比例
				int tempMaxWeight = 0;
				for(Entry<String,Integer> e : tempResult.entrySet()){
					if(!e.getKey().contains("Weight")) continue;
					if(e.getValue() > tempMaxWeight){
						finalCategory = e.getKey().substring(0,e.getKey().indexOf('W'));
						tempMaxWeight = e.getValue();
						//带权值处理为两种类别节点相同的情况
					}else if(e.getValue() == tempMaxWeight){
						finalCategory = "X";
					}
				}
			}
			
			//判断结果，加入结果集，移除source集
			if(!finalCategory.equals("")){
				System.out.println(" Category is "+finalCategory);
				HashMap<String,String> value = new HashMap<String,String>();
				value.put("category",finalCategory);
				value.put("weight","*");
				resultMap.put(currentNode.getNodeIndex()+"", value);
				
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
		//剩余的是用上述不加权的方法处理不了，马上考虑边的权值进行分类
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
	 * 输出当前结果集reusultMap的结果
	 * author ZHP
	 * 2014年12月8日
	 */
	public void outResultSet(){
		
		Iterator<Entry<String,HashMap<String,String>>> it = resultMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,HashMap<String,String>> entry = it.next();
			System.out.println("\ncurrentNode is "+entry.getKey());
			HashMap<String,String> adjnode = entry.getValue();
			for(Entry<String,String> e : adjnode.entrySet()){
				System.out.print(" ->"+e.getKey()+"  : "+e.getValue()+"    ");
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
