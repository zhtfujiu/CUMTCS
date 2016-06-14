package com.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTools {

	/** 
     * 把InputStream中的内容读出来，放到一个byte[]中返回 
     */ 
	public static byte[] getBytes(InputStream is){
		byte[] data=new byte[1024];
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		int len=0;
		try {
			while((len=is.read(data))!=-1){
				baos.write(data, 0, len);
			}
			baos.flush();
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return baos.toByteArray();
		
	}
}
