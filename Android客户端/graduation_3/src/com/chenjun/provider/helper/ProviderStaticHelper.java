package com.chenjun.provider.helper;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.chenjun.fund.model.Jjjz;
import com.chenjun.provider.metadata.FundInfoProviderMetaData;

/**
 * �����ΪProvider�ṩһЩ��ѯ��д��ĺ���
 * Provider�ǲ������ػ������ݿ��һ���м��������ͨ������ѯ���޸��ڱ��ػ������ݿ����Ϣ
 * ���ѯ�����뱾�ػ������ݿ��л�����ʷ��ֵ��Ϣ
 * @author zet
 *
 */
public class ProviderStaticHelper {
	private static final String ORDER_BY = "rq asc";
	
	/**
	 * ���ݻ���Ĵ���ӱ��ػ������ݿ����ȡ�ɵĻ���ֵ��Ϣ
	 * @param activity ��ʾ����ֵ��ʷ���Ƶ�Activity�����漰Activity��UI��ֻ�õ�Android SDKΪ�������ṩ��managedQuery����������ȥ��Provider������Ϣ��
	 * @param dm �������
	 * @return
	 */
	public static List<Jjjz> getJjjsListFromDB(Activity activity, String dm){
		Uri dmUri = Uri.parse("content://" + FundInfoProviderMetaData.AUTHORITY + "/" + FundInfoProviderMetaData.JJJZ_TABLE_NAME + "/" + dm);
		Cursor cursor = activity.managedQuery(dmUri, null, null, null, ORDER_BY);
		List<Jjjz> list = new LinkedList<Jjjz>();
		
		int dmIndex = cursor.getColumnIndex("dm");
		int jzIndex = cursor.getColumnIndex("jz");
		int rqIndex = cursor.getColumnIndex("rq");
		int ljjzIndex = cursor.getColumnIndex("ljjz");
		int fqjzIndex = cursor.getColumnIndex("fqjz");
		
		if(cursor.moveToFirst() == false){
			return list;
		}
		
		while(cursor.moveToNext()){
			Jjjz jjjz = new Jjjz();
			jjjz.setDm(cursor.getString(dmIndex));
			jjjz.setRq(cursor.getString(rqIndex));
			jjjz.setJz(cursor.getString(jzIndex));
			jjjz.setLjjz(cursor.getString(ljjzIndex));
			jjjz.setFqjz(cursor.getString(fqjzIndex));
			list.add(jjjz);
		}
		
		return list;
	}
	
	/**
	 * ���µĻ���ֵ��Ϣд�뱾�ػ������ݿ⡣
	 * @param context ��ʾ����ֵ��ʷ���Ƶ������ģ�Activity����ֻ�����ṩ��һ��getContentResolver()������������Providerд������
	 * @param jjjzList �µĻ���ֵ��Ϣ��List<Jjjz>��ʽ��
	 * @return
	 */
	public static int writeJjjzListToDB(Context context, List<Jjjz> jjjzList){
		Uri uri = FundInfoProviderMetaData.JjjzTableMetaTable.CONTENT_URI;
		ContentResolver cr = context.getContentResolver();
		
		int insertCount = 0;
		
		for(Jjjz jjjz: jjjzList){
			ContentValues value = new ContentValues();
			value.put(FundInfoProviderMetaData.JjjzTableMetaTable.JJJZ_DM, jjjz.getDm());
			value.put(FundInfoProviderMetaData.JjjzTableMetaTable.JJJZ_RQ, jjjz.getRq());
			value.put(FundInfoProviderMetaData.JjjzTableMetaTable.JJJZ_JZ, jjjz.getJz());
			value.put(FundInfoProviderMetaData.JjjzTableMetaTable.JJJZ_LJJZ, jjjz.getLjjz());
			value.put(FundInfoProviderMetaData.JjjzTableMetaTable.JJJZ_FQJZ, jjjz.getFqjz());
			cr.insert(uri, value);
			insertCount ++;
		}
		
		System.out.println("write finish!");
		return insertCount;
	}
}
