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
 * ������ģϣ��ļ��ϲ���һ���ģϣ�
 *  ����1���汾��dotʵ������ÿ���汾ʵ��5�Σ���ÿ������ı�Ȩֵ��1��
 *  �ϲ����Ȩֵ������ÿ��ʵ���г��ֵĴ���
 * */
public class perProcess {

	private static ArrayList<nodeBean> Nodes = new ArrayList<nodeBean>();//�洢�ڵ�Node
	private static ArrayList<edgeBean>[] EdgesList =  new ArrayList[7];
	private static int nodeNumber = 0;
	
	/**
	 *���ñ߼������򣬷����ڽӱ������
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
	 * ����Nodes�����Ƿ������ǰ�ڵ�
	 * author ZHP
	 * 2014��12��6��
	 * @param test�ڵ�����
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
	 * function ����DOT�ļ���Ϣ���ɵ㼯Nodes�ͱ߼�edgeBeans
	 * author ZHP
	 * 2014��12��6��
	 * @param oneStr �ļ����������ַ���
	 */
	public static void string_process(String oneStr,int i){
		EdgesList[i] = new ArrayList<edgeBean>();
		
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
				nodeNumber++;
				nodeBean one = new nodeBean(nodeNumber,test);
				Nodes.add(one);
				newDot  = newDot + test +"  [shape=ellipse] \n";
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
			//Ȩ��weighĬ��1
			String[] weight_test = end_test[1].split("\"");
			int weight = 1;
			//ȥ���Լ������Լ��ı�
			if(starts.equals(ends)) {
				line++;
				continue;
			}
			//�洢���߼���
			edgeBean one_edgeBean = new edgeBean(starts,ends,weight);
			EdgesList[i].add(one_edgeBean);
			line++;
		}
		
		Collections.sort(EdgesList[i], comparator);
		
		//�����ظ��ı���Ϣ���Ӻ�ȥ��
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
		//��ȡDOT�ļ����ó��㼯�ϣ��߼��ϣ������ָ���ļ�
		try{
			for(int i=1;i<=7;i++){//C:\Users\Administrator\Desktop\TAR\tar-1.12Expriment\1
				FileInputStream in = new FileInputStream("C:\\Users\\Administrator\\Desktop\\TAR\\tar-"+ReadfilePath+"Expriment\\"+i+"\\graph.dot");
				System.out.println("C:\\Users\\Administrator\\Desktop\\TAR\\tar-"+ReadfilePath+"Expriment\\"+i+"\\graph.dot");
		//		System.out.println("C:\\Users\\Administrator\\Desktop\\TAR\\tar-"+ReadfilePath+"Expriment\\"+ReadfilePath+"Newgraph.dot");
				BufferedInputStream input = new BufferedInputStream(in);
				
				byte bs[] = new byte[input.available()];
				in.read(bs);
				String s = new String(bs);
				
				//�����ļ��еĵ㼯�Ϻͱ߼���
				string_process(s,i-1);
				input.close();
			}
		}catch(Exception e){System.out.println(e.toString());}
		
		//�ϲ�5��ʵ�����Ϣ
		ArrayList<edgeBean> allEdge = new ArrayList<edgeBean>();
		for(int i=0;i<EdgesList.length;i++){
			allEdge.addAll(EdgesList[i]);
		}
		
		Collections.sort(allEdge, comparator);
		
		//ȥ�رߣ���Ȩֵ
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
		//�����Ż���dot�ļ�
		utilBean.outNewDot(newDot,"C:\\Users\\Administrator\\Desktop\\TAR\\tar-"+ReadfilePath+"Expriment\\"+ReadfilePath+"Newgraph.dot");
		System.out.println("C:\\Users\\Administrator\\Desktop\\TAR\\tar-"+ReadfilePath+"Expriment\\"+ReadfilePath+"Newgraph.dot");
		System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////");
	}
}
