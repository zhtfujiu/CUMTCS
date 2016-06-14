package com.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

public class HttpImage extends Thread {

	private ImageView picture;
	private String url;
	private Handler handler;
	public HttpImage(String url,Handler handler,ImageView picture) {
		this.url=url;
		this.handler=handler;
		this.picture=picture;
	}
	@Override
	public void run() {
		URL httpUrl;
		try {
			httpUrl=new URL(url);
			HttpURLConnection conn=(HttpURLConnection)httpUrl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			//
			InputStream in=conn.getInputStream();
			final Bitmap bitmap=BitmapFactory.decodeStream(in);
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					picture.setImageBitmap(bitmap);
				}
			});
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
