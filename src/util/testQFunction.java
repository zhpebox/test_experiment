package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import test.BDFResultNode;
import test.edge;
import test.node;
import AdjList.AdjArrayObject;
import KNNpackage.Knode;

/**
 * ����ģ���
 * @author ZHP
 * 2014��12��14��
 */
public class testQFunction {
	
	private  HashMap<String,HashMap<String,String>> resultMap;
	private  ArrayList<Knode> sourcedata;
	private  ArrayList<node> Nodes ;//�洢�ڵ�Node
	private  ArrayList<edge> Edges;//�洢��Edges
	private  ArrayList<AdjArrayObject>[] inDegreeList;
	private  ArrayList<AdjArrayObject>[] outDegreeList;
	private  int[][] adjMatrix;
	private  int totalWeight = 1; //���бߵ�Ȩ��֮��

	private ArrayList<BDFResultNode> BDFReuslt  = new ArrayList<BDFResultNode>();
	
	public testQFunction(
			HashMap<String, HashMap<String, String>> resultMap,
			ArrayList<Knode> sourcedata, ArrayList<node> nodes,
			ArrayList<edge> edges,
			ArrayList<AdjArrayObject>[] inDegreeList,
			ArrayList<AdjArrayObject>[] outDegreeList) {
		this.resultMap = resultMap;
		this.sourcedata = sourcedata;
		this.Nodes = nodes;
		this.Edges = edges;
		this.inDegreeList = inDegreeList;
		this.outDegreeList = outDegreeList;
		int total = 0;
		for(edge e:Edges){
			total += e.getWeight();
			System.out.println(total+"  "+e.getWeight());
		}
		this.totalWeight = total;
		this.genMatrix();
	}
	

	/**
	 * ���ݵ����Ϣ�����ڽӾ���
	 * author ZHP
	 * 2015��1��19��
	 */
	public  void genMatrix(){
		adjMatrix =new  int [Nodes.size()][Nodes.size()];
		//�����ʼ��Ϊ0��
		for(int i = 0;i<adjMatrix.length;i++){
			for(int j=0;j<adjMatrix[i].length;j++){
				adjMatrix[i][j] =0;
			}
		}
		//�������б���Ϣ   
		for(edge e : Edges){
			int start = e.getS_node();
			int end = e.getE_node();
			int weight = e.getWeight();
			adjMatrix[start-1][end-1] = weight;
		}
	}
	
	/**
	 *�����ڿ˺���,�Ƚ������ڵ�������ͬ�����ͬ��1����ͬ��0
	 * author ZHP
	 * 2015��1��19��
	 * @param one
	 * @param two
	 * @return
	 */
	public  int KroneckerFun(Knode one, Knode two){
		String oneCategory = "";
		String twoCategory = "";
		if(resultMap.containsKey(one.getNodeIndex()+"")){
			oneCategory =  resultMap.get(one.getNodeIndex()+"").get("category");
		}
		if(resultMap.containsKey(two.getNodeIndex()+"")){
			twoCategory =  resultMap.get(two.getNodeIndex()+"").get("category");
		}
		if(oneCategory.equals(twoCategory)){
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 *�����ڿ˺���,�Ƚ������ڵ�������ͬ�����ͬ��1����ͬ��0
	 * author ZHP
	 * 2015��1��19��
	 * @param one
	 * @param two
	 * @return
	 */
	public  int KroneckerFuns(Knode one, Knode two){
		String oneCategory = "";
		String twoCategory = "";
		if(resultMap.containsKey(one.getNodeIndex()+"")){
			oneCategory =  resultMap.get(one.getNodeIndex()+"").get("category");
		}
		if(resultMap.containsKey(two.getNodeIndex()+"")){
			twoCategory =  resultMap.get(two.getNodeIndex()+"").get("category");
		}
		if(oneCategory.equals(twoCategory) || oneCategory.equals("X") || twoCategory.equals("X")){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * ����ģ�黯Q������Ȩֵ����ͼ��
	 * author ZHP
	 * 2015��1��19��
	 * @param inDegreeList
	 * @param outDegreeList
	 * @return
	 */
	public  float softwareQFunction( ){
		float Q_gen = 0; //����ģ���
		
		for(Knode start : sourcedata){ //˫��ѭ�������������
			for(Knode end : sourcedata){
				System.out.println(start.getNodeIndex()+"   "+end.getNodeIndex());
				int out = 0;
				if(outDegreeList[start.getNodeIndex()]!=null){
					for(AdjArrayObject e : outDegreeList[start.getNodeIndex()]){
						out += e.getNodeWeight();
					}
				}
				int in = 0;
				if(inDegreeList[end.getNodeIndex()]!=null){
					for(AdjArrayObject e: inDegreeList[end.getNodeIndex()]){
						in += e.getNodeWeight();
					}
				}
				int existWeight = adjMatrix[start.getNodeIndex()-1][end.getNodeIndex()-1] ;
				Q_gen += (existWeight - (float)out*in/totalWeight ) *KroneckerFun(start, end);
				System.out.println("("+existWeight+" -"+out+" *  "+in+"  /  "+totalWeight+")  * "+KroneckerFun(start, end)+
						"   "+(existWeight - (float)out*in/totalWeight ) *KroneckerFun(start, end)
						+"   "+Q_gen);
			}
		}
		Q_gen = Q_gen/totalWeight;
		return Q_gen;
	}
	
	
	/**
	 * ��ͬ��Q���������Ǹ�������ĸ��ھۡ�����϶�����һ��������׼
	 */
	public float  couplingAndcohesion(){
		float ccQ = 0;
		float ccTotal = 0;
		
		//��������ߵ���Ϣ
		for(edge e :Edges){
			//��ʼ��Դ����յ�
			Knode  start = new Knode();
			start.setNodeIndex(e.getS_node());
			Knode end = new Knode();
			end.setNodeIndex(e.getE_node());
			int weight = e.getWeight();
			
			ccQ += (float)1 * KroneckerFun(start, end);
			ccTotal += (float)1;
		}
		
		ccQ = ccQ/ccTotal;
		
		return ccQ;
	}
	
	
	/**
	 * ��������ڵ�����������Ƿ��ڵ�ǰģ����
	 * 20150128�޸ģ����ĳ����ھ�ΪBDF�ھ�
	 */
	public float modelFunction() {
		float Q = 0;
		int in = 0;
		int out = 0;
		
		for(Knode node : sourcedata){
			BDFResultNode BDFser = new BDFResultNode();
			BDFser.setNodeIndex(node.getNodeIndex());
			int index = BDFReuslt.indexOf(BDFser);
			BDFResultNode nodeBDF = BDFReuslt.get(index);
			if(nodeBDF==null) continue;
			String[] adj = nodeBDF.getNodeResult().split(",");
			for(String e : adj){
				Knode outnode = new Knode();
				outnode.setNodeIndex(Integer.parseInt(e));
				if(KroneckerFuns(node, outnode)==1){
					in += 1;
				}else{
					out += 1;
				}
			}
			
		}
		Q = (float)in/(in + out);
		return Q;
	}


	public ArrayList<BDFResultNode> getBDFReuslt() {
		return BDFReuslt;
	}


	public void setBDFReuslt(ArrayList<BDFResultNode> bDFReuslt) {
		BDFReuslt = bDFReuslt;
	}

}
