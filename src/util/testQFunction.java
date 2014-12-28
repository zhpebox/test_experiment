package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import AdjList.AdjArrayObject;

/**
 * 测试模块度
 * @author ZHP
 * 2014年12月14日
 */
public class testQFunction {

	public static int modelFunction(HashMap<String, ArrayList<AdjArrayObject>> categoryResultList, ArrayList<AdjArrayObject>[] outDegreeList,HashMap<String,HashMap<String,String>> resultMap){
		int result = 1;

		for(Entry<String,ArrayList<AdjArrayObject>> oneCateList : categoryResultList.entrySet()){
			System.out.println("The Category is "+ oneCateList.getKey());
			int inNum = 0;
			int outNum = 0;
			ArrayList<AdjArrayObject> resultList = oneCateList.getValue();
			for(AdjArrayObject node : resultList){
				System.out.println("    node is  "+node.getNodeIndex()+"     Weight "+node.getNodeWeight());
				//按照模块中的每个节点的出度内外比例计算
				ArrayList<AdjArrayObject> list = outDegreeList[node.getNodeIndex()];
				if(list!=null && list.size()!=0){
					for(AdjArrayObject adj : list){
						System.out.println(adj.getNodeIndex());
						if(resultList.contains(adj)){
							inNum += 1;
						} else {
							outNum += 1;
						}
					}
				}else{
					continue;
				}
			}
			System.out.println("  in = "+inNum+"  out = "+outNum);
		}
		return 0;
	}
}
