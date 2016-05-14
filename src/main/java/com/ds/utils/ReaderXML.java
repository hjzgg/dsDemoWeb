package com.ds.utils;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReaderXML {
	
	public String read(String path){
		
		String str=null;
		
		str=reader(path);
		
		return str;
	}
	
	private String reader(String path){
		
		SAXReader reader=new SAXReader();
		String str=null;
		
		try {
			
			Document d=reader.read(new File(path));
			
			Element e=d.getRootElement();
			
			Element htmle=e.element("html");
			
			str=htmle.asXML();
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return str;
		
	}

}