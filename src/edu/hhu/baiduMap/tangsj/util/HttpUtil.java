package edu.hhu.baiduMap.tangsj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	public static HttpClient httpClient = new DefaultHttpClient();
	private static final String BASEURL = "http://10.158.132.36/login/servlet/RegistServlet";
	private static final String BASE_LOGINURL="http://10.158.132.36/login/servlet/LoginServlet";
	
	public static String getBaseLoginurl() {
		return BASE_LOGINURL;
	}

	public static String getBaseurl() {
		return BASEURL;
	}


	public static String postRequest(final String url,final Map<String, String>rawParams)throws Exception{
		FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
			public String call() throws Exception{
				HttpPost post = new HttpPost(url);
				List<NameValuePair>params = new ArrayList<NameValuePair>();
				for(String key : rawParams.keySet()){
					params.add(new BasicNameValuePair(key, rawParams.get(key)));
				}
				post.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
				HttpResponse httpres = httpClient.execute(post);
				if(httpres.getStatusLine().getStatusCode()==200){
					String result = EntityUtils.toString(httpres.getEntity());
					return result;
				}
				return null;
			}
		});	
		new Thread(task).start();
		return task.get();
		
	}
}
