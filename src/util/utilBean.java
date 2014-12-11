package util;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class utilBean {

	public static void outNewDot(String newDot,String outputPath){
		
		try{
			FileOutputStream out = new FileOutputStream(outputPath);
			out.write(newDot.getBytes());
			out.close();
		}catch(Exception e){e.printStackTrace();}
		
	}

	//输出结果集
	public static  <T> T printResult(ArrayList<T> result,String outputPath,int Scols,int Ecols){
		try{
			FileOutputStream out = new FileOutputStream(outputPath);
			String oneline = "";//行输出信息
			for(int i=0;i<result.size();i++){
				T currentOne = result.get(i);
				String properityResult = "";
				Class c =currentOne.getClass();
				//forName 通过类的名称，加载一个该类的对象，然而此处，我们得到了对象可直接用上述方法加载
				//Class c1 = Class.forName(result.get(i).getClass().toString().replace("class ", ""));
				Field[] fields=c.getDeclaredFields();
				if(Ecols>fields.length || Ecols<0)  Ecols = fields.length;
				int j=0;
				if(Scols>0) j = Scols-1; 
		    		for(;j < Ecols;j++){
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
