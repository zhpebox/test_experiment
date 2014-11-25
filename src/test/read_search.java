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
	
	private static ArrayList<node> Nodes = new ArrayList<node>();//�洢�ڵ�Node
	private static ArrayList<edge> Edges = new ArrayList<edge>();//�洢��Edges
	
	//���������ڵ�����
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
	
	//�����ļ���Ϣ���ɵ㼯Nodes�ͱ߼�Edges
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
			}
		}
		
		//���������еı���Ϣ
		while(line<source.length){
			//ͷ�ڵ�starts
			String[] test = source[line].split("->");
			String starts = test[0].trim();
			//β�ڵ�ends
			String[] end_test = test[1].split("\\[");
			String ends = end_test[0].trim();
			//Ȩ��weigh
			String[] weight_test = end_test[1].split("\"");
			String weigh = weight_test[1].trim();
			//�洢���߼���
			edge one_edge = new edge(ser_index(starts),ser_index(ends),weigh);
			Edges.add(one_edge);
			line++;
		}
		
	}
	
	//����㼯���ļ� Nodes
	public static void print_Nodes(){
		try{
			FileOutputStream out = new FileOutputStream("F:\\test_file\\nodes.doc");
			
			String oneline = "";//ÿ�����������Ϣ
			for(int i=0;i<Nodes.size();i++){
				node one = (node)Nodes.get(i);
				oneline = one.getIndex() +"  "+one.getNname() +"\n";
				out.write(oneline.getBytes());
			}
			out.close();
			
		}catch(Exception e)
		{	System.out.println(e.toString());}
	}
	
	//����߼����ļ� Edges
	public static void print_Edges(){
		try{
			FileOutputStream out = new FileOutputStream("F:\\test_file\\edges_data.doc");
			
			String oneline = "";//ÿ�����������Ϣ
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
			
			//�����ļ��еĵ㼯�Ϻͱ߼���
			string_process(s);
			System.out.println("�㼯��Nodes�Ĵ�С = "+Nodes.size());
			System.out.println("�߼���Edges�Ĵ�С = "+Edges.size());
			
			//����㼯�Ϻͱ߼��ϵ��ļ�
			print_Nodes();
			print_Edges();
			
			input.close();
		}catch(Exception e){System.out.println(e.toString());}
		
		long cost = System.currentTimeMillis()-start;
		System.out.println("ִ�е�ʱ���ǣ�"+cost);
		System.out.println();
		//�߽������ڽӱ�
		System.out.println("�߽������ڽӱ�");
		ListToAdjacencyList changEdge = new ListToAdjacencyList();
		
		List<ArrayList<Integer>> resultArray = changEdge.changToAdjList(Edges);
		changEdge.outPutTheResultArray(resultArray);
	
		System.out.println("\n--------------------------------������ȱ���");
		
		//������ȱ���
		BDFclass bdf = new BDFclass();
		bdf.IterateTheArrayList(resultArray);
		
		System.out.println("\n--------------------------------������ȱ���");
		//������ȱ���
		DFSclass dfs = new DFSclass();
		List<ArrayList<Integer>> dfsresult = dfs.IterateListToDFS(resultArray);
		dfs.PrintOutTheResult(dfsresult);
		
		//�ڽӽڵ�
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
		KNNalog knn = new KNNalog();
		//���÷���ڵ�
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
		//��ʼ�����ϣ�����ڵ㼰���ھӷ���
		knn.initKNNdata();
		//����ʣ��ڵ�
		knn.doKnn();
		
	}	
}
