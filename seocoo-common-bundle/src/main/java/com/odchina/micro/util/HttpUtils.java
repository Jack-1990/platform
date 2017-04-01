package com.odchina.micro.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
/**
 * 
 * @author <a href="mailto:peng.wu@foxmail.com">wu.peng</a>
 * 
 */
public class HttpUtils {

	private static final Log log = LogFactory.getLog(HttpUtils.class);

	public static byte[] post(String url, Map<String, String> params) {
		HttpClient hc = new HttpClient();
		PostMethod method = null;
		InputStream in = null;
		try {
			method = new PostMethod(url);
			List<NameValuePair> _params = new ArrayList<NameValuePair>();
			if (params != null) {
				for (String key : params.keySet()) {
					_params.add(new NameValuePair(key, params.get(key)));
				}
			}
			method.setRequestBody(_params.toArray(new NameValuePair[] {}));
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			method.getParams().setParameter(HttpMethodParams.HTTP_URI_CHARSET,
					"utf-8");
			int code = hc.executeMethod(method);
			if (code == HttpStatus.SC_OK) {
				in = method.getResponseBodyAsStream();
				return com.odchina.micro.util.IOUtils.readByteArray(in);
			}
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			if (method != null) {
				method.releaseConnection();
				method = null;
			}
			IOUtils.closeQuietly(in);
		}
		return new byte[] {};
	}

	public static Map<String, String> getParams(ServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		if (request == null) {
			return params;
		}
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			params.put(paramName, paramValue);
		}
		return params;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param json
	 *            请求参数，请求参数是Json格式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String json)
	{
		CloseableHttpClient client = HttpClients.createDefault();
		try
		{
			// 1.1 参数配置1：创建代理，参数分别是(代理IP, 代理端口, 类型)非必要
			String proxyIp = "192.168.1.1";
			int proxyPort = 3281;
			HttpHost proxy = new HttpHost(proxyIp, proxyPort, "http");
			// 1.2 参数配置2：创建配置参数，访问接口时代理可以不用设置
			RequestConfig config = RequestConfig.custom().setProxy(proxy).setSocketTimeout(2000).setConnectTimeout(2000).setConnectionRequestTimeout(2000).setStaleConnectionCheckEnabled(true).build();
			// 1.3 参数配置3：编码格式
			String charset = "UTF-8";
			// 2. 获取请求目标，创建请求对象和响应对象
			URI uri = null;
			try
			{
				uri = new URI(url);
			} catch (URISyntaxException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int port = uri.getPort();
			if (port == -1)
			{
				port = 80;// 协议默认端口
			}
			// 2.1 获取请求目标
			HttpHost target = new HttpHost(uri.getHost(), port, "http");
			// 2.2 创建请求对象
			HttpPost request = new HttpPost(uri);
			// 2.3 创建响应对象
			CloseableHttpResponse response = null;
			try
			{
				// 3. 封装请求参数，设置配置信息
				StringEntity se = new StringEntity(json, charset);
				request.setEntity(se);
				se.setContentType("application/json");
				// request.setConfig(config);
				// 4. 发送post请求
				response = client.execute(target, request);
				// 5. 读取响应信息
				BufferedReader reader = null;
				try
				{
					reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
					StringBuilder result = new StringBuilder();
					String message = null;
					while ((message = reader.readLine()) != null)
					{
						result.append(message).append(System.getProperty("line.separator"));
					}
					String resultStr = result.toString();
					resultStr = resultStr.trim();// 去掉前后空格
					// 移除制表位换行符等，非必要
					resultStr = resultStr.replace("\n", "");
					resultStr = resultStr.replace("\r", "");
					resultStr = resultStr.replace("\t", "");
					return resultStr;
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (IllegalStateException e)
				{
					e.printStackTrace();
				} catch (IOException e)
				{
					e.printStackTrace();
				} finally
				{
					try
					{
						if (reader != null)
						{
							reader.close();
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			} catch (ClientProtocolException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			} finally
			{
				if (response != null)
				{
					try
					{
						response.close();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		} finally
		{
			// 释放资源
			try
			{
				client.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
