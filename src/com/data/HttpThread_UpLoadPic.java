package com.data;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class HttpThread_UpLoadPic{

	private String url;
	private File file;
	private static final String TAG = "uploadFile";  
    private static final int TIME_OUT = 10 * 10000000; // ��ʱʱ��  
    private static final String BianMa = "utf-8"; // ���ñ���  
    public static final String SUCCESS = "1";  
    public static final String FAILURE = "0";  
	
	public HttpThread_UpLoadPic(String url,File file) {
		this.url=url;
		this.file=file;
	}
	
	public String UpLoadPic() {
		try {
			String boundary=UUID.randomUUID().toString(); // �߽��ʶ ������� ;
			String prefix="--";
			String end="\r\n";
			String CONTENT_TYPE = "multipart/form-data"; // ��������
			//
			URL httpUrl=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");//��������ͷ��POST��ʽ��������
			conn.setDoInput(true); // ����������  
            conn.setDoOutput(true); // ���������  
            conn.setUseCaches(false); // ������ʹ�û���  
            conn.setRequestMethod("POST"); // ����ʽ  
            conn.setRequestProperty("Charset", BianMa); // ���ñ���  
            conn.setRequestProperty("connection", "keep-alive");  
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="  
                    + boundary);
			//
            if(file!=null){
            	/** 
                 * ���ļ���Ϊ�գ����ļ���װ�����ϴ� 
                 */  
                OutputStream outputSteam = conn.getOutputStream();  
  
                DataOutputStream dos = new DataOutputStream(outputSteam);  
                StringBuffer sb = new StringBuffer();  
                sb.append(prefix);  
                sb.append(boundary);  
                sb.append(end);
                /** 
                 * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key �ſ��Եõ���Ӧ���ļ� 
                 * filename���ļ������֣�������׺���� ����:abc.png 
                 */    
                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""  
                        + file.getName() + "\"" + end);  
                sb.append("Content-Type: application/octet-stream; charset="  
                        + BianMa + end);  
                sb.append(end);  
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);  
                byte[] bytes = new byte[1024];  
                int len = 0;  
                while ((len = is.read(bytes)) != -1) {  
                    dos.write(bytes, 0, len);  
                }  
                is.close();  
                dos.write(end.getBytes());  
                byte[] end_data = (prefix + boundary + prefix + end)  
                        .getBytes();  
                dos.write(end_data);  
                dos.flush();  
                /** 
                 * ��ȡ��Ӧ�� 200=�ɹ� ����Ӧ�ɹ�����ȡ��Ӧ���� 
                 */  
                int res = conn.getResponseCode();  
                if (res == 200) {  
                    return SUCCESS;  
                }
            }

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}
}
