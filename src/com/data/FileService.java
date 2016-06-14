package com.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class FileService {

	private Context context;
	
	public FileService(Context context) {
		this.context=context;
	}
	public boolean savePswToRom(String fileName,String userID,String password){
		try {
			FileOutputStream fos=context.openFileOutput(fileName, Context.MODE_PRIVATE);
			String result=userID+":"+password;
			fos.write(result.getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return true;
	}
	public boolean saveNameToRom(String fileName,String userID,String name){
		try {
			FileOutputStream fos=context.openFileOutput(fileName, Context.MODE_PRIVATE);
			String result=userID+":"+name;
			fos.write(result.getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return true;
	}

	public Map<String,String> getUserPsw(String fileName) throws FileNotFoundException{		
		//方法1
		File file=new File("data/data/com.cumtcs/files/"+fileName);
		FileInputStream fis=new FileInputStream(file);
		//方法2
		FileInputStream fis2=context.openFileInput(fileName);
		byte[] data=StreamTools.getBytes(fis2);
		String result=new String(data);
		String results[]=result.split(":");
		Map<String,String> map=new HashMap<>();
		map.put("userID", results[0]);
		map.put("password", results[1]);
		
		return map;		
	}

	public Map<String,String> getUserName(String fileName) throws FileNotFoundException{		
		//方法1
		File file=new File("data/data/com.cumtcs/files/"+fileName);
		FileInputStream fis=new FileInputStream(file);
		//方法2
		FileInputStream fis2=context.openFileInput(fileName);
		byte[] data=StreamTools.getBytes(fis2);
		String result=new String(data);
		String results[]=result.split(":");
		Map<String,String> map=new HashMap<>();
		map.put("userID", results[0]);
		map.put("name", results[1]);		
		return map;		
	}

}
