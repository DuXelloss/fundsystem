package com.chenjun.network;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.Environment;

import com.chenjun.utils.compress.Compress;
import com.chenjun.xmlpull.XmlPullReader;

public class HttpDownloader {
	private static final int BUFFER_LENGTH = 400;
	
	public static final String CACHE_PATH = "androidFund/cache";
	public static final String REPORT_NAME = "Report.xml";
	
	public static final String EXCHANGE_ENCODING = "gbk";
	/**
	 * �����ṩ��Url��HTTP�����������Ӧ����ҳ
	 * 
	 * @param urlStr
	 *            ����url
	 * @return
	 */
	public static String download(String urlStr) throws Exception {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		URL url = null;

		// ����һ��URL����
		url = new URL(urlStr);
		// ����һ��Http����
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

		// ʹ��IO����ȡ����
		buffer = new BufferedReader(new InputStreamReader(
				urlConn.getInputStream()));
		while ((line = buffer.readLine()) != null) {
			sb.append(line);
		}

		try {
			buffer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	/**
	 * �����ṩ��Url��HTTP������������GZIPѹ�����ֽ�����
	 * 
	 * @param urlStr
	 *            ����url
	 * @return
	 */
	public static String downloadCompressedByte(String urlStr) throws Exception {
//		InputStream is = null;
//		URL url = null;
//
//		// ����һ��URL����
//		url = new URL(urlStr);
//		// ����һ��Http����
//		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
//
//		// ʹ��IO����ȡ����
//		is = urlConn.getInputStream();
//		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//		byte[] buffer = new byte[BUFFER_LENGTH];
//		int length = 0;
//
//		while ((length = is.read(buffer)) != -1) {
//			byteBuffer.write(buffer, 0, length);
//		}
//
//		byteBuffer.flush();
//		byteBuffer.close();
		
		HttpGet httpGet = new HttpGet(urlStr);
        
        //Ϊ���HttpGet����һЩ�ض������ԣ������������HttpClient
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 60000);
        httpGet.setParams(params);
        
        HttpResponse httpResponse = HttpClientUtils.getHttpClient().execute(httpGet);;
        
        byte[] receivedByte = EntityUtils.toByteArray(httpResponse.getEntity());
//		byte[] receivedByte = byteBuffer.toByteArray();

		String result = null;
		
		//�жϽ��յ����ֽ������Ƿ���ѹ������
		if (receivedByte[0] == Compress.FLAG_GBK_STRING_UNCOMPRESSED_BYTEARRAY) {
			result = new String(receivedByte, 1, receivedByte.length - 1, EXCHANGE_ENCODING);
		} 
		
		else if (receivedByte[0] == Compress.FLAG_GBK_STRING_COMPRESSED_BYTEARRAY) {

			byte[] compressedByte = new byte[receivedByte.length - 1];

			for (int i = 0; i < compressedByte.length; i++) {
				compressedByte[i] = receivedByte[i + 1];
			}
			byte[] resultByte = Compress.byteDecompress(compressedByte);
			result = new String(resultByte, EXCHANGE_ENCODING);
		}

		return result;
	}

	
	
	
	/**
	 * ����FundReport�ļ�������ɹ��򷵻�true��������sd�����ҵ��û����ļ�
	 * �������ʧ�ܣ�����false
	 * 
	 */
	public static boolean downloadReport(){
		InputStream is = null;
		OutputStream os = null;
		URL url = null;
		String date = null;

		File destPath = new File(Environment.getExternalStorageDirectory(),
				CACHE_PATH);

		File destFile = new File(destPath, REPORT_NAME);
		
		if (destFile.exists()) {
			date = XmlPullReader.parseFundReportListXml(destFile).getDate();
		}
		
		String urlStr = null;
		
		if(null != date){
			urlStr = NetWorkConfig.getReportUrl(date);
		}
		else {
			urlStr = NetWorkConfig.getReportUrl();
		}
		
//		System.out.println(urlStr);
		
		// ����url
		try {
			// ����һ��URL����
			url = new URL(urlStr);

			// ����һ��Http����
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();

			// ʹ��IO����ȡ����
			is = urlConn.getInputStream();
			
			destPath.mkdirs();
			
			byte[] buffer = new byte[400];
			int length = 0;
			ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

			while ((length = is.read(buffer)) != -1) {
				byteBuffer.write(buffer, 0, length);
			}
			
			byteBuffer.flush();
			byteBuffer.close();
			
			byte[] receivedByte =  byteBuffer.toByteArray();
			
			//�жϽ��յ����ֽ������Ƿ���ѹ������
			if (receivedByte[0] == Compress.FLAG_UTF8_STRING_COMPRESSED_BYTEARRAY) {
				if (!destFile.exists()) {
					destFile.createNewFile();
				} else {
					destFile.delete();
					destFile.createNewFile();
				}
				
				byte[] compressedByte = new byte[receivedByte.length - 1];

				for (int i = 0; i < compressedByte.length; i++) {
					compressedByte[i] = receivedByte[i + 1];
				}
				byte[] resultByte = Compress.byteDecompress(compressedByte);
				
//				System.out.println(new String(resultByte, "utf-8"));
				
				os = new FileOutputStream(destFile);
				
				os.write(resultByte);
				os.flush();
				
				return true;
			} else if (receivedByte[0] == Compress.FLAG_NO_UPDATE_INFO) {
				System.out.println("no update");
				return true;
			}
		} 
		 catch (Exception e) {
			
		}finally{
			try {
				if(is != null){
					is.close();
				}
				if(os != null){
					os.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

}