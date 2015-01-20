package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import clearData.edgeBean;
import compare.algorithmOne;
import util.testQFunction;
import util.utilBean;
import AdjList.AdjArrayObject;
import AdjList.EdgesToArray;
import KNNpackage.KNNalog;
public class read_search {
	
	private static ArrayList<node> Nodes = new ArrayList<node>();//存储节点Node
	private static ArrayList<edge> Edges = new ArrayList<edge>();//存储边Edges
	private static int MaxNodesIndex  = 0;
	private static ArrayList<AdjArrayObject>[] inDegreeList ;
	private static ArrayList<AdjArrayObject>[] outDegreeList;
	//按照节点和边生成dot文件
	/**
	 *设置边集合排序，方便邻接表的生成
	 */
	private static  Comparator<InfuNode> comparator = new Comparator<InfuNode>() {
		public int compare(InfuNode o1, InfuNode o2) {
			return Integer.compare(o2.getInfValue(), o1.getInfValue());
		}
	};
	
	
	
	private static String newDot = "digraph newDOT { \n";
	
	/**
	 * 遍历Nodes集合是否包涵当前节点
	 * author ZHP
	 * 2014年12月6日
	 * @param test节点名称
	 * @return
	 */
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
	
	/**
	 * function 解析DOT文件信息生成点集Nodes和边集Edges
	 * author ZHP
	 * 2014年12月6日
	 * @param oneStr 文件输入流的字符串
	 */
	public static void string_process(String oneStr){
		
		oneStr = oneStr.substring(oneStr.indexOf('{')+1, oneStr.lastIndexOf('}')).trim();
		//按‘\n’分解成行
		String[] source = oneStr.split("\n");
		//按行（line）遍历文件，
		int line = 0;
		int addIndex =0;
		//遍历文件中的函数变量，以“->”标示结束
		while(line<source.length && !source[line].contains("->")){
			String test = source[line].trim();
			test = test.substring(0, test.indexOf(' '));
			line++;
			
			//遍历当前点集合是否有重复元素（点）,如果不包含则加入该点的信息
			if(ser_index(test) == -1){
				addIndex++;
				node one = new node(addIndex,test);
				Nodes.add(one);
				MaxNodesIndex = addIndex;
				newDot  = newDot + addIndex +"  [shape=ellipse] \n";
			}
		}
		
		//遍历函数中的边信息
		while(line<source.length && source[line].contains("->")){
			//头节点starts
			String[] test = source[line].split("->");
			String starts = test[0].trim();
			//尾节点ends
			String[] end_test = test[1].split("\\[");
			String ends = end_test[0].trim();
			//权重weigh
			String[] weight_test = end_test[1].split("\"");
			String weigh = weight_test[1].trim();
			//去掉自己调用自己的边
			if(starts.equals(ends)) {
				line++;
				continue;
			}
			//存储到边集合
			edge one_edge = new edge(ser_index(starts),ser_index(ends),weigh);
			Edges.add(one_edge);
			newDot = newDot + one_edge.getS_node()  +" -> "+one_edge.getE_node()+"  [label=\""+one_edge.getWeight()+" calls\" fontsize=\"10\"]\n ";
			line++;
		}
		//重复的边信息，加和去重
		edge temp =new edge(-1,-1,"0 calls");
		ArrayList<edge> tempList = (ArrayList<edge>)Edges.clone();
		for(edge eg : tempList){
			if(eg.getS_node()==temp.getS_node() && eg.getE_node()==temp.getE_node()){
				eg.setWeight(eg.getWeight()+temp.getWeight());
				Edges.remove(temp);
			}
			temp = eg;
		}
	}
	
	
	public static void main(String[] args) {
	
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//读取DOT文件，得出点集合，边集合，输出到指定文件
		long start = System.currentTimeMillis();
		try{
			FileInputStream in = new FileInputStream("F:\\test_file\\cflow\\CFLOW\\1.4Exp\\1.4Newgraph.dot");
			BufferedInputStream input = new BufferedInputStream(in);
			
			byte bs[] = new byte[input.available()];
			in.read(bs);
			String s = new String(bs);
			
			//处理文件中的点集合和边集合
			string_process(s);
			input.close();
			
			System.out.println("点集合Nodes的大小 = "+Nodes.size());
			System.out.println("边集合Edges的大小 = "+Edges.size());
			
			//输出点集合和边集合的文件
			utilBean.printResult(Nodes, "F:\\test_file\\cflow\\CFLOW\\1.4Exp\\1.4Nnode_data.doc",-1,-1);
			utilBean.printResult(Edges, "F:\\test_file\\cflow\\CFLOW\\1.4Exp\\1.4Nedges_data.doc",-1,-1);
			
		}catch(Exception e){System.out.println(e.toString());}
		
		//输出序号化的dot文件
		utilBean.outNewDot(newDot,"F:\\test_file\\cflow\\CFLOW\\1.4Exp\\1.4NnewDot.data");
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//根据出入度获取邻接表
		
		//依据Edges集合获取出/入度的邻接表
		EdgesToArray getInAndOutDegreeList = new EdgesToArray();
		getInAndOutDegreeList.EdgeToAdjList(Edges, MaxNodesIndex);
		inDegreeList = getInAndOutDegreeList.getIndegreeArray();
		outDegreeList = getInAndOutDegreeList.getOutdegreeArray();
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//输出节点的出度
		System.out.println("***************************************************************************");
		System.out.println("输出出度节点的邻居数");
		for(node outOne : Nodes){
			ArrayList<AdjArrayObject> currentAdj = outDegreeList[outOne.getIndex()];
			
			if(currentAdj==null || currentAdj.size()==0){
				System.out.println("current Node is "+outOne.getIndex()+"   出度大小0");
			}else{
				System.out.println("current Node is "+outOne.getIndex()+"   出度大小"+currentAdj.size());
			}
		}
		//按格式输出
		for(node outOne : Nodes){
			ArrayList<AdjArrayObject> currentAdj = outDegreeList[outOne.getIndex()];
			if(currentAdj==null || currentAdj.size()==0){
				System.out.println("0");
			}else{
				System.out.println(""+currentAdj.size());
			}
		}
		System.out.println("***************************************************************************");
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//按出度邻接表遍历
		System.out.println("\n--------------------------------广度优先遍历");
		
		//无向图广度优先遍历
		BDFclass bdf = new BDFclass();
		ArrayList<BDFResultNode> BDFReuslt = bdf.IterateTheArrayList(outDegreeList, Nodes);
		//输出广度优先遍历结果
		utilBean.printResult(BDFReuslt, "F:\\test_file\\cflow\\CFLOW\\1.1Exp\\BDF_data.doc",-1,-1);
		//加入节点影响力结果集
		ArrayList<InfuNode> infuList = new ArrayList<InfuNode>();
		for(BDFResultNode e : BDFReuslt){
			InfuNode one = new InfuNode();
			one.setNindex(e.getNodeIndex());
			one.setInfValue(e.getOutDegree()*e.getNodeBranch());
			infuList.add(one);
		}
		
		for(InfuNode e:infuList ){
			System.out.println(e.getNindex()+"     "+e.getInfValue());
		}
		//对影响力排序
		Collections.sort(infuList, comparator);
		
		//邻居节点
		HashMap<String,HashMap<String,Integer>> adjNodes = new HashMap<String,HashMap<String,Integer>>();
		edgeToNeighbourList edgeNeibour = new edgeToNeighbourList();
		try {
			adjNodes = edgeNeibour.computeAdjNode(Edges);
		} catch (Exception e) {e.printStackTrace();}
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		KNNalog knn = new KNNalog();
		//设置分类节点
		ArrayList<node> cnodes = new ArrayList<node>();
		int topK = 0;
		float tempQ = 0;
		float Q = 0;
		String qStr = "";
		while (topK < infuList.size() ) {
			tempQ = Q;

			node temp = new node();
			temp.setIndex(infuList.get(topK).getNindex());
			cnodes.add(temp);

			knn.setCategorynode(cnodes);
			knn.setEdges(Edges);
			knn.setNodes(Nodes);
			knn.setInDegreeList(inDegreeList);
			knn.setOutDegreeList(outDegreeList);
			knn.setSourcedata(Nodes);
			// step1
			knn.initKNNdata();
			// step2
			knn.doKnn();
		//	System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		//	knn.outResultSet(0);

			// 模块化函数度量
			Q = knn.computeModel();
			System.out.print("Q ^^^^ "+Q);
			qStr += Q + " ";
			topK++;
		}
		
		System.out.println("\nQQQQ"+qStr);
		//对比试验1
//		algorithmOne aloOne = new algorithmOne();
//		aloOne.setSourcedata(Nodes);
//		aloOne.setInDegreeList(inDegreeList);
//		aloOne.setOutDegreeList(outDegreeList);
//		aloOne.computeAlgorithm();
		
		long cost = System.currentTimeMillis()-start;
		System.out.println("\n\n执行的时间是："+cost);
		System.out.println();
	}	
}
