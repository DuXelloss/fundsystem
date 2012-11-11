package com.chenjun.listviewadapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.chenjun.R;
import com.chenjun.activities.FundInfoActivityGroup;
import com.chenjun.activities.GoToFundInfoActivityGroup;
import com.chenjun.fund.model.FundReport;
import com.chenjun.view.SyncHorizontalScrollView;

public class FundBaseInfoListViewAdapter extends BaseAdapter {
	private List<FundReport> mData;
	private LayoutInflater mInflater;
	private GoToFundInfoActivityGroup parentActivity;
	private Context context;
	private HorizontalScrollView headView;
	
	public FundBaseInfoListViewAdapter(Context context, List<FundReport> mData, GoToFundInfoActivityGroup parentActivity, HorizontalScrollView headView){
		this.context = context;
		this.mData = mData;
		this.mInflater = LayoutInflater.from(context);
		this.parentActivity = parentActivity;
		this.headView = headView;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		
        if (convertView == null) {
             
            holder = new ViewHolder();  
             
            convertView = mInflater.inflate(R.layout.fundbaseinfo_list_element, null);
            
            holder.jjjc = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Jjjc);
            holder.dm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Jjdm);
            
            holder.tzlx = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Tzlx);
            holder.zxrq = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Rq);
            holder.jjjz = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Jjjz);
            holder.rzd = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Increase);
            holder.ljjz = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Ljjz);
            holder.rzf = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_IncreasePrecent);
            holder.tlxpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Tlxpm);
            holder.zpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Zpm);
            
            holder.sqrq = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Sqrq);
            holder.sqjz = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Sqjz);
            holder.sqljjz = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_Sqljjz);
            
            holder.zzf = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_zzf);
            holder.zzftlxpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_zzftlxpm);
            holder.zzfzpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_zzfzpm);
            
            holder.yzf = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_yzf);
            holder.yzftlxpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_yzftlxpm);
            holder.yzfzpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_yzfzpm);
            
            holder.jdzf = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_jdzf);
            holder.jdzftlxpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_jdzftlxpm);
            holder.jdzfzpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_jdzfzpm);
            
            holder.bnzf = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_bnzf);
            holder.bnzftlxpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_bnzftlxpm);
            holder.bnzfzpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_bnzfzpm);
            
            holder.nzf = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_nzf);
            holder.nzftlxpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_nzftlxpm);
            holder.nzfzpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_nzfzpm);
            
            holder.jnzf = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_jnzf);
            holder.jnzftlxpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_jnzftlxpm);
            holder.jnzfzpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_jnzfzpm);
            
            holder.lnzf = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_lnzf);
            holder.lnzftlxpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_lnzftlxpm);
            holder.lnzfzpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_lnzfzpm);
            
            holder.snzf = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_snzf);
            holder.snzftlxpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_snzftlxpm);
            holder.snzfzpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_snzfzpm);
            
            holder.wnzf = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_wnzf);
            holder.wnzftlxpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_wnzftlxpm);
            holder.wnzfzpm = (TextView)convertView.findViewById(R.id.FundBaseInfo_element_wnzfzpm);
            
            convertView.setTag(holder);
             
        }else {
             
            holder = (ViewHolder)convertView.getTag();
        }
        
        holder.jjjc.setText(mData.get(position).getJjjc());
        holder.dm.setText(mData.get(position).getDm());
        
        holder.tzlx.setText(mData.get(position).getTzlx());
        holder.zxrq.setText(mData.get(position).getZxrq());
        holder.jjjz.setText(mData.get(position).getZxjz());
        holder.rzd.setText(mData.get(position).getRzd());
        holder.ljjz.setText(mData.get(position).getLjjz());
        holder.rzf.setText(mData.get(position).getRzf());
        holder.tlxpm.setText(mData.get(position).getRzftlxpm());
        holder.zpm.setText(mData.get(position).getRzfzpm());
        
        holder.sqrq.setText(mData.get(position).getSqrq());
        holder.sqjz.setText(mData.get(position).getSqjz());
        holder.sqljjz.setText(mData.get(position).getSqljjz());
        
        holder.zzf.setText(mData.get(position).getZzf());
        holder.zzftlxpm.setText(mData.get(position).getZzftlxpm());
        holder.zzfzpm.setText(mData.get(position).getZzfzpm());
        
        holder.yzf.setText(mData.get(position).getYzf());
        holder.yzftlxpm.setText(mData.get(position).getYzftlxpm());
        holder.yzfzpm.setText(mData.get(position).getYzfzpm());
        
        holder.jdzf.setText(mData.get(position).getJzf());
        holder.jdzftlxpm.setText(mData.get(position).getJdzftlxpm());
        holder.jdzfzpm.setText(mData.get(position).getJdzfzpm());
        
        holder.bnzf.setText(mData.get(position).getBnzf());
        holder.bnzftlxpm.setText(mData.get(position).getBnzftlxpm());
        holder.bnzfzpm.setText(mData.get(position).getBnzfzpm());
        
        holder.nzf.setText(mData.get(position).getYnzf());
        holder.nzftlxpm.setText(mData.get(position).getNzftlxpm());
        holder.nzfzpm.setText(mData.get(position).getNzfzpm());
        
        holder.jnzf.setText(mData.get(position).getJnylzf());
        holder.jnzftlxpm.setText(mData.get(position).getJnzftlxpm());
        holder.jnzfzpm.setText(mData.get(position).getJnzfzpm());
        
        holder.lnzf.setText(mData.get(position).getLnzf());
        holder.lnzftlxpm.setText(mData.get(position).getLnzftlxpm());
        holder.lnzfzpm.setText(mData.get(position).getLnzfzpm());
        
        holder.snzf.setText(mData.get(position).getSnzf());
        holder.snzftlxpm.setText(mData.get(position).getSnzftlxpm());
        holder.snzfzpm.setText(mData.get(position).getSnzfzpm());
        
        holder.wnzf.setText(mData.get(position).getWnzf());
        holder.wnzftlxpm.setText(mData.get(position).getWnzftlxpm());
        holder.wnzfzpm.setText(mData.get(position).getWnzfzpm());
        
        Intent intent = new Intent();
		intent.putExtra("jjjc", mData.get(position).getJjjc());
		intent.putExtra("dm", mData.get(position).getDm());
		intent.setClass(context, FundInfoActivityGroup.class);
        
        convertView.setOnClickListener(new ClickListener(intent));
        
        SyncHorizontalScrollView scrollView = (SyncHorizontalScrollView)convertView.findViewById(R.id.fundbaseinfo_list_item_scrollView);
        scrollView.setSyncView(parent);
        scrollView.setHeadView(headView);
        
        return convertView;
	}
	
	
	public final class ViewHolder{
		public TextView jjjc;				//������
		public TextView dm;					//�������
		
		public TextView tzlx;				//Ͷ������
		public TextView zxrq;				//����
		public TextView jjjz;				//����ֵ
		public TextView rzd;				//���ǵ�
		public TextView ljjz;				//�ۼƾ�ֵ
		public TextView rzf;				//���Ƿ�
		public TextView tlxpm;				//ͬ��������
		public TextView zpm;				//������
		
		public TextView sqrq;				//��������
		public TextView sqjz;				//���ھ�ֵ
		public TextView sqljjz;				//�����ۼƾ�ֵ
		
		public TextView zzf;				//���Ƿ�
		public TextView zzftlxpm;			//���Ƿ�ͬ��������
		public TextView zzfzpm;				//���Ƿ�������
		
		public TextView yzf;				//���Ƿ�
		public TextView yzftlxpm;			//���Ƿ�ͬ��������
		public TextView yzfzpm;				//���Ƿ�������
		
		public TextView jdzf;				//�����Ƿ�
		public TextView jdzftlxpm;			//�����Ƿ�ͬ��������
		public TextView jdzfzpm;			//�����Ƿ�������
		
		public TextView bnzf;				//�����Ƿ�
		public TextView bnzftlxpm;			//�����Ƿ�ͬ��������
		public TextView bnzfzpm;			//�����Ƿ�������
		
		public TextView nzf;				//���Ƿ�
		public TextView nzftlxpm;			//���Ƿ�ͬ��������
		public TextView nzfzpm;				//���Ƿ�������
		
		public TextView jnzf;				//�����Ƿ�
		public TextView jnzftlxpm;			//�����Ƿ�ͬ��������
		public TextView jnzfzpm;			//�����Ƿ�������
		
		public TextView lnzf;				//�����Ƿ�
		public TextView lnzftlxpm;			//�����Ƿ�ͬ��������
		public TextView lnzfzpm;			//�����Ƿ�������
		
		public TextView snzf;				//�����Ƿ�
		public TextView snzftlxpm;			//�����Ƿ�ͬ��������
		public TextView snzfzpm;			//�����Ƿ�������
		
		public TextView wnzf;				//�����Ƿ�
		public TextView wnzftlxpm;			//�����Ƿ�ͬ��������
		public TextView wnzfzpm;			//�����Ƿ�������
	}
	
	private class ClickListener implements OnClickListener{
		private Intent intent;
		
		public ClickListener(Intent intent){
			this.intent = intent;
		}
		@Override
		public void onClick(View v) {
			parentActivity.handle(intent);
		}
	}
	
	
	
	public void addDate(List<FundReport> addList){
		this.mData.addAll(addList);
	}
}
