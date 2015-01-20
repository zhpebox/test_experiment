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
	
	private static ArrayList<node> Nodes = new ArrayList<node>();//�洢�ڵ�Node
	private static ArrayList<edge> Edges = new ArrayList<edge>();//�洢��Edges
	private static int MaxNodesIndex  = 0;
	private static ArrayList<AdjArrayObject>[] inDegreeList ;
	private static ArrayList<AdjArrayObject>[] outDegreeList;
	//���սڵ�ͱ�����dot�ļ�
	/**
	 *���ñ߼������򣬷����ڽӱ������
	 */
	private static  Comparator<InfuNode> comparator = new Comparator<InfuNode>() {
		public int compare(InfuNode o1, InfuNode o2) {
			return Integer.compare(o2.getInfValue(), o1.getInfValue());
		}
	};
	
	
	
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
		int addIndex =0;
		//�����ļ��еĺ����������ԡ�->����ʾ����
		while(line<source.length && !source[line].contains("->")){
			String test = source[line].trim();
			test = test.substring(0, test.indexOf(' '));
			line++;
			
			//������ǰ�㼯���Ƿ����ظ�Ԫ�أ��㣩,��������������õ����Ϣ
			if(ser_index(test) == -1){
				addIndex++;
				node one = new node(addIndex,test);
				Nodes.add(one);
				MaxNodesIndex = addIndex;
				newDot  = newDot + addIndex +"  [shape=ellipse] \n";
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
			FileInputStream in = new FileInputStream("F:\\test_file\\cflow\\CFLOW\\1.4Exp\\1.4Newgraph.dot");
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
			utilBean.printResult(Nodes, "F:\\test_file\\cflow\\CFLOW\\1.4Exp\\1.4Nnode_data.doc",-1,-1);
			utilBean.printResult(Edges, "F:\\test_file\\cflow\\CFLOW\\1.4Exp\\1.4Nedges_data.doc",-1,-1);
			
		}catch(Exception e){System.out.println(e.toString());}
		
		//�����Ż���dot�ļ�
		utilBean.outNewDot(newDot,"F:\\test_file\\cflow\\CFLOW\\1.4Exp\\1.4NnewDot.data");
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//���ݳ���Ȼ�ȡ�ڽӱ�
		
		//����Edges���ϻ�ȡ��/��ȵ��ڽӱ�
		EdgesToArray getInAndOutDegreeList = new EdgesToArray();
		getInAndOutDegreeList.EdgeToAdjList(Edges, MaxNodesIndex);
		inDegreeList = getInAndOutDegreeList.getIndegreeArray();
		outDegreeList = getInAndOutDegreeList.getOutdegreeArray();
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//����ڵ�ĳ���
		System.out.println("***************************************************************************");
		System.out.println("������Ƚڵ���ھ���");
		for(node outOne : Nodes){
			ArrayList<AdjArrayObject> currentAdj = outDegreeList[outOne.getIndex()];
			
			if(currentAdj==null || currentAdj.size()==0){
				System.out.println("current Node is "+outOne.getIndex()+"   ���ȴ�С0");
			}else{
				System.out.println("current Node is "+outOne.getIndex()+"   ���ȴ�С"+currentAdj.size());
			}
		}
		//����ʽ���
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
		//�������ڽӱ����
		System.out.println("\n--------------------------------������ȱ���");
		
		//����ͼ������ȱ���
		BDFclass bdf = new BDFclass();
		ArrayList<BDFResultNode> BDFReuslt = bdf.IterateTheArrayList(outDegreeList, Nodes);
		//���������ȱ������
		utilBean.printResult(BDFReuslt, "F:\\test_file\\cflow\\CFLOW\\1.1Exp\\BDF_data.doc",-1,-1);
		//����ڵ�Ӱ���������
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
		//��Ӱ��������
		Collections.sort(infuList, comparator);
		
		//�ھӽڵ�
		HashMap<String,HashMap<String,Integer>> adjNodes = new HashMap<String,HashMap<String,Integer>>();
		edgeToNeighbourList edgeNeibour = new edgeToNeighbourList();
		try {
			adjNodes = edgeNeibour.computeAdjNode(Edges);
		} catch (Exception e) {e.printStackTrace();}
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		KNNalog knn = new KNNalog();
		//���÷���ڵ�
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

			// ģ�黯��������
			Q = knn.computeModel();
			System.out.print("Q ^^^^ "+Q);
			qStr += Q + " ";
			topK++;
		}
		
		System.out.println("\nQQQQ"+qStr);
		//�Ա�����1
//		algorithmOne aloOne = new algorithmOne();
//		aloOne.setSourcedata(Nodes);
//		aloOne.setInDegreeList(inDegreeList);
//		aloOne.setOutDegreeList(outDegreeList);
//		aloOne.computeAlgorithm();
		
		long cost = System.currentTimeMillis()-start;
		System.out.println("\n\nִ�е�ʱ���ǣ�"+cost);
		System.out.println();
	}	
}
