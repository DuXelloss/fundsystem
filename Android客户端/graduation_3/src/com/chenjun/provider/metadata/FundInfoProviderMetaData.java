package com.chenjun.provider.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class FundInfoProviderMetaData {
	private FundInfoProviderMetaData() {
	}
	
	//authority
	public static final String AUTHORITY = "com.chenjun.fund.provider.FundInfo"; 
	
	//database name
	public static final String DATABASE_NAME = "jj.db";
	
	//database version
	public static final int DATABASE_VERSION = 1;
	
	//����
	public static final String JJJZ_TABLE_NAME = "jjjz";
	public static final String JJGK_TABLE_NAME = "jjgk";
	
	//����jjjz����ڲ���
	public static class JjjzTableMetaTable implements BaseColumns{
		private JjjzTableMetaTable(){
			
		}
		
		public static final String TABLE_NAME = "jjjz";
		
		//Jjjz��Uri
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + JJJZ_TABLE_NAME);
		
		//public static final String DEAFAULT_SORT_ORDER = "";
		
		//�������
		
		public static final String JJJZ_DM = "dm";
		public static final String JJJZ_RQ = "rq";
		public static final String JJJZ_JZ = "jz";
		public static final String JJJZ_LJJZ = "ljjz";
		public static final String JJJZ_FQJZ = "fqjz";
	}
}
