package com.bolo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

/**
 * 
 * 功能描述：创建TXT文件并进行读、写、修改操作
 *      
 * @author <a href="mailto:zhanghhui@126.com">KenZhang</a>
 * @version 1.0 
 * Creation date: 2007-12-18 - 下午06:48:45
 */
public class TxtRwriteFile {
	 public  BufferedReader bufread;
	    //指定文件路径和名称
	    private  String path = "";
	    private  File filename = null;
	    private  String readStr ="";

	    public  TxtRwriteFile(String path){
	    	try{
	    		openTxtFile(path);
	    	}catch(Exception ex){
	    		
	    	}
	    }
	    /**
	     * 创建文本文件.
	     * @throws IOException 
	     * 
	     */
	    public  void openTxtFile(String path) throws IOException{
	    	if(!"".equals(path)&&this.filename==null){
	    		this.path=path;
	    		filename = new File(path);
            }
	    	if (!"".equals(path)&&!filename.exists()) {
	            filename.createNewFile();
	            System.err.println(filename + "已创建！");
	        }
	    }
	    
	    /**
	     * 读取文本文件.
	     * 
	     */
	    public  String readTxtFile(){
	        String read;
	        FileReader fileread;
	        try {
	            fileread = new FileReader(filename);
	            bufread = new BufferedReader(fileread);
	            try {
	                while ((read = bufread.readLine()) != null) {
	                    readStr = readStr + read+ "\r\n";
	                }
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        System.out.println("文件内容是:"+ "\r\n" + readStr);
	        return readStr;
	    }
	    
	    /**
	     * 写文件.
	     * 
	     */
	    public  void writeTxtToTop(String newStr) throws IOException{
	        //先读取原有文件内容，然后进行写入操作
	        String filein = newStr + "\r\n" + readStr + "\r\n";
	        RandomAccessFile mm = null;
	        try {
	            mm = new RandomAccessFile(filename, "rw");
	            mm.writeBytes(filein);
	        } catch (IOException e1) {
	            // TODO 自动生成 catch 块
	            e1.printStackTrace();
	        } finally {
	            if (mm != null) {
	                try {
	                    mm.close();
	                } catch (IOException e2) {
	                    // TODO 自动生成 catch 块
	                    e2.printStackTrace();
	                }
	            }
	        }
	    }
	    
	    /**
	     * 将文件中指定内容的第一行替换为其它内容.
	     * 
	     * @param oldStr
	     *            查找内容
	     * @param replaceStr
	     *            替换内容
	     */
	    public  void replaceTxtByStr(String oldStr,String replaceStr) {
	        String temp = "";
	        try {
	            File file = new File(path);
	            FileInputStream fis = new FileInputStream(file);
	            InputStreamReader isr = new InputStreamReader(fis);
	            BufferedReader br = new BufferedReader(isr);
	            StringBuffer buf = new StringBuffer();

	            // 保存该行前面的内容
	            for (int j = 1; (temp = br.readLine()) != null
	                    && !temp.equals(oldStr); j++) {
	                buf = buf.append(temp);
	                buf = buf.append(System.getProperty("line.separator"));
	            }

	            // 将内容插入
	            buf = buf.append(replaceStr);

	            // 保存该行后面的内容
	            while ((temp = br.readLine()) != null) {
	                buf = buf.append(System.getProperty("line.separator"));
	                buf = buf.append(temp);
	            }

	            br.close();
	            FileOutputStream fos = new FileOutputStream(file);
	            PrintWriter pw = new PrintWriter(fos);
	            pw.write(buf.toString().toCharArray());
	            pw.flush();
	            pw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    /** 
	    * 写文件 
	    * 
	    * @param newStr 
	    *            新内容 
	    * @throws IOException 
	    */ 
	    public  boolean writeTxtToend(String newStr) 
	    throws IOException { 
	    	// 先读取原有文件内容，然后进行写入操作 
	    	boolean flag = false; 
	    	String filein = newStr + "\r\n"; 
	    	String temp = ""; 
	    	
	    	FileInputStream fis = null; 
	    	InputStreamReader isr = null; 
	    	BufferedReader br = null; 
	    	
	    	FileOutputStream fos = null; 
	    	PrintWriter pw = null; 
	    	try { 
	    	// 文件路径 
	    	File file = new File(path); 
	    	// 将文件读入输入流 
	    	fis = new FileInputStream(file); 
	    	isr = new InputStreamReader(fis); 
	    	br = new BufferedReader(isr); 
	    	StringBuffer buf = new StringBuffer(); 
	    	
	    	// 保存该文件原有的内容 
	    	for (int j = 1; (temp = br.readLine()) != null; j++) { 
	    	buf = buf.append(temp); 
	    	// System.getProperty("line.separator") 
	    	// 行与行之间的分隔符 相当于“\n” 
	    	buf = buf.append(System.getProperty("line.separator")); 
	    	} 
	    	buf.append(filein); 
	    	
	    	fos = new FileOutputStream(file); 
	    	pw = new PrintWriter(fos); 
	    	pw.write(buf.toString().toCharArray()); 
	    	pw.flush(); 
	    	flag = true; 
	    } catch (IOException e1) { 
	    // TODO 自动生成 catch 块 
	    throw e1; 
	    } finally { 
	    	if (pw != null) { 
	    	pw.close(); 
	    	} 
	    	if (fos != null) { 
	    	fos.close(); 
	    	} 
	    	if (br != null) { 
	    	br.close(); 
	    	} 
	    	if (isr != null) { 
	    	isr.close(); 
	    	} 
	    	if (fis != null) { 
	    	fis.close(); 
	    	} 
	    	} 
	    	return flag; 
	    } 
	    /**
	     * main方法测试
	     * @param s
	     * @throws IOException
	     */
	    public static void main(String[] s) throws IOException {
	    	TxtRwriteFile a=new TxtRwriteFile("f:/javaText.txt");
	    	a.openTxtFile("f:/javaText.txt");
	    	//TxtRwriteFile.readTxtFile();
	    	//TxtRwriteFile.writeTxtFile("20080808:12:13");
	    	//TxtRwriteFile.writeTxtFile("20080808:12:13ddddddddkkk");
	    	a.writeTxtToend("@2222222www");
	    	a.readTxtFile();
//	        ReadWriteFile.replaceTxtByStr("ken", "zhang");
	    }
}
