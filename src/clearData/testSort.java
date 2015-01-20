package clearData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

	public class testSort {
	/**
	 *设置边集合排序，方便邻接表的生成
	 */
	private static  Comparator<edgeBean> comparator = new Comparator<edgeBean>() {
		public int compare(edgeBean o1, edgeBean o2) {
			System.out.println("比较");
			System.out.println(o1.getStartNode()+"  "+o2.getStartNode());
			if (o1.getStartNode().compareTo( o2.getStartNode()) <0) {
				return -1;
			} else if  (o1.getStartNode().compareTo( o2.getStartNode()) == 0){
				System.out.println(o1.getEndNode()+" "+o2.getEndNode());
				if(o1.getEndNode().compareTo(o2.getEndNode())<=0){
					return -1;
				}
			}
			return 1;
		}
	};
	
	private static  Comparator<edgeBean> comparator2 = new Comparator<edgeBean>() {
		public int compare(edgeBean o1, edgeBean o2) {
			System.out.println("比较");
			if (o1.getWeight()>o2.getWeight()) {
				return 1;
			} 
			return 0;
		}
	};
	
	private static Comparator<edgeBean> as = new Comparator<edgeBean>() {
		
		@Override
		public int compare(edgeBean paramT1, edgeBean paramT2) {
			System.out.println("比较");
//			if (paramT1.getWeight()>paramT2.getWeight()) {
//				return 0;
//			} else{
//				return 1;
//			}
			System.out.println("----->"+paramT1.getWeight().compareTo(paramT2.getWeight()));
			return paramT1.getWeight().compareTo(paramT2.getWeight());
		}
	};
	
	public static void main(String[] args) {
		edgeBean a = new edgeBean("a","z",2);
		edgeBean b = new edgeBean("s","z",1);
		edgeBean c = new edgeBean("c","b",5);
		edgeBean d = new edgeBean("m","b",3);
		
		ArrayList<edgeBean> list = new ArrayList<edgeBean>();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		
		for(edgeBean e : list){
			System.out.println(e.getStartNode()+" "+e.getEndNode()+" "+e.getWeight());
		}
		
		System.out.println(System.getProperties().getProperty("os.name"));
		
		Collections.sort(list, comparator);
		System.out.println("*******************************************************************");
		for(edgeBean e : list){
			System.out.println(e.getStartNode()+" "+e.getEndNode()+" "+e.getWeight());
		}
		
	}
	
	
}
