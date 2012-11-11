package com.chenjun.fund.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ModelFactory {
	
	/**
	 * ��Json File�ļ�ת��Ϊ�ַ�����ʽ
	 * @param jsonFile Json�ļ�
	 * @return Json�ַ���
	 */
	private static String fileToString(File jsonFile){
		String jsonString = new String();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(jsonFile);
			byte[] buffer =  new byte[1024];
			int i;
			while((i = fis.read(buffer)) != -1){
				jsonString += new String(buffer, 0, i, "gb2312");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(fis != null){
					fis.close();
					fis = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonString;
	}
	
	/**
	 * ��Json�ļ�·��ת��ΪJson�ַ���
	 * @param jsonFilePath Json�ļ�·��
	 * @return Json�ַ���
	 */
	private static String filePathToString(String jsonFilePath){
		File jsonFile = new File(jsonFilePath);
		return fileToString(jsonFile);
	}
	
	/**
	 * ��Json�ַ����л�õ�������ֵ����
	 * @param jsonString Json�ַ���
	 * @return Jjjz����
	 */
	public static Jjjz getJjjz(String jsonString){
		Gson gson = new Gson();
		return gson.fromJson(jsonString, Jjjz.class);
	}
	
	/**
	 * ��Json�ļ���õ�������ֵ����
	 * @param jsonFile Json�ļ�
	 * @return Jjjz����
	 */
	public static Jjjz getJjjz(File jsonFile){
		String jsonString = fileToString(jsonFile);
		return getJjjz(jsonString);
	}
	
	/**
	 * ��Json�ַ���ת��Ϊ�������ֵ����
	 * @param jsonString Json�ַ���
	 * @return LinkedList<Jjjz>����
	 */
	public static LinkedList<Jjjz> getJjjzs(String jsonString){
		Gson gson = new Gson();
		Type type = new TypeToken<LinkedList<Jjjz>>() {}.getType();
		LinkedList<Jjjz> jjjzs = gson.fromJson(jsonString, type);
		return jjjzs;
	}
	
	/**
	 * ��Json�ļ�ת��Ϊ�������ֵ����
	 * @param jsonFile Json�ļ�
	 * @return LinkedList<Jjjz>����
	 */
	public static LinkedList<Jjjz> getJjjzs(File jsonFile){
		String jsonString = fileToString(jsonFile);
		//System.out.println(json);
		return getJjjzs(jsonString);
	}
}
