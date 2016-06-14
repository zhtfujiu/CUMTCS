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
    private static final int TIME_OUT = 10 * 10000000; // 超时时间  
    private static final String BianMa = "utf-8"; // 设置编码  
    public static final String SUCCESS = "1";  
    public static final String FAILURE = "0";  
	
	public HttpThread_UpLoadPic(String url,File file) {
		this.url=url;
		this.file=file;
	}
	
	public String UpLoadPic() {
		try {
			String boundary=UUID.randomUUID().toString(); // 边界标识 随机生成 ;
			String prefix="--";
			String end="\r\n";
			String CONTENT_TYPE = "multipart/form-data"; // 内容类型
			//
			URL httpUrl=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");//设置请求头，POST方式发生数据
			conn.setDoInput(true); // 允许输入流  
            conn.setDoOutput(true); // 允许输出流  
            conn.setUseCaches(false); // 不允许使用缓存  
            conn.setRequestMethod("POST"); // 请求方式  
            conn.setRequestProperty("Charset", BianMa); // 设置编码  
            conn.setRequestProperty("connection", "keep-alive");  
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="  
                    + boundary);
			//
            if(file!=null){
            	/** 
                 * 当文件不为空，把文件包装并且上传 
                 */  
                OutputStream outputSteam = conn.getOutputStream();  
  
                DataOutputStream dos = new DataOutputStream(outputSteam);  
                StringBuffer sb = new StringBuffer();  
                sb.append(prefix);  
                sb.append(boundary);  
                sb.append(end);
                /** 
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 
                 * filename是文件的名字，包含后缀名的 比如:abc.png 
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
                 * 获取响应码 200=成功 当响应成功，获取响应的流 
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
