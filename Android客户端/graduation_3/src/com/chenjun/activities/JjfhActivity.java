package com.chenjun.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.SimpleAdapter;

import com.chenjun.R;
import com.chenjun.fund.model.Jjfh;
import com.chenjun.fund.model.ModelFactory;
import com.chenjun.network.HttpDownloader;
import com.chenjun.network.NetWorkConfig;
import com.chenjun.utils.StringUtils;
import com.chenjun.utils.ThreadPoolUtils;

public class JjfhActivity extends ListActivity {

	private static final String[] itemNames = new String[] { "count", "ggrqKey",
			"ggrqValue", "dwfhKey", "dwfhValue", "gqdjrKey", "gqdjrValue",
			"cxrKey", "cxrValue", "pxrKey", "pxrValue", "ztrKey", "ztrValue",
			"jjfejsKey", "jjfejsValue" };

	private static final int[] itemIds = new int[] { R.id.Jjfh_count,
			R.id.Jjfh_ggrq_Key, R.id.Jjfh_ggrq_Value, R.id.Jjfh_dwfh_Key,
			R.id.Jjfh_dwfh_Value, R.id.Jjfh_gqdjr_Key, R.id.Jjfh_gqdjr_Value,
			R.id.Jjfh_cxr_Key, R.id.Jjfh_cxr_Value, R.id.Jjfh_pxr_Key,
			R.id.Jjfh_pxr_Value, R.id.Jjfh_ztr_Key, R.id.Jjfh_ztr_Value,
			R.id.Jjfh_jjfejs_Key, R.id.Jjfh_jjfejs_Value };

	private static final int JJFH_RECEIVE_REFLESH = 1;

	private List<Jjfh> jjfhs;
	//private Thread downloadThread; // ���ػ���ֺ���߳�
	private Handler refleshHandler = new RefleshHandler(); // ˢ��Handler�������û���ť��������ݵ���ʱ�����Ӧ�¼�

	private String dm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.jjfhactivity);
		super.onCreate(savedInstanceState);
		
		
		dm = getIntent().getStringExtra("dm");
		
		if (jjfhs == null) {
			ThreadPoolUtils.execute(new DownloadRunnable(dm));
		}

	}

	/**
	 * ���������߳̾��庯��
	 * 
	 * @author zet
	 * 
	 */
	private class DownloadRunnable implements Runnable {
		private String dm;
		
		public DownloadRunnable(String dm){
			this.dm = dm;
		}
		
		@Override
		public void run() {
			System.out.println("��ʼ��������");

			String json;
			try {
				json = HttpDownloader
						.downloadCompressedByte(NetWorkConfig.getJjfhUrl(dm));
				System.out.println("��������ɹ���");

				// System.out.println(json);

				jjfhs = ModelFactory.getJjfhs(json);

				System.out.println("����ת���ɹ���");

				// ���Ͷ�Ӧ�¼���Handler
				refleshHandler.sendEmptyMessage(JJFH_RECEIVE_REFLESH);
			} catch (Exception e) {
				// ����ʧ�ܴ���Ӧ���½�һ����ʾ���û�����Ϣ��ѯ���Ƿ�����
				System.out.println("��������ʧ�ܣ�");
			}
		}
	}

	/**
	 * ˢ��Handler
	 * 
	 * @author zet
	 * 
	 */
	private class RefleshHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (JJFH_RECEIVE_REFLESH == msg.what) {
				refleshListView();
			}
		}
	}

	private void refleshListView() {
		setListAdapter(new SimpleAdapter(this, getData(),
				R.layout.jjfh_list_element, itemNames, itemIds));
		// this.getListView().postInvalidate();
	}

	private List<Map<String, Object>> getData() {
		if (jjfhs == null) {
			return null;
		}

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map;
		
		if (jjfhs.size() == 0) {
			map = new HashMap<String, Object>();
			map.put("count", getString(R.string.JjfhActivity_noFh_HelpInfo_TextView));
			list.add(map);
		}
		
		for (int i = 0; i < jjfhs.size(); i++) {
			map = new HashMap<String, Object>();
			Jjfh jjfh = jjfhs.get(i);

			map.put("count", "��" + (i + 1) + "�ηֺ�");

			map.put("ggrqKey",
					getString(R.string.JjfhActivity_ggrq_Key_TextView));
			map.put("ggrqValue",
					StringUtils.databaseStr2TextViewStr(jjfh.getGgrq()));

			map.put("dwfhKey",
					getString(R.string.JjfhActivity_dwfh_Key_TextView));
			map.put("dwfhValue",
					StringUtils.databaseStr2TextViewStr(jjfh.getDwfh()));

			map.put("gqdjrKey",
					getString(R.string.JjfhActivity_gqdjr_Key_TextView));
			map.put("gqdjrValue",
					StringUtils.databaseStr2TextViewStr(jjfh.getGqdjr()));

			map.put("cxrKey", getString(R.string.JjfhActivity_cxr_Key_TextView));
			map.put("cxrValue",
					StringUtils.databaseStr2TextViewStr(jjfh.getCxr()));

			map.put("pxrKey", getString(R.string.JjfhActivity_pxr_Key_TextView));
			map.put("pxrValue",
					StringUtils.databaseStr2TextViewStr(jjfh.getPxr()));

			map.put("ztrKey", getString(R.string.JjfhActivity_ztr_Key_TextView));
			map.put("ztrValue",
					StringUtils.databaseStr2TextViewStr(jjfh.getZtr()));

			map.put("jjfejsKey",
					getString(R.string.JjfhActivity_jjfejs_Key_TextView));
			map.put("jjfejsValue",
					StringUtils.databaseStr2TextViewStr(jjfh.getJjfejs()));

			list.add(map);
		}

		return list;
	}
}
