package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import compare.algorithmOne;

import util.testQFunction;
import util.utilBean;
import AdjList.AdjArrayObject;
import AdjList.EdgesToArray;
import KNNpackage.KNNalog;
public class read_search {
	
	private static ArrayList<node> Nodes = new ArrayList<node>();//�洢�ڵ�Node
	private static ArrayList<edge> Edges = new ArrayList<edge>();//�洢��Edges
	private static int MaxNodesIndex  = 0;
	private static ArrayList<AdjArrayObject>[] inDegreeList ;
	private static ArrayList<AdjArrayObject>[] outDegreeList;
	//���սڵ�ͱ�����dot�ļ�
	private static String newDot = "digraph newDOT { \n";
	
	/**
	 * ����Nodes�����Ƿ������ǰ�ڵ�
	 * author ZHP
	 * 2014��12��6��
	 * @param test�ڵ�����
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
	 * function ����DOT�ļ���Ϣ���ɵ㼯Nodes�ͱ߼�Edges
	 * author ZHP
	 * 2014��12��6��
	 * @param oneStr �ļ����������ַ���
	 */
	public static void string_process(String oneStr){
		oneStr = oneStr.substring(oneStr.indexOf('{')+1, oneStr.lastIndexOf('}')).trim();
		//����\n���ֽ����
		String[] source = oneStr.split("\n");
		//���У�line�������ļ���
		int line = 0;
		//�����ļ��еĺ����������ԡ�->����ʾ����
		while(line<source.length && !source[line].contains("->")){
			String test = source[line].trim();
			test = test.substring(0, test.indexOf(' '));
			line++;
			
			//������ǰ�㼯���Ƿ����ظ�Ԫ�أ��㣩,��������������õ����Ϣ
			if(ser_index(test) == -1){
				node one = new node(line,test);
				Nodes.add(one);
				MaxNodesIndex = line;
				newDot  = newDot + line +"  [shape=ellipse] \n";
			}
		}
		
		//���������еı���Ϣ
		while(line<source.length && source[line].contains("->")){
			//ͷ�ڵ�starts
			String[] test = source[line].split("->");
			String starts = test[0].trim();
			//β�ڵ�ends
			String[] end_test = test[1].split("\\[");
			String ends = end_test[0].trim();
			//Ȩ��weigh
			String[] weight_test = end_test[1].split("\"");
			String weigh = weight_test[1].trim();
			//ȥ���Լ������Լ��ı�
			if(starts.equals(ends)) {
				line++;
				continue;
			}
			//�洢���߼���
			edge one_edge = new edge(ser_index(starts),ser_index(ends),weigh);
			Edges.add(one_edge);
			newDot = newDot + one_edge.getS_node()  +" -> "+one_edge.getE_node()+"  [label=\""+one_edge.getWeight()+" calls\" fontsize=\"10\"]\n ";
			line++;
		}
		//�ظ��ı���Ϣ���Ӻ�ȥ��
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
		//��ȡDOT�ļ����ó��㼯�ϣ��߼��ϣ������ָ���ļ�
		long start = System.currentTimeMillis();
		try{
			FileInputStream in = new FileInputStream("E:\\Jworkspace\\expriment\\test\\data\\cflow-1.0.data");
			BufferedInputStream input = new BufferedInputStream(in);
			
			byte bs[] = new byte[input.available()];
			in.read(bs);
			String s = new String(bs);
			
			//�����ļ��еĵ㼯�Ϻͱ߼���
			string_process(s);
			input.close();
			
			System.out.println("�㼯��Nodes�Ĵ�С = "+Nodes.size());
			System.out.println("�߼���Edges�Ĵ�С = "+Edges.size());
			
			//����㼯�Ϻͱ߼��ϵ��ļ�
			utilBean.printResult(Nodes, "F:\\test_file\\node_data.doc",-1,-1);
			utilBean.printResult(Edges, "F:\\test_file\\edges_data.doc",-1,-1);
			
		}catch(Exception e){System.out.println(e.toString());}
		
		//�����Ż���dot�ļ�
		utilBean.outNewDot(newDot,"F:\\test_file\\newDot.data");
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//���ݳ���Ȼ�ȡ�ڽӱ�
		
		//����Edges���ϻ�ȡ����ȵ��ڽӱ�
		EdgesToArray getInAndOutDegreeList = new EdgesToArray();
		getInAndOutDegreeList.EdgeToAdjList(Edges, MaxNodesIndex);
		inDegreeList = getInAndOutDegreeList.getIndegreeArray();
		outDegreeList = getInAndOutDegreeList.getOutdegreeArray();
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//����ͼ���ڽӱ�
		/*
		//�߽������ڽӱ�
		System.out.println("�߽������ڽӱ�");
		ListToAdjacencyList changEdge = new ListToAdjacencyList();
		
		List<ArrayList<Integer>> resultArray = changEdge.changToAdjList(Edges,Nodes);
		changEdge.outPutTheResultArray(resultArray);
		*/
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//�������ڽӱ����
		System.out.println("\n--------------------------------������ȱ���");
		
		//����ͼ������ȱ���
		BDFclass bdf = new BDFclass();
		ArrayList<BDFResultNode> BDFReuslt = bdf.IterateTheArrayList(outDegreeList, Nodes);
		//���������ȱ������
		utilBean.printResult(BDFReuslt, "F:\\test_file\\BDF_data.doc",-1,-1);
		
		/*
		System.out.println("\n--------------------------------������ȱ���");
		//������ȱ���
		DFSclass dfs = new DFSclass();
		List<ArrayList<Integer>> dfsresult = dfs.IterateListToDFS(resultArray);
		dfs.PrintOutTheResult(dfsresult);
		*/
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//�ھӽڵ�
		HashMap<String,HashMap<String,Integer>> adjNodes = new HashMap<String,HashMap<String,Integer>>();
		edgeToNeighbourList edgeNeibour = new edgeToNeighbourList();
		try {
			adjNodes = edgeNeibour.computeAdjNode(Edges);
		} catch (Exception e) {e.printStackTrace();}
		
		/*
		//�������һ���ڽӽڵ�Ľ����
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
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		KNNalog knn = new KNNalog();
		//���÷���ڵ�
		 ArrayList<node> cnodes = new  ArrayList<node>();
			 node temp = new node();
			 temp.setIndex(1);
			 cnodes.add(temp);
			 temp = new node();
			 temp.setIndex(25);
			 cnodes.add(temp);
			 temp = new node();
			 temp.setIndex(32);
			 cnodes.add(temp);
		knn.setCategorynode(cnodes);
	//	knn.setAdjListofNode(adjNodes);
		knn.setInDegreeList(inDegreeList);
		knn.setOutDegreeList(outDegreeList);
		knn.setSourcedata(Nodes);
		//��ʼ�����ϣ�����ڵ㼰���ھӷ���
		knn.initKNNdata();
		//����ʣ��ڵ�
		knn.doKnn();
		System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		knn.outResultSet(0);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//ģ�黯��������
		knn.computeModel();		
		
		
		algorithmOne aloOne = new algorithmOne();
		aloOne.setSourcedata(Nodes);
		aloOne.setInDegreeList(inDegreeList);
		aloOne.setOutDegreeList(outDegreeList);
		aloOne.computeAlgorithm();
		
		
		
		
		long cost = System.currentTimeMillis()-start;
		System.out.println("\n\nִ�е�ʱ���ǣ�"+cost);
		System.out.println();
	}	
}
