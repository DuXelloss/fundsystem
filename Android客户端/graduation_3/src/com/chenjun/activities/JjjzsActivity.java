package com.chenjun.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chenjun.R;
import com.chenjun.fund.model.AddSelfCheckStatus;
import com.chenjun.fund.model.Jjjz;
import com.chenjun.fund.model.Jjjzs;
import com.chenjun.fund.model.ModelFactory;
import com.chenjun.network.HttpDownloader;
import com.chenjun.network.InitFundReport;
import com.chenjun.network.NetWorkConfig;
import com.chenjun.provider.helper.ProviderStaticHelper;
import com.chenjun.utils.StringUtils;
import com.chenjun.utils.ThreadPoolUtils;
import com.chenjun.view.JjjzsChart;
import com.chenjun.view.JjjzsChart.ChartType;
import com.chenjun.view.RangeInfoViewGroup;

/**
 * ����ֵ���Ƶ�Activity������������Ϣ������ͼ��ͳ����
 * @author zet
 *
 */
public class JjjzsActivity extends Activity{
	
	private static final int JJJZ_CHART_REFLESH = 1;
	private static final int JJJZ_BASEINFO_REFLESH = 2;
	private static final int DOWNLOAD_SUCCESS = 3;
	private static final int DOWNLOAD_ERROR = 4;
	private static final int ADD_SELF_CHECK_FUND_SUCCESS = 5;
	private static final int ADD_SELF_CHECK_FUND_FAIL = 6;
	private static final int NET_WORK_ERROR = 0;
	
	private JjjzsChart jjjzsChart;								//����ͼ
	private Jjjzs jjjzs;										//����ֵ��ʷ������Ϣ
	//private Thread downloadThread;								//���ػ������Ƶ��߳�
	private Handler refleshHandler = new RefleshHandler();		//ˢ��Handler�������û���ť��������ݵ���ʱ�����Ӧ�¼�
	
