package com.chenjun.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.chenjun.R;
import com.chenjun.fund.model.FundReport;
import com.chenjun.fund.model.FundReportList;
import com.chenjun.listviewadapter.FundBaseInfoListViewAdapter;
import com.chenjun.network.InitFundReport;
import com.chenjun.utils.ThreadPoolUtils;
import com.chenjun.view.SyncHorizontalScrollView;

public class SearchActivity extends Activity implements
		GoToFundInfoActivityGroup, OnScrollListener {
	private static final int SEARCH_CONTENT_REFLESH = 1;

	private Button searchBtn = null;
	private EditText searchKeyWordEdit = null;

	private static final int INIT_ITEM_COUNT = 30;
	private static final int ONCE_SCROLL_ADD_COUNT = 10;
	
	private ListView listView;
	private SyncHorizontalScrollView headView;
	private FundReportList fundReportList;
	
	private FundBaseInfoListViewAdapter listAdapter;
	
	private int itemCount = INIT_ITEM_COUNT;
	
	private int lastVisibleItem;
	
	private List<FundReport> resultReport;
	private Handler refleshHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.searchactivity);
		super.onCreate(savedInstanceState);
		
		fundReportList = InitFundReport.fundReportList;

		searchBtn = (Button) findViewById(R.id.SearchActivity_searchBtn);
		searchKeyWordEdit = (EditText) findViewById(R.id.SearchActivity_keyEdit);
		listView = (ListView)findViewById(R.id.SearchActivity_result_list);
		headView = (SyncHorizontalScrollView)findViewById(R.id.fundbaseinfo_list_head_scrollView);
		
		headView.setSyncView(listView);
		
		setListData();
		listView.setOnScrollListener(this);
		
		refleshHandler = new RefleshHandler();
		

		// �󶨼�����
		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyWord = searchKeyWordEdit.getText().toString();
				keyWord.trim();
				ThreadPoolUtils.execute(new SearchRunnable(keyWord));
				v.setClickable(false);
			}
		});

	}

	@Override
	public void handle(Intent intent) {
		this.startActivity(intent);
	}

	/**
	 * �����̵߳ľ��庯��
	 * 
	 * @author zet
	 * 
	 */
	private class SearchRunnable implements Runnable {
		private String keyWord;

		public SearchRunnable(String keyWord) {
			this.keyWord = keyWord;
		}

		public void run() {
			System.out.println("��ʼ��������");

			try {
				resultReport =fundReportList.search(keyWord);

				// ���Ͷ�Ӧ�¼���Handler
				refleshHandler.sendEmptyMessage(SEARCH_CONTENT_REFLESH);
			} catch (Exception e) {
				// ����ʧ�ܴ���Ӧ���½�һ����ʾ���û�����Ϣ��ѯ���Ƿ�����
				// Toast.makeText(JjjzsActivity.this, "��������ʧ�ܣ�",
				// Toast.LENGTH_LONG).show();
				System.out.println("��������ʧ�ܣ�");
			}

		}
	}
	

	/**
	 * ��ддHanlder��Ӧ���ȵĺ���
	 * 
	 * @author zet
	 * 
	 */
	private class RefleshHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// ��������
			if (msg.what == SEARCH_CONTENT_REFLESH) {
				searchBtn.setClickable(true);
				setListData();
			}
		}
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		this.lastVisibleItem = firstVisibleItem + visibleItemCount - 1;
		
		if(lastVisibleItem == (itemCount - 2)){
			listAdapter.addDate(getListMapData(itemCount, itemCount + ONCE_SCROLL_ADD_COUNT));
			itemCount += ONCE_SCROLL_ADD_COUNT;
			listAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		//�ﵽ�б�ĵײ�
		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE && lastVisibleItem == (itemCount - 1)){
			listAdapter.addDate(getListMapData(itemCount, itemCount + ONCE_SCROLL_ADD_COUNT));
			itemCount += ONCE_SCROLL_ADD_COUNT;
			listAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * �����б�����
	 */
	private void setListData() {
		if (resultReport == null) {
			return;
		}

		listAdapter = new FundBaseInfoListViewAdapter(this, getListMapData(0 , INIT_ITEM_COUNT), this, headView);
		
		listView.setAdapter(listAdapter);
	}
	
	private List<FundReport> getListMapData(int start, int end){
		
		if(start < 0 || end < 0 || start > resultReport.size()){
			return null;
		}
		
		if(end > resultReport.size()){
			end = resultReport.size();
		}
		
		List<FundReport> mData = new ArrayList<FundReport>();

		for (int i = start; i < end; i++) {
			mData.add(resultReport.get(i));
		}
		
		return mData;
	}
}
