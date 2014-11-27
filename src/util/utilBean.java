package util;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class utilBean {
		//��������
		public static  <T> T printResult(ArrayList<T> result,String outputPath){
			try{
				FileOutputStream out = new FileOutputStream(outputPath);
				String oneline = "";//�������Ϣ
				for(int i=0;i<result.size();i++){
					T currentOne = result.get(i);
					String properityResult = "";
					Class c =currentOne.getClass();
					//forName ͨ��������ƣ�����һ������Ķ���Ȼ���˴������ǵõ��˶����ֱ����������������
					//Class c1 = Class.forName(result.get(i).getClass().toString().replace("class ", ""));
					Field[] fields=c.getDeclaredFields();
			    		for(int j = 0;j < fields.length;j++){
			    			String fieldName = fields[j].getName();
			    			Class type = fields[j].getType();
			    			String firstLetter = fieldName.substring(0,1).toUpperCase();
			    			String fieldGetterName = "get"+firstLetter+fieldName.substring(1);
		    				String fieldSetterName = "set"+firstLetter+fieldName.substring(1);
		    				Method getMethod = c.getMethod(fieldGetterName, new Class[]{});
		    				Method setMethod = c.getMethod(fieldSetterName, new Class[]{type});
		    				Object value = getMethod.invoke(currentOne,  new Object[] {});
		    				properityResult = properityResult + "   " +fieldName+" : "+value.toString()+" ";
			    		}
			    		properityResult += " \n";
			    		out.write(properityResult.getBytes());
			    	}
				out.close();
			}catch(Exception e){e.printStackTrace();}
			return null;
		}
				
}