	private String dm;
	private String jjjc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.jjjzsactivity);
		super.onCreate(savedInstanceState);
		jjjzsChart = (JjjzsChart)findViewById(R.id.chart);
		
		Intent intent = getIntent();
		jjjc = intent.getStringExtra("jjjc");
		dm = intent.getStringExtra("dm");
		
		refleshBaseInfo();
		
		//���������߳�
		ThreadPoolUtils.execute(new DownloadRunnable(dm));
		
		initBtn();
		bindRangeInfoViewGroup();
	}
	
	@Override
	protected void onDestroy() {
//		downloadThread.stop()
		super.onDestroy();
	}
	
	/**
	 * �����̵߳ľ��庯��
	 * �̵߳ľ���������裺
	 *    1.���ݻ���Ĵ��룬�ӱ��ػ������ݿ���ȡ�ö�Ӧ�����������оɵĻ���ֵ��Ϣ
	 *    2.ȡ�þɵĻ���ֵ��Ϣ�����һ������
	 *    3.���ݻ����������һ�����ڣ�������������µĻ���ֵ��Ϣ
	 *    4.�ϲ� �ɵľ�ֵ��Ϣ���µľ�ֵ��Ϣ����֪ͨUI�̣߳���ֵ��Ϣ�����ڴ���׼����ϣ�UI�߳̿��Կ�ʼˢ��ͼ�����Ϣ
	 *    5.���µľ�ֵ��Ϣд�뱾�ػ������ݿ�
	 * @author zet
	 *
	 */
	private class DownloadRunnable implements Runnable {
		
		private String dm;
		
		public DownloadRunnable(String dm){
			this.dm = dm;
		}
		
		public void run() {
			System.out.println("��ʼ��������");
			try {
				//���ݴ��룬��ѯ���ػ������ݿ�Ļ���ֵ��Ϣ
				List<Jjjz> jjjzList = ProviderStaticHelper.getJjjsListFromDB(JjjzsActivity.this, dm);
				
				//���ݱ��ػ�����������������ֵ�����ڣ��ٽ���Ϣ��֯��url�������������
				String url = null;
				
				if(jjjzList.size() > 0){
					String lastDate = jjjzList.get(jjjzList.size() - 1).getRq();
					url = NetWorkConfig.getJjjzUrl(dm, lastDate);
				}
				else {
					url = NetWorkConfig.getJjjzUrl(dm);
				}
				
				String json = HttpDownloader.downloadCompressedByte(url);
				
				List<Jjjz> downloadJjjzList = ModelFactory.getJjjzList(json);
				
				//�ϲ�������¾�ֵ����
				jjjzList.addAll(downloadJjjzList);
				
				jjjzs = new Jjjzs(jjjzList);
				
				jjjzsChart.setJjjzs(jjjzs);
				
				System.out.println("����ת���ɹ���");
				
				
				//������ɣ����Ͷ�Ӧ�¼���Handler��֪ͨUI�߳̿���ˢ��UI
				refleshHandler.sendEmptyMessage(JJJZ_CHART_REFLESH);
				refleshHandler.sendEmptyMessage(JJJZ_BASEINFO_REFLESH);
				refleshHandler.sendEmptyMessage(DOWNLOAD_SUCCESS);
				
				//���µľ�ֵ��Ϣд�뻺�����ݿ�
				ProviderStaticHelper.writeJjjzListToDB(JjjzsActivity.this, downloadJjjzList);
				
				
			} catch (Exception e) {
				refleshHandler.sendEmptyMessage(DOWNLOAD_ERROR);
				System.out.println("��������ʧ�ܣ�");
			}
			
		}
	}
	
	/**
	 * UIˢ�µ�Hanlder
	 * @author zet
	 *
	 */
	private class RefleshHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			//ͼ��ˢ��
			if(msg.what == JJJZ_CHART_REFLESH){
				JjjzsActivity.this.jjjzsChart.postInvalidate();
				//JjjzsActivity.this.writeToDB();
			}
			//������Ϣˢ��
			else if(msg.what == JJJZ_BASEINFO_REFLESH){
				refleshJzAndIncrease();
			}
			
			//���سɹ�
			else if(msg.what == DOWNLOAD_SUCCESS){
				Toast.makeText(JjjzsActivity.this, "��ֵ����ɹ���", Toast.LENGTH_SHORT).show();
			}
			
			//����ʧ��
			else if(msg.what == DOWNLOAD_ERROR){
				Toast.makeText(JjjzsActivity.this, "��ֵ����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 *  ��JjjzsChart��ͳ����������ʵ�ֶ�̬ˢ��
	 */
	private void bindRangeInfoViewGroup(){
		RangeInfoViewGroup rangeInfoViewGroup = new RangeInfoViewGroup();
		
		rangeInfoViewGroup.setStartDateTextView((TextView)this.findViewById(R.id.startTimeValue));
		rangeInfoViewGroup.setEndDateTextView((TextView)this.findViewById(R.id.endTimeValue));
		rangeInfoViewGroup.setStartJjTextView((TextView)this.findViewById(R.id.startJzValue));
		rangeInfoViewGroup.setEndJjTextView((TextView)this.findViewById(R.id.endJzValue));
		rangeInfoViewGroup.setRangeIncreaseTextView((TextView)this.findViewById(R.id.increaseNumValue));
		rangeInfoViewGroup.setRangeIncreasePrecentTextView((TextView)this.findViewById(R.id.increasePercentValue));
		
		this.jjjzsChart.setRangeInfoViewGroup(rangeInfoViewGroup);
	}
	
	/**
	 * ������Ϣˢ�µĺ���
	 * ��ֵ������Ϣ�����ֻҪ�������������������Activity������Ϣ��ˢ�»������Ϣ
	 */
	private void refleshJzAndIncrease(){
		Jjjz lastJjjz = jjjzs.getJjjz(jjjzs.getCount() -1);
		Jjjz preJjjz = jjjzs.getJjjz(jjjzs.getCount() - 2);
		
		if(lastJjjz == null || preJjjz == null){
			return;
		}
		
		String jz = StringUtils.stringLenthFormat(lastJjjz.getJz(), 6, '0');
		String date = StringUtils.dateStringChange(lastJjjz.getRq());
		
		TextView jzTextView = (TextView)this.findViewById(R.id.JjjzsActivity_jjjzTextView);
		jzTextView.setText(jz);
		
		TextView dateTextView = (TextView)this.findViewById(R.id.JjjzsActivity_jjjzDateTextView);
		dateTextView.setText(date);

		double jzIncrease = Double.parseDouble(lastJjjz.getFqjz()) - Double.parseDouble(preJjjz.getFqjz());
		
		TextView jzIncreaseTextView = (TextView)this.findViewById(R.id.JjjzsActivity_jzIncreaseTextView);
		if(jzIncrease < 0){		
			String jzIncreaseStr = StringUtils.stringLenthFormat(String.valueOf(jzIncrease), 7, '0');
			jzIncreaseTextView.setTextColor(Color.GREEN);
			jzIncreaseTextView.setText(jzIncreaseStr);
		}
		else{
			String jzIncreaseStr = StringUtils.stringLenthFormat(String.valueOf(jzIncrease), 6, '0');
			jzIncreaseTextView.setTextColor(Color.RED);
			jzIncreaseTextView.setText(jzIncreaseStr);
		}
	}
	
	/**
	 * ��ʼ����ʾ���������İ�ť
	 */
	private void initBtn(){
		 ImageButton tenDayBtn = (ImageButton) findViewById(R.id.tenDayBtn);
		 tenDayBtn.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	btnClickFunction(ChartType.TEN_DAY);
	            }
	        });
		 
		 ImageButton monthBtn = (ImageButton) findViewById(R.id.monthBtn);
		 monthBtn.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	btnClickFunction(ChartType.MONTH);
	            }
	        });
		 
		 ImageButton quarterBtn = (ImageButton) findViewById(R.id.quarterBtn);
		 quarterBtn.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	btnClickFunction(ChartType.THREE_MONTH);
	            }
	        });
		 
		 ImageButton halfYearBtn = (ImageButton) findViewById(R.id.halfYearBtn);
		 halfYearBtn.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	btnClickFunction(ChartType.HALF_YEAR);
	            }
	        });
		 
		 ImageButton oneYearBtn = (ImageButton) findViewById(R.id.yearBtn);
		 oneYearBtn.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	btnClickFunction(ChartType.YEAR);
	            }
	        });
		 
		 ImageButton addToSelfCheckBtn = (ImageButton)findViewById(R.id.addToSelf);
		 addToSelfCheckBtn.setOnClickListener(new AddToSelfCheckFundBtnListener());
	}
	
	/**
	 * �ع���������ť��Ҫ�õ����ظ�����
	 * @param dayCount
	 */
	private void btnClickFunction(ChartType chartType){
		if(jjjzs != null){
			jjjzsChart.setChecked(false);
    		jjjzsChart.setChartType(chartType);
    		refleshHandler.sendEmptyMessage(JJJZ_CHART_REFLESH);
    	}
	}
	
	/**
	 * ��ʾ�����ƺʹ���
	 */
	private void refleshBaseInfo(){
		TextView jjjcTextView = (TextView)findViewById(R.id.JjjzsActivity_jjNameTextView);
		jjjcTextView.setText(this.jjjc);
		
		TextView dmTextView = (TextView)findViewById(R.id.JjjzsActivity_jjdmTextView);
		dmTextView.setText(this.dm);
	}
	
	private class AddToSelfCheckFundBtnListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			ThreadPoolUtils.execute(new AddToSelfCheckFundRunnable());
		}
	}
	
	private class AddToSelfCheckFundRunnable implements Runnable{
		@Override
		public void run() {
			String accountId = InitFundReport.loginStatus.getAccountId();
			String url = NetWorkConfig.getAddSelfCheckFundUrl(accountId, dm);
			try{
				String json = HttpDownloader.downloadCompressedByte(url);
				AddSelfCheckStatus status = ModelFactory.getAddSelfCheckStatus(json);
				System.out.println(status.isSuccess());
				System.out.println(status.getDms());
				
				//������ѡʧ��
				if(status.isSuccess() == false){
					refleshHandler.sendEmptyMessage(ADD_SELF_CHECK_FUND_FAIL);
					return;
				}
				
				refleshHandler.sendEmptyMessage(ADD_SELF_CHECK_FUND_SUCCESS);
				InitFundReport.loginStatus.setSelfCheckJjDms(status.getDms());
			}
			catch(Exception ex){
				refleshHandler.sendEmptyMessage(NET_WORK_ERROR);
				System.out.println("��������ʧ�ܣ�");
			}
		}
	}
}
