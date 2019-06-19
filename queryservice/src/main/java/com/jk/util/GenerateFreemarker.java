package com.jk.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class GenerateFreemarker {
	
	
	
	 //生成html	
		/**
		 * @param GeneratePath   生成路径
		 * @param templatePath   模板路径
		 * @param map            模板数据
		 * @param Ftlsite</pre>     模板名称
		 */
	    public static void createHtml(String GeneratePath, String templatePath, Map<String,Object> map, String Ftlsite){
	    	
	    	System.out.println("服务器生成路径:"+GeneratePath);
	    	
	    	
	      /*  CONFIGURATION CONFIGURATION = NEW CONFIGURATION();
	        CONFIGURATION.SETDEFAULTENCODING("UTF-8");  */
	        // ========================获取模板=========================================
	        
	        /*String filePath = req.getSession().getServletContext().getRealPath("/ftl/");*/	// FTL文件所存在的位置
	        
	        Configuration conf = new Configuration();
	        conf.setDefaultEncoding("UTF-8"); 
	        try {
				conf.setDirectoryForTemplateLoading(new File(templatePath));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}	
	        //加载模板文件目录
	        
	        Template t = null;
	        try {
	            t = conf.getTemplate(Ftlsite+".ftl"); // 获取模板名称
	            t.setEncoding("UTF-8");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        //===========================生成页面========================================
	        File outFile = new File(GeneratePath);
	        Writer out = null;
	        try {
	            out = new BufferedWriter(new OutputStreamWriter(	// OutputStreamWriter	输出流
	                    new FileOutputStream(outFile),"UTF-8"));
	        } catch (FileNotFoundException e1) {
	            e1.printStackTrace();	//printStackTrace  堆栈
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	 
	        try {
	            t.process(map, out);	//生成html文件
	        } catch (TemplateException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	
	    	
	    }
		
	
	
	
	
	
	
	
	
	
	

/*
	//生成页面
    public static boolean makeHtml(HttpServletRequest req,HashMap<String,Object> map,Integer id,String Ftlsite){
        System.out.println("--------------生成页面----**------------");
	       
     
        
        
        String path = "F:\\maven\\Freemarker\\src\\main\\webapp\\"+id+".html";
       
        
      //给静态文件定生成路径	设置文件保存的本地路径
		String filePath = req.getSession().getServletContext().getRealPath("/html/"+id+"html"+".html");
		System.out.println("服务器生成路径:"+filePath);
        
        
        try {
        	 //生成html
			createWord(req, filePath, map,Ftlsite);
		} catch (IOException e) {
			e.printStackTrace();
		}
 
        return true;
    }
    
	
    //获取模板
    public static void createWord(HttpServletRequest req,String path,Map<String, Object> dataMap,String Ftlsite) throws IOException {
    	 
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");  
        // ========================获取模板=========================================
        
        String filePath = req.getSession().getServletContext().getRealPath("/ftl/");	// FTL文件所存在的位置
        
        Configuration conf = new Configuration();
        conf.setDirectoryForTemplateLoading(new File(filePath));	//加载模板文件目录
        
        Template t = null;
        try {
            t = conf.getTemplate(Ftlsite+".ftl"); // 获取模板名称
            t.setEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //===========================生成页面========================================
        File outFile = new File(path);
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(	// OutputStreamWriter	输出流
                    new FileOutputStream(outFile),"UTF-8"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();	//printStackTrace  堆栈
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
 
        try {
            t.process(dataMap, out);	//生成html文件
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
 
	
	
}
