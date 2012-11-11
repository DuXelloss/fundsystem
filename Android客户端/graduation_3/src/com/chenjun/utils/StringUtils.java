package com.chenjun.utils;

public class StringUtils {
	
	/**
	 * ͳһ��ֵ������С�����4λ
	 * @param jz
	 * @return
	 */
	public static String jzFormat(String jz){
		if(jz == null){
			return null;
		}
		
		int pointInt = jz.indexOf(".");
		
		return jz.substring(0, pointInt + 5);
	}
	
	/**
	 * ͳһ�Ƿ�������С�����3λ
	 * @param jz
	 * @return
	 */
	public static String increaseFormat(String jz){
		if(jz == null){
			return null;
		}
		
		int pointInt = jz.indexOf(".");
		
		return jz.substring(0, pointInt + 5);
	}
	
	/**
	 * ��Դ�ַ�����䵽ָ���ĳ��Ȳ����ء�
	 * @param str	Դ��Ŀ���ַ���
	 * @param length		Ŀ���ַ����ĳ���
	 * @param addChar	����ַ�
	 * @return String���ͣ�Ŀ���ַ���
	 */
	public static String stringLenthFormat(String str, int length, char addChar){
		if(str.length() > length){
			str = str.substring(0, length);
		}
		else if(str.length() < length){
			int addCount = length - str.length();
			for(int i = 0; i < addCount; i ++){
				str = str + addChar;
			}
		}
		return str;
	}
	
	/**
	 * ��Դ�ַ��������ÿ���ַ�����䵽��Ӧ�ĳ���
	 * @param strArray
	 * @param length
	 * @param addChar
	 * @return
	 */
	public static String[] stringArrayLengthFormat(String[] strArray, int length, char addChar){
		for(int i = 0; i < strArray.length; i++){
			strArray[i] = stringLenthFormat(strArray[i], length, addChar);
		}
		return strArray;
	}
	
	public static boolean isDateString(String dateStr){
		if(dateStr == null){
			return false;
		}
		if(dateStr.length() != 8){
			return false;
		}
		String year = dateStr.substring(0, 4);
		try{
			int yearInt = Integer.parseInt(year);
			if(yearInt > 3000 || yearInt < 1900){
				return false;
			}
		}catch(Exception e){
			return false;
		}
		String month = dateStr.substring(4, 6);
		try{
			int monthInt = Integer.parseInt(month);
			if(monthInt > 13 || monthInt < 1){
				return false;
			}
		}catch(Exception e){
			return false;
		}
		String day = dateStr.substring(6, 8);
		try{
			int dayInt = Integer.parseInt(day);
			if(dayInt > 31 || dayInt < 1){
				return false;
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public static boolean isDateRangeString(String dateRangeStr){
		if(dateRangeStr == null){
			return false;
		}
		if(dateRangeStr.length() != 17){
			return false;
		}
		String startYear = dateRangeStr.substring(0, 8);
		String endYear = dateRangeStr.substring(9, 17);
		if(!isDateString(startYear)){
			return false;
		}
		if(!isDateString(endYear)){
			return false;
		}
		return true;
	}
	
	public static String dateString2zhDateString(String originalStr){
		if(originalStr == null){
			return originalStr;
		}
		if(originalStr.length() != 8){
			return originalStr;
		}
		String year = originalStr.substring(0, 4);
		String month = originalStr.substring(4, 6);
		String day = originalStr.substring(6, 8);
		return year + "��" + month + "��" + day + "��";
	}
	
	public static String dateRangeString2zhDateRangeString(String originalStr){
		if(originalStr == null){
			return originalStr;
		}
		if(originalStr.length() != 17){
			return originalStr;
		}
		String startYear = originalStr.substring(0, 8);
		String endYear = originalStr.substring(9, 17);
		return dateString2zhDateString(startYear) + "-" + dateString2zhDateString(endYear);
	}
	/**
	 * Ϊ�����ַ���������������
	 */
	public static String addT(String str){
		return "\t\t" + str;
	}
	/**
	 * ��20120102���������ڸ�ʽ���ַ���ת��Ϊ2012/01/02
	 * @param originalStr
	 * @return
	 */
	public static String dateStringChange(String originalStr){
		String year = originalStr.substring(0, 4);
		String month = originalStr.substring(4, 6);
		String day = originalStr.substring(6, 8);
		return year + "/" + month + "/" + day;
	}
	
	/**
	 * ��20120102���������ڸ�ʽ���ַ�������ת��Ϊ2012/01/02��ʽ���ַ�������
	 * @param originalStr
	 * @return
	 */
	public static String[] dateStringArrayChange(String[] originalStrArray){
		for(int i = 0; i < originalStrArray.length; i ++){
			originalStrArray[i] = dateStringChange(originalStrArray[i]);
		}
		return originalStrArray;
	}
	
	public static String databaseStr2TextViewStr(String str){
		if(str == null){
			return "����Ϣ";
		}
		
		if(str.equals("null")){
			str = "����Ϣ";
		}
		else if(StringUtils.isDateString(str)){
			str = StringUtils.dateString2zhDateString(str);
		}
		else if(StringUtils.isDateRangeString(str)){
			str = StringUtils.dateRangeString2zhDateRangeString(str);
		}
		return str;
	}
}
