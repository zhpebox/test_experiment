package test;

import java.util.ArrayList;
import java.util.List;

public class DFSclass {

	// �����ڽӱ�����������ȱ�������
	public List<ArrayList<Integer>> IterateListToDFS(List<ArrayList<Integer>> sourceList) {
		List<ArrayList<Integer>> resultList = new ArrayList<ArrayList<Integer>>();
		// ���嵱ǰ�ڵ�ı�������
		ArrayList<Integer> resultTemp = null;
		// ���б���������ϣ�ȥͷ�������ǰ�ڵ㣬��һ���������������
		for (ArrayList<Integer> line : sourceList) {
			int back = 0 ;//���ݱ�־
			int branch = 1;//��֧��Ŀ
			if (line != null && line.size() != 0) {
				// ʵ��������
				resultTemp = new ArrayList<Integer>();
				// ���ڵ������,��ʶָ��ǰ�ڵ�
				int currentNode = line.get(0);
				int currentPonint = 0;
				resultTemp.add(currentNode);
				// �жϵ�ǰ�ڵ��Ƿ���ݵ����ڵ㣬��ʶλ��>-1,�������ڵ����
				while (currentPonint > -1) {
					// �Ƿ���ں��ӽڵ�,������Ҫ�������жϴ��ڵ�ʱ��ֱ����ӣ���ֹ���α��������
					int addSuccess = 0;
					int hasAdList = 0;
					
					// ѭ�����ҵ�ǰ�ڵ���ڽӱ�
					for (ArrayList<Integer> linechild : sourceList) {
						
						if (linechild == null || linechild.size() == 0) continue;
						// �����ڽӱ�ı�ʶsize,�Ա���ǰ���ӽڵ㱻���ʹ�������������
						int size = 1;
						//ѭ�����ڽӱ�
						while (currentNode == linechild.get(0) && linechild.size() > size) {
							hasAdList = 1;
							int tempNode = linechild.get(size);
							if (!isQueueExist(resultTemp, tempNode)) {
								resultTemp.add(tempNode);
								currentNode = tempNode;
								currentPonint = resultTemp.size() - 1;
								addSuccess = 1;
								//�жϷ�֧���Ƿ��1
								if(back>0){
									branch++;
									back = 0;
								} 
								break;
							} else {
								size++;
							}
						}
						// �ҵ��ڽӱ���������ѭ��
						if (hasAdList == 1) break;
					}
					// ��ǰ���ڵ�ĺ���˳���������
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
		// ��ѯ�������Ƿ����ظ��ڵ㣬��ֹ�ظ�����
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
