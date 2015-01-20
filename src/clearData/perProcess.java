package clearData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import AdjList.AdjArrayObject;
import test.edge;
import test.node;
import util.utilBean;

/**
 * 将多个ＤＯＴ文件合并成一个ＤＯＴ
 *  解析1个版本的dot实验结果，每各版本实验5次，将每次试验的边权值归1，
 *  合并后的权值根据在每次实验中出现的次数
 * */
public class perProcess {

	private static ArrayList<nodeBean> Nodes = new ArrayList<nodeBean>();//存储节点Node
	private static ArrayList<edgeBean>[] EdgesList =  new ArrayList[7];
	private static int nodeNumber = 0;
	
	/**
	 *设置边集合排序，方便邻接表的生成
	 */
	private static  Comparator<edgeBean> comparator = new Comparator<edgeBean>() {
		public int compare(edgeBean o1, edgeBean o2) {
			if (o1.getStartNode().compareTo( o2.getStartNode()) >0) {
				return 1;
			} else if  (o1.getStartNode().compareTo( o2.getStartNode()) == 0){
				if(o1.getEndNode().compareTo(o2.getEndNode())>=0){
					return 1;
				}
			}
			return -1;
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
			nodeBean one = (nodeBean)Nodes.get(i);
			if(one.getNname().equals(test)){
				index = one.getNindex();
				break;
			}
		}
		return index;
	}
	
	/**
	 * function 解析DOT文件信息生成点集Nodes和边集edgeBeans
	 * author ZHP
	 * 2014年12月6日
	 * @param oneStr 文件输入流的字符串
	 */
	public static void string_process(String oneStr,int i){
		EdgesList[i] = new ArrayList<edgeBean>();
		
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
				nodeNumber++;
				nodeBean one = new nodeBean(nodeNumber,test);
				Nodes.add(one);
				newDot  = newDot + test +"  [shape=ellipse] \n";
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
			//权重weigh默认1
			String[] weight_test = end_test[1].split("\"");
			int weight = 1;
			//去掉自己调用自己的边
			if(starts.equals(ends)) {
				line++;
				continue;
			}
			//存储到边集合
			edgeBean one_edgeBean = new edgeBean(starts,ends,weight);
			EdgesList[i].add(one_edgeBean);
			line++;
		}
		
		Collections.sort(EdgesList[i], comparator);
		
		//单独重复的边信息，加和去重
		edgeBean temp =new edgeBean("-1","-1",0);
		ArrayList<edgeBean> tempList = (ArrayList<edgeBean>)EdgesList[i].clone();
		for(edgeBean eg : tempList){
			if(eg.getStartNode().equals(temp.getStartNode()) && eg.getEndNode().equals(temp.getEndNode())){
				EdgesList[i].remove(temp);
			}
			temp = eg;
		}
	}
	
	public static void main(String[] args) {
		
		String ReadfilePath = "1.27";
		//读取DOT文件，得出点集合，边集合，输出到指定文件
		try{
			for(int i=1;i<=7;i++){//C:\Users\Administrator\Desktop\TAR\tar-1.12Expriment\1
				FileInputStream in = new FileInputStream("C:\\Users\\Administrator\\Desktop\\TAR\\tar-"+ReadfilePath+"Expriment\\"+i+"\\graph.dot");
				System.out.println("C:\\Users\\Administrator\\Desktop\\TAR\\tar-"+ReadfilePath+"Expriment\\"+i+"\\graph.dot");
		//		System.out.println("C:\\Users\\Administrator\\Desktop\\TAR\\tar-"+ReadfilePath+"Expriment\\"+ReadfilePath+"Newgraph.dot");
				BufferedInputStream input = new BufferedInputStream(in);
				
				byte bs[] = new byte[input.available()];
				in.read(bs);
				String s = new String(bs);
				
				//处理文件中的点集合和边集合
				string_process(s,i-1);
				input.close();
			}
		}catch(Exception e){System.out.println(e.toString());}
		
		//合并5次实验的信息
		ArrayList<edgeBean> allEdge = new ArrayList<edgeBean>();
		for(int i=0;i<EdgesList.length;i++){
			allEdge.addAll(EdgesList[i]);
		}
		
		Collections.sort(allEdge, comparator);
		
		//去重边，加权值
		edgeBean temp =new edgeBean("-1","-1",0);
		ArrayList<edgeBean> tempList = (ArrayList<edgeBean>)allEdge.clone();
		for(edgeBean eg : tempList){
			if(eg.getStartNode().equals(temp.getStartNode()) && eg.getEndNode().equals(temp.getEndNode())){
				eg.setWeight(temp.getWeight()+eg.getWeight());
				allEdge.remove(temp);
			}
			temp = eg;
		}
		
		for(edgeBean e : allEdge){
			newDot = newDot + e.getStartNode()  +" -> "+e.getEndNode()+"  [label=\""+e.getWeight()+" calls\" fontsize=\"10\"]\n ";
		}
		newDot = newDot + "}";
		//输出序号化的dot文件
		utilBean.outNewDot(newDot,"C:\\Users\\Administrator\\Desktop\\TAR\\tar-"+ReadfilePath+"Expriment\\"+ReadfilePath+"Newgraph.dot");
		System.out.println("C:\\Users\\Administrator\\Desktop\\TAR\\tar-"+ReadfilePath+"Expriment\\"+ReadfilePath+"Newgraph.dot");
		System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////");
	}
}
