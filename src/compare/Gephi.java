package compare;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import test.BDFResultNode;
import test.edge;
import test.node;
import util.testQFunction;
import util.testQunweight;
import AdjList.AdjArrayObject;
import KNNpackage.Knode;

public class Gephi {
	
	private ArrayList<Knode> sourcedata;
	//初始化结果list<node<category,weight>>
	private HashMap<String,HashMap<String,String>> resultMap = new HashMap<String, HashMap<String,String>>();
	private ArrayList<Knode> resultList;
	private ArrayList<Knode> finalSourcedata;
	private ArrayList<node> Nodes;
	private ArrayList<edge> Edges;
	private ArrayList<AdjArrayObject>[] inDegreeList;
	private ArrayList<AdjArrayObject>[] outDegreeList;
	//广度优先遍历结果
	private ArrayList<BDFResultNode> BDFReuslt ;
	
	public ArrayList<BDFResultNode> getBDFReuslt() {
		return BDFReuslt;
	}

	public void setBDFReuslt(ArrayList<BDFResultNode> bDFReuslt) {
		BDFReuslt = bDFReuslt;
	}

	public void string_process(String oneStr){
		String[] source = oneStr.split("\r\n");
		//按行（line）遍历文件，
		for(String sline : source){
			String[] lineData = sline.split(" ");
			HashMap<String, String> mapvalue = new HashMap<String, String>();
			mapvalue.put("category",lineData[1]);
			resultMap.put(lineData[0], mapvalue);
		}
	}
	
	public void getFileIn(){
		try{
			FileInputStream in = new FileInputStream("E:\\finalexp\\GephiAlgorithm.txt");
			BufferedInputStream input = new BufferedInputStream(in);
			byte bs[] = new byte[input.available()];
			in.read(bs);
			String s = new String(bs);
			string_process(s);
		//	getCategory(s);
			input.close();
			in.close();
		}catch(Exception e){e.printStackTrace();}
	}
	
	public float computeModel(){
		testQFunction Q = new testQFunction(resultMap, finalSourcedata, Nodes, Edges, inDegreeList, outDegreeList);
		return Q.softwareQFunction();
	}
	
	public float computeOUT(){
		testQFunction Q = new testQFunction(resultMap, finalSourcedata, Nodes, Edges, inDegreeList, outDegreeList);
		Q.setBDFReuslt(BDFReuslt);
		return Q.modelFunction();
	}
	
	
	
//	public void getCategory(String oneStr){
//		HashMap<String,ArrayList<AdjArrayObject>> categoryResultList = new HashMap<String, ArrayList<AdjArrayObject>>();
//
//		ArrayList<Knode> categorynode = new ArrayList<Knode>();
//		String[] source = oneStr.split("\r\n");
//		
//		for(String sline : source){
//			String[] lineData = sline.split(" ");
//			
//			AdjArrayObject adjObj = new AdjArrayObject();
//			adjObj.setNodeIndex(Integer.parseInt(lineData[0]));
//			
//			ArrayList<AdjArrayObject> tempOne ;
//			
//			//若当前结果类型集合中不含当前类型
//			if(categoryResultList.containsKey(lineData[1])){
//				tempOne = categoryResultList.get(lineData[1]);
//				if (tempOne.contains(lineData[0])) {
//					continue;
//				}
//			}else{
//				tempOne = new ArrayList<AdjArrayObject>();
//			}
//
//			tempOne.add(adjObj);
//			categoryResultList.put(lineData[1], tempOne);
//		}
//	}
//	
	
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
		this.finalSourcedata = sdata;
	}

	public HashMap<String, HashMap<String, String>> getResultMap() {
		return resultMap;
	}

	public void setResultMap(HashMap<String, HashMap<String, String>> resultMap) {
		this.resultMap = resultMap;
	}

	public ArrayList<Knode> getResultList() {
		return resultList;
	}



	public void setResultList(ArrayList<Knode> resultList) {
		this.resultList = resultList;
	}



	public ArrayList<Knode> getFinalSourcedata() {
		return finalSourcedata;
	}

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
