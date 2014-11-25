package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import KNNpackage.KNNalog;
public class read_search {
	
	private static ArrayList<node> Nodes = new ArrayList<node>();//存储节点Node
	private static ArrayList<edge> Edges = new ArrayList<edge>();//存储边Edges
	
	//遍历包含节点的序号
	public static int ser_index(String test){
		int index = -1;
		for(int i=0;i<Nodes.size();i++){
			node one = (node)Nodes.get(i);
			if(one.getNname().equals(test)){
				index = one.getIndex();
				break;
			}
		}
		return index;
	}
	
	//根据文件信息生成点集Nodes和边集Edges
	public static void string_process(String oneStr){
		
		oneStr = oneStr.substring(oneStr.indexOf('{')+1, oneStr.lastIndexOf('}')).trim();
		//按‘\n’分解成行
		String[] source = oneStr.split("\n");
		//按行（line）遍历文件，
		int line = 0;
		//遍历文件中的函数变量，以“->”标示结束
		while(line<source.length && !source[line].contains("->")){
			String test = source[line].trim();
			test = test.substring(0, test.indexOf(' '));
			line++;
			
			//遍历当前点集合是否有重复元素（点）,如果不包含则加入该点的信息
			if(ser_index(test) == -1){
			node one = new node(line,test);
				Nodes.add(one);
			}
		}
		
		//遍历函数中的边信息
		while(line<source.length){
			//头节点starts
			String[] test = source[line].split("->");
			String starts = test[0].trim();
			//尾节点ends
			String[] end_test = test[1].split("\\[");
			String ends = end_test[0].trim();
			//权重weigh
			String[] weight_test = end_test[1].split("\"");
			String weigh = weight_test[1].trim();
			//存储到边集合
			edge one_edge = new edge(ser_index(starts),ser_index(ends),weigh);
			Edges.add(one_edge);
			line++;
		}
		
	}
	
	//输出点集合文件 Nodes
	public static void print_Nodes(){
		try{
			FileOutputStream out = new FileOutputStream("F:\\test_file\\nodes.doc");
			
			String oneline = "";//每次输出的行信息
			for(int i=0;i<Nodes.size();i++){
				node one = (node)Nodes.get(i);
				oneline = one.getIndex() +"  "+one.getNname() +"\n";
				out.write(oneline.getBytes());
			}
			out.close();
			
		}catch(Exception e)
		{	System.out.println(e.toString());}
	}
	
	//输出边集合文件 Edges
	public static void print_Edges(){
		try{
			FileOutputStream out = new FileOutputStream("F:\\test_file\\edges_data.doc");
			
			String oneline = "";//每次输出的行信息
			for(int i=0;i<Edges.size();i++){
				edge one = (edge)Edges.get(i);
				oneline = one.getS_node() +":"+one.getE_node() +":"+one.getWeight()+"\n";
				out.write(oneline.getBytes());
			}
			out.close();
			
		}catch(Exception e)
		{	System.out.println(e.toString());}
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		try{
			FileInputStream in = new FileInputStream("E:\\Jworkspace\\expriment\\test\\src\\test\\graphdel7.txt");
			BufferedInputStream input = new BufferedInputStream(in);
			
			byte bs[] = new byte[input.available()];
			in.read(bs);
			String s = new String(bs);
			
			//处理文件中的点集合和边集合
			string_process(s);
			System.out.println("点集合Nodes的大小 = "+Nodes.size());
			System.out.println("边集合Edges的大小 = "+Edges.size());
			
			//输出点集合和边集合的文件
			print_Nodes();
			print_Edges();
			
			input.close();
		}catch(Exception e){System.out.println(e.toString());}
		
		long cost = System.currentTimeMillis()-start;
		System.out.println("执行的时间是："+cost);
		System.out.println();
		//边界生成邻接表
		System.out.println("边界生成邻接表");
		ListToAdjacencyList changEdge = new ListToAdjacencyList();
		
		List<ArrayList<Integer>> resultArray = changEdge.changToAdjList(Edges);
		changEdge.outPutTheResultArray(resultArray);
	
		System.out.println("\n--------------------------------广度优先遍历");
		
		//广度优先遍历
		BDFclass bdf = new BDFclass();
		bdf.IterateTheArrayList(resultArray);
		
		System.out.println("\n--------------------------------深度优先遍历");
		//深度优先遍历
		DFSclass dfs = new DFSclass();
		List<ArrayList<Integer>> dfsresult = dfs.IterateListToDFS(resultArray);
		dfs.PrintOutTheResult(dfsresult);
		
		//邻接节点
		HashMap<String,HashMap<String,Integer>> adjNodes = new HashMap<String,HashMap<String,Integer>>();
		edgeToNeighbourList edgeNeibour = new edgeToNeighbourList();
		try {
			adjNodes = edgeNeibour.computeAdjNode(Edges);
		} catch (Exception e) {e.printStackTrace();}
		
		/*
		//迭代输出一下邻接节点的结果集
		Iterator<Entry<String,HashMap<String,Integer>>> it = adjNodes.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,HashMap<String,Integer>> entry = it.next();
			System.out.println("\ncurrentNode is "+entry.getKey());
			HashMap<String,Integer> adjnode = entry.getValue();
			for(Entry<String,Integer> e : adjnode.entrySet()){
				System.out.print(" ->"+e.getKey()+"  : "+e.getValue()+"    ");
			}
		}
		*/
		KNNalog knn = new KNNalog();
		//设置分类节点
		 ArrayList<node> cnodes = new  ArrayList<node>();
			 node temp = new node();
			 temp.setIndex(5);
			 cnodes.add(temp);
			 temp = new node();
			 temp.setIndex(17);
			 cnodes.add(temp);
			 temp = new node();
			 temp.setIndex(30);
			 cnodes.add(temp);
		knn.setCategorynode(cnodes);
		knn.setAdjListofNode(adjNodes);
		knn.setSourcedata(Nodes);
		//初始化集合，将类节点及其邻居分类
		knn.initKNNdata();
		//处理剩余节点
		knn.doKnn();
		
	}	
}
