package com.chenjun.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.chenjun.R;
import com.chenjun.fund.model.Jjgk;
import com.chenjun.fund.model.ModelFactory;
import com.chenjun.network.HttpDownloader;
import com.chenjun.network.NetWorkConfig;
import com.chenjun.utils.StringUtils;
import com.chenjun.utils.ThreadPoolUtils;

public class JjgkActivity extends Activity{
	private static final int JJGK_RECEIVE_REFLESH = 1;
	
	private Jjgk jjgk;
	//private Thread downloadThread;								//���ػ������Ƶ��߳�
	private Handler refleshHandler = new RefleshHandler();		//ˢ��Handler�������û���ť��������ݵ���ʱ�����Ӧ�¼�
	
	private String dm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.jjgkactivity);
		super.onCreate(savedInstanceState);
		
		dm = getIntent().getStringExtra("dm");
		
		if(jjgk == null){
			ThreadPoolUtils.execute(new DownloadRunnable(dm));
		}
	}
	
	/**
	 * ˢ��Handler
	 * @author zet
	 *
	 */
	private class RefleshHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			if(JJGK_RECEIVE_REFLESH == msg.what){
				refleshInfo();
			}
		}
	}
	
	/**
	 * ���������߳̾��庯��
	 * @author zet
	 *
	 */
	private class DownloadRunnable implements Runnable{
		private String dm;
		
		public DownloadRunnable(String dm){
			this.dm = dm;
		}
		
		@Override
		public void run() {
			System.out.println("��ʼ��������");
			
			String json;
			try {
				//json = HttpDownloader.download("http://60.176.46.221:8081/AndroidFundServer/jjgk.json?dm=000021");
				//json = HttpDownloader.download("http://androidfund.3322.org:8081/AndroidFundServer/jjgk.json?dm=000021");
				json = HttpDownloader.downloadCompressedByte(NetWorkConfig.getJjgkUrl(dm));
				System.out.println("��������ɹ���");
				
				//System.out.println(json);
				
				jjgk = ModelFactory.getJjgk(json);
				
				System.out.println("����ת���ɹ���");
				
				//���Ͷ�Ӧ�¼���Handler
				refleshHandler.sendEmptyMessage(JJGK_RECEIVE_REFLESH);
			} catch (Exception e) {
				//����ʧ�ܴ���Ӧ���½�һ����ʾ���û�����Ϣ��ѯ���Ƿ�����
				//Toast.makeText(JjjzsActivity.this, "��������ʧ�ܣ�", Toast.LENGTH_LONG).show();
				System.out.println("��������ʧ�ܣ�");
			}
		}
	}
	
	private void refleshInfo(){
		if(jjgk == null){
			return;
		}
		
		//����
		TextView dmTextView = (TextView)findViewById(R.id.Jjgk_dm_Value);
		setTextViewText(dmTextView, jjgk.getDm());
		
		//��������
		TextView jjmcTextView = (TextView)findViewById(R.id.Jjgk_jjmc_Value);
		setTextViewText(jjmcTextView, jjgk.getJjmc());
		
		//������
		TextView jjjcTextView = (TextView)findViewById(R.id.Jjgk_jjjc_Value);
		setTextViewText(jjjcTextView, jjgk.getJjjc());
		
		//ƴ�����
		TextView pyjcTextView = (TextView)findViewById(R.id.Jjgk_pyjc_Value);
		setTextViewText(pyjcTextView, jjgk.getPyjc());
		
		//��������
		TextView jjlxTextView = (TextView)findViewById(R.id.Jjgk_jjlx_Value);
		setTextViewText(jjlxTextView, jjgk.getJjlx());
		
		//���������
		TextView jjglrTextView = (TextView)findViewById(R.id.Jjgk_jjglr_Value);
		setTextViewText(jjglrTextView, jjgk.getJjglr());
		
		//�����й���
		TextView jjtgrTextView = (TextView)findViewById(R.id.Jjgk_jjtgr_Value);
		setTextViewText(jjtgrTextView, jjgk.getJjtgr());
		
		//�������
		TextView glflTextView = (TextView)findViewById(R.id.Jjgk_glfl_Value);
		setTextViewText(glflTextView, jjgk.getGlfl());
		
		//�йܷ���
		TextView tgflTextView = (TextView)findViewById(R.id.Jjgk_tgfl_Value);
		setTextViewText(tgflTextView, jjgk.getTgfl());
		
		//Ͷ������
		TextView tzlxTextView = (TextView)findViewById(R.id.Jjgk_tzlx_Value);
		setTextViewText(tzlxTextView, jjgk.getTzlx());
		
		//��������
		TextView clrqTextView = (TextView)findViewById(R.id.Jjgk_clrq_Value);
		setTextViewText(clrqTextView, jjgk.getClrq());
		
		//�����깺��ʼ��
		TextView kfsgqsrTextView = (TextView)findViewById(R.id.Jjgk_kfsgqsr_Value);
		setTextViewText(kfsgqsrTextView, jjgk.getKfsgqsr());
		
		//���������ʼ��
		TextView kfshqsrTextView = (TextView)findViewById(R.id.Jjgk_kfshqsr_Value);
		setTextViewText(kfshqsrTextView, jjgk.getKfshqsr());
		
		//�����깺�������
		TextView dbsgjexxTextView = (TextView)findViewById(R.id.Jjgk_dbsgjexx_Value);
		setTextViewText(dbsgjexxTextView, jjgk.getDbsgjexx());
		
		//������طݶ�����
		TextView dbshfexxTextView = (TextView)findViewById(R.id.Jjgk_dbshfexx_Value);
		setTextViewText(dbshfexxTextView, jjgk.getDbshfexx());
		
		//Ͷ�ʷ��
		TextView tzfgTextView = (TextView)findViewById(R.id.Jjgk_tzfg_Value);
		setTextViewText(tzfgTextView, jjgk.getTzfg());
		
		//Ͷ��Ŀ��
		TextView tzmbTextView = (TextView)findViewById(R.id.Jjgk_tzmb_Value);
		setTextViewText(tzmbTextView, jjgk.getTzmb());
		
		//Ͷ�ʷ�Χ
		TextView tzfwTextView = (TextView)findViewById(R.id.Jjgk_tzfw_Value);
		setTextViewText(tzfwTextView, jjgk.getTzfw());
		
		//�޶���ر����϶�
		TextView jeshblrdTextView = (TextView)findViewById(R.id.Jjgk_jeshblrd_Value);
		setTextViewText(jeshblrdTextView, jjgk.getJeshblrd());
		
		//�޶��������
		TextView jeshtkTextView = (TextView)findViewById(R.id.Jjgk_jeshtk_Value);
		setTextViewText(jeshtkTextView, jjgk.getJeshtk());
		
		//�г�������ʾ
		TextView scfxtsTextView = (TextView)findViewById(R.id.Jjgk_scfxts_Value);
		setTextViewText(scfxtsTextView, jjgk.getScfxts());
		
		//���������ʾ
		TextView glfxtsTextView = (TextView)findViewById(R.id.Jjgk_glfxts_Value);
		setTextViewText(glfxtsTextView, jjgk.getGlfxts());
		
		//����������ʾ
		TextView jsfxtsTextView = (TextView)findViewById(R.id.Jjgk_jsfxts_Value);
		setTextViewText(jsfxtsTextView, jjgk.getJsfxts());
		
		//������ȡ��׼
		TextView fltqbzTextView = (TextView)findViewById(R.id.Jjgk_fltqbz_Value);
		setTextViewText(fltqbzTextView, jjgk.getFltqbz());
		
		//��������
		TextView jjfqrTextView = (TextView)findViewById(R.id.Jjgk_jjfqr_Value);
		setTextViewText(jjfqrTextView, jjgk.getJjfqr());
		
		//���۴�����
		TextView xsdlrTextView = (TextView)findViewById(R.id.Jjgk_xsdlr_Value);
		setTextViewText(xsdlrTextView, jjgk.getXsdlr());
		
		//Ͷ�ʲ���
		TextView tzclTextView = (TextView)findViewById(R.id.Jjgk_tzcl_Value);
		setTextViewText(tzclTextView, jjgk.getTzcl());
		
		//������ֹ����
		TextView jjzztkTextView = (TextView)findViewById(R.id.Jjgk_jjzztk_Value);
		setTextViewText(jjzztkTextView, jjgk.getJjzztk());
		
		//ҵ���Ƚϻ�׼
		TextView yjbjjzTextView = (TextView)findViewById(R.id.Jjgk_yjbjjz_Value);
		setTextViewText(yjbjjzTextView, jjgk.getYjbjjz());
		
		//�깺״̬
		TextView sgztTextView = (TextView)findViewById(R.id.Jjgk_sgzt_Value);
		setTextViewText(sgztTextView, jjgk.getSgzt());
	}
	
	private void setTextViewText(TextView textView, String info){
		if(textView == null || info == null){
			return;
		}
		info = StringUtils.databaseStr2TextViewStr(info);
		textView.setText(StringUtils.addT(info));
	}
}
