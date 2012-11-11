package com.chenjun.fund.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ModelFactory {
	
	private static final Gson gson = new Gson();
	
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
	public static String filePathToString(String jsonFilePath){
		File jsonFile = new File(jsonFilePath);
		return fileToString(jsonFile);
	}
	
	public static LoginStatus getLoginStatus(String jsonString){
		return gson.fromJson(jsonString, LoginStatus.class);
	}
	
	public static AddSelfCheckStatus getAddSelfCheckStatus(String jsonString){
		return gson.fromJson(jsonString, AddSelfCheckStatus.class);
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
	 * ��Json�ַ���ת��Ϊ�����һ��ʱ���ڣ�����ֵ����
	 * @param jsonString Json�ַ���
	 * @return Jjjzs����
	 */
	public static Jjjzs getJjjzs(String jsonString){
		List<Jjjz> jjjzs = getJjjzList(jsonString);
		return new Jjjzs(jjjzs);
	}
	
	/**
	 * ��Json�ַ���ת��Ϊ�����һ��ʱ���ڣ�����ֵ������List<Jjjz>����ʽ
	 * @param jsonString
	 * @return
	 */
	public static List<Jjjz> getJjjzList(String jsonString){
		Gson gson = new Gson();
		Type type = new TypeToken<LinkedList<Jjjz>>() {}.getType();
		LinkedList<Jjjz> jjjzs = gson.fromJson(jsonString, type);
		return jjjzs;
	}
	
	/**
	 * ��Json�ļ�ת��Ϊ�����һ��ʱ���ڣ�����ֵ����
	 * @param jsonFile Json�ļ�
	 * @return Jjjzs����
	 */
	public static Jjjzs getJjjzs(File jsonFile){
		String jsonString = fileToString(jsonFile);
		//System.out.println(json);
		return getJjjzs(jsonString);
	}
	
	/**
	 * ��Json�ַ���ת��Ϊ����ſ�
	 * @param jsonString Json�ַ���
	 * @return Jjgk����
	 */
	public static Jjgk getJjgk(String jsonString){
		Gson gson = new Gson();
		return gson.fromJson(jsonString, Jjgk.class);
	}
	
	/**
	 * ��Json�ַ���ת��Ϊ�������ֺ����
	 * @param jsonString Json�ַ���
	 * @return List<Jjfh> Jjfh����
	 */
	public static List<Jjfh> getJjfhs(String jsonString){
		Gson gson = new Gson();
		Type type = new TypeToken<LinkedList<Jjfh>>() {}.getType();
		LinkedList<Jjfh> jjfhs = gson.fromJson(jsonString, type);
		return jjfhs;
	}
	
	/**
	 * ��Json�ַ���ת��Ϊ����˾����
	 * @param jsonString Json�ַ���
	 * @return Jjgs Jjgs����
	 */
	public static Jjgs getJjgs(String jsonString){
		Gson gson = new Gson();
		return gson.fromJson(jsonString, Jjgs.class);
	}
	
	/**
	 * ��Json�ַ���ת��Ϊ��������ֶ���
	 * @param jsonString Json�ַ���
	 * @return List<Jjfh> Jjfh����
	 */
	public static List<Jjcf> getJjcfs(String jsonString){
		Gson gson = new Gson();
		Type type = new TypeToken<LinkedList<Jjcf>>() {}.getType();
		LinkedList<Jjcf> jjcfs = gson.fromJson(jsonString, type);
		return jjcfs;
	}
	
	/**
	 * ��Json�ַ���ת��Ϊ������������Ϣ����
	 * @param jsonString Json�ַ���
	 * @return List<Jjfh> Jjfh����
	 */
	public static List<JjBaseInfo> getJjBaseInfos(String jsonString){
		Gson gson = new Gson();
		Type type = new TypeToken<LinkedList<JjBaseInfo>>() {}.getType();
		LinkedList<JjBaseInfo> jjBaseInfos = gson.fromJson(jsonString, type);
		return jjBaseInfos;
	}
}
