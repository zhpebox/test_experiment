package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class StatisNodeByCall {
	
	/**
	 * 统计每个函数节点的调用次数
	 * @throws FileNotFoundException 
	 */
	public static void countCallsForEachNode() throws IOException{

		//设立结果集合

		Map<String, Integer> resultMap = new HashMap<String,Integer>();
		//读取边文件急
		FileInputStream input = new FileInputStream("F:\\test_file\\edges_data.doc");
		BufferedInputStream in = new BufferedInputStream(input);
		byte bs[] = new byte[in.available()];
		in.read(bs);
		String s = new String(bs);
		String[] source = s.split("\n");
		for(String oneLine : source){
			System.out.println("oneLine = "+ oneLine);
			String[] lineStr = oneLine.trim().split(":");
			System.out.println(" -"+lineStr[1]+"");
			if(resultMap.get(lineStr[1])!=null){
				int callNum = resultMap.get(lineStr[1]);
				callNum += Integer.parseInt(lineStr[2]);
				resultMap.put(lineStr[1], callNum);
			}else{
				resultMap.put(lineStr[1],Integer.parseInt(lineStr[2]));
			}
		}
		
		//结果集合写入文件
		FileOutputStream out = new FileOutputStream("F:\\test_file\\nodeCall_data.doc");
		try{
			String writeLine = "";
			Iterator<Entry<String,Integer>> it = resultMap.entrySet().iterator();
			while(it.hasNext()){
				Entry<String,Integer> one = it.next();
				System.out.println("  the Entry is key = "+one.getKey()+" and value = "+one.getValue());
				writeLine = one.getKey()+"  "+one.getValue()+"\n";
				out.write(writeLine.getBytes());
			}
		}catch(Exception e){e.printStackTrace();}
		
		//每个节点的结果集合
	}

	public static void main(String[] args) throws IOException {
		StatisNodeByCall.countCallsForEachNode();
	}
}
