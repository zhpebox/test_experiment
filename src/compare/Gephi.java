package compare;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	
	public void string_process(String oneStr){
		
	//	oneStr = oneStr.substring(oneStr.indexOf('{')+1, oneStr.lastIndexOf('}')).trim();
		//按‘\n’分解成行
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
			input.close();
			in.close();
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public float computeModel(){
		testQFunction Q = new testQFunction(resultMap, finalSourcedata, Nodes, Edges, inDegreeList, outDegreeList);
//		testQunweight UQ = new testQunweight(resultMap, finalSourcedata, Nodes, Edges, inDegreeList, outDegreeList);
		return Q.softwareQFunction();
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
