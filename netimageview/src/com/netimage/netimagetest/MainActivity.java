package com.netimage.netimagetest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Handler handler;
	private ImageView img1;
	private EditText ed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		img1 = (ImageView) findViewById(R.id.img1);
		ed = (EditText) findViewById(R.id.ed1);
		handler = new Myhandler();
	}

	public void GetImage(View view) {
		
		Thread  t = new TestThread();
		t.start();
		}
	

	class TestThread extends Thread {

		@Override
		public void run() {
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bitmap bf = null;
			String path = ed.getText().toString().trim();
			if (!TextUtils.isEmpty(path)) {
				URL url;
				try {
					url = new URL(path);
					HttpURLConnection uc = (HttpURLConnection) url
							.openConnection();
					uc.setRequestMethod("GET");
					uc.setConnectTimeout(3000);
					// uc.setReadTimeout(3000);
					uc.setDoInput(true);
					int code = uc.getResponseCode();
					if (code == 200) {
						
						InputStream is = uc.getInputStream();
						bf = BitmapFactory.decodeStream(is);
						Message msg = handler.obtainMessage();
						msg.obj = bf ;
						handler.sendMessage(msg);
					} else {
						Toast.makeText(MainActivity.this, "ªÒ»°Õº∆¨ ß∞‹", 0)
								.show();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(MainActivity.this, "ªÒ»°Õº∆¨URL ß∞‹", 0)
							.show();
				}
			} else {
				Toast.makeText(MainActivity.this, "ªÒ»°Õº∆¨URL ß∞‹", 0)
						.show();
			}
		}
	}
	
	class Myhandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bitmap bf = (Bitmap)msg.obj;
			img1.setImageBitmap(bf);
		}
		
	}

}
