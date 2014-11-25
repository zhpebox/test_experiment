package test;

import java.util.ArrayList;
import java.util.List;

public class DFSclass {

	// 遍历邻接表，生成深度优先遍历序列
	public List<ArrayList<Integer>> IterateListToDFS(List<ArrayList<Integer>> sourceList) {
		List<ArrayList<Integer>> resultList = new ArrayList<ArrayList<Integer>>();
		// 定义当前节点的遍历序列
		ArrayList<Integer> resultTemp = null;
		// 按行遍历结果集合，去头结点做当前节点，第一个孩子入遍历集合
		for (ArrayList<Integer> line : sourceList) {
			int back = 0 ;//回溯标志
			int branch = 1;//分支数目
			if (line != null && line.size() != 0) {
				// 实例化队列
				resultTemp = new ArrayList<Integer>();
				// 根节点入队列,标识指向当前节点
				int currentNode = line.get(0);
				int currentPonint = 0;
				resultTemp.add(currentNode);
				// 判断当前节点是否回溯到根节点，标识位置>-1,即到根节点结束
				while (currentPonint > -1) {
					// 是否存在孩子节点,由于需要遍历，判断存在的时候直接添加，防止二次遍历在添加
					int addSuccess = 0;
					int hasAdList = 0;
					
					// 循环查找当前节点的邻接表
					for (ArrayList<Integer> linechild : sourceList) {
						
						if (linechild == null || linechild.size() == 0) continue;
						// 给出邻接表的标识size,以备当前孩子节点被访问过，继续向后访问
						int size = 1;
						//循环该邻接表
						while (currentNode == linechild.get(0) && linechild.size() > size) {
							hasAdList = 1;
							int tempNode = linechild.get(size);
							if (!isQueueExist(resultTemp, tempNode)) {
								resultTemp.add(tempNode);
								currentNode = tempNode;
								currentPonint = resultTemp.size() - 1;
								addSuccess = 1;
								//判断分支数是否加1
								if(back>0){
									branch++;
									back = 0;
								} 
								break;
							} else {
								size++;
							}
						}
						// 找到邻接表，可以跳出循环
						if (hasAdList == 1) break;
					}
					// 当前及节点的孩子顺利进入队列
					if (addSuccess != 1){
						currentPonint--;
						back = 1;
						if(currentPonint>-1)
						currentNode = resultTemp.get(currentPonint);
					}
				}
			}
			resultList.add(resultTemp);
			if(resultTemp!=null && resultTemp.size()!=0){
				System.out.println("The currate node is "+resultTemp.get(0)+"       branch = "+branch);
			}
			System.out.println();
		}
		return resultList;
	}

	public boolean isQueueExist(ArrayList<Integer> resultTemp, int currentNode) {
		// 查询队列中是否有重复节点，防止重复遍历
		boolean isExist = false;
		for (Integer one : resultTemp) {
			if (one == currentNode) {
				isExist = true;
			}
		}
		return isExist;
	}

	public void PrintOutTheResult(List<ArrayList<Integer>> resultList){
		for(ArrayList<Integer> resultLine : resultList){
			if(resultLine==null || resultLine.size()<1) continue;
			System.out.println("The current node is "+resultLine.get(0));
			for(Integer result : resultLine){
				System.out.print(" "+result);
			}
			System.out.println();
		}
	}
}
