package com.chenjun.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.SimpleAdapter;

import com.chenjun.R;
import com.chenjun.fund.model.Jjgs;
import com.chenjun.fund.model.ModelFactory;
import com.chenjun.network.HttpDownloader;
import com.chenjun.network.NetWorkConfig;
import com.chenjun.utils.StringUtils;
import com.chenjun.utils.ThreadPoolUtils;

public class JjgsActivity extends ListActivity {
	private static final String[] itemNames = new String[] { "key", "value" };

	private static final int[] itemIds = new int[] { R.id.element_Key,
			R.id.element_Value };

	private static final int JJGS_RECEIVE_REFLESH = 1;

	private String dm;
	
	private Jjgs jjgs;
//	private Thread downloadThread; // ���ػ���ֺ���߳�
	private Handler refleshHandler = new RefleshHandler(); // ˢ��Handler�������û���ť��������ݵ���ʱ�����Ӧ�¼�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.jjgsactivity);
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		dm = intent.getStringExtra("dm");
		
		if (jjgs == null) {
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
//				json = HttpDownloader
//						.download("http://122.235.44.158:8081/AndroidFundServer/jjgs.json?dm=000021");
				json = HttpDownloader
						.downloadCompressedByte(NetWorkConfig.getJjgsUrl(dm));
				System.out.println("��������ɹ���");

				// System.out.println(json);

				jjgs = ModelFactory.getJjgs(json);

				System.out.println("����ת���ɹ���");

				// ���Ͷ�Ӧ�¼���Handler
				refleshHandler.sendEmptyMessage(JJGS_RECEIVE_REFLESH);
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
			if (JJGS_RECEIVE_REFLESH == msg.what) {
				refleshListView();
			}
		}
	}

	/**
	 * ˢ��ListView����
	 */
	private void refleshListView() {
		setListAdapter(new SimpleAdapter(this, getData(),
				R.layout.jjgs_list_element, itemNames, itemIds));
		// this.getListView().postInvalidate();
	}

	private List<Map<String, Object>> getData() {
		if (jjgs == null) {
			return null;
		}

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map;
		String value;

		// ��������
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_jgmc_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getJgmc()));
		map.put("value", value);
		list.add(map);

		// ����������
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_fddbr_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getFddbr()));
		map.put("value", value);
		list.add(map);

		// ��������
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_slrq_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getSlrq()));
		map.put("value", value);
		list.add(map);

		// ע���ʱ�
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_zczb_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getZczb()));
		map.put("value", value);
		list.add(map);

		// ע���ַ
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_zcdz_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getZcdz()));
		map.put("value", value);
		list.add(map);

		// �ܾ���
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_zjl_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getZjl()));
		map.put("value", value);
		list.add(map);

		// ��ϵ�绰
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_lxdh_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getLxdh()));
		map.put("value", value);
		list.add(map);

		// ��ϵ����
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_lxcz_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getLxcz()));
		map.put("value", value);
		list.add(map);

		// ��ϵ��ַ
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_lxdz_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getLxdz()));
		map.put("value", value);
		list.add(map);

		// ��������
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_yzbm_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getYzbm()));
		map.put("value", value);
		list.add(map);

		// ��������
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_dzyx_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getDzyx()));
		map.put("value", value);
		list.add(map);

		// ��˾��ַ
		map = new HashMap<String, Object>();
		map.put("key", getString(R.string.JjgsActivity_gswz_Key_TextView));
		value = StringUtils.addT(StringUtils.databaseStr2TextViewStr(jjgs
				.getGswz()));
		map.put("value", value);
		list.add(map);

		return list;
	}
}
