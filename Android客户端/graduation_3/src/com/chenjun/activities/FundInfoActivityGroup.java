package com.chenjun.activities;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.chenjun.R;

/**
 * �鿴ĳ�����������Ϣ��Activity����������ֵ���ƣ�����ſ�������ֺ죬�����֣��������
 * 
 * @author zet
 * 
 */
public class FundInfoActivityGroup extends ActivityGroup {
	private static final String JJJZS_ACTIVITY = "jjjzsActivity";
	private static final String JJGK_ACTIVITY = "jjgkActivity";
	private static final String JJFH_ACTIVITY = "jjfhActivity";
	private static final String JJCF_ACTIVITY = "jjcfActivity";
	private static final String JJGS_ACTIVITY = "jjgsActivity";

	private LinearLayout container;

	private String jjjc;
	private String dm;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ������ͼ
		setContentView(R.layout.fundinfoactivitygroup);

		container = (LinearLayout) findViewById(R.id.containerBody);

		Intent intent = getIntent();
		jjjc = intent.getStringExtra("jjjc");
		dm = intent.getStringExtra("dm");

		setBtnListener();
		initContainerView();
	}

	/**
	 * ��ʼ����ť������
	 */
	private void setBtnListener() {
		// ��ֵ����
		ImageButton jjjzsBtn = (ImageButton) findViewById(R.id.FundInfo_Jjjzs_Btn);
		jjjzsBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				container.removeAllViews();
				Activity jjjzActivity = FundInfoActivityGroup.this
						.getLocalActivityManager().getActivity(JJJZS_ACTIVITY);
				if (jjjzActivity == null) {
					getLocalActivityManager().startActivity(
							JJJZS_ACTIVITY,
							new Intent(FundInfoActivityGroup.this,
									JjjzsActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
									.putExtra("jjjc", jjjc).putExtra("dm", dm));
				}
				container.addView(
						getLocalActivityManager().getActivity(JJJZS_ACTIVITY)
								.getWindow().getDecorView(),
						new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
			}
		});

		// ����ſ�
		ImageButton jjgkBtn = (ImageButton) findViewById(R.id.FundInfo_Jjgk_Btn);
		jjgkBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				container.removeAllViews();
				Activity jjcfActivity = FundInfoActivityGroup.this
						.getLocalActivityManager().getActivity(JJGK_ACTIVITY);
				if (jjcfActivity == null) {
					getLocalActivityManager().startActivity(
							JJGK_ACTIVITY,
							new Intent(FundInfoActivityGroup.this,
									JjgkActivity.class).addFlags(
									Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra(
									"dm", dm));
				}

				container.addView(
						getLocalActivityManager().getActivity(JJGK_ACTIVITY)
								.getWindow().getDecorView(),
						new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
			}
		});

		// ����ֺ�
		ImageButton jjfhBtn = (ImageButton) findViewById(R.id.FundInfo_Jjfh_Btn);
		jjfhBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				container.removeAllViews();
				Activity jjfhActivity = FundInfoActivityGroup.this
						.getLocalActivityManager().getActivity(JJFH_ACTIVITY);
				if (jjfhActivity == null) {
					getLocalActivityManager().startActivity(
							JJFH_ACTIVITY,
							new Intent(FundInfoActivityGroup.this,
									JjfhActivity.class).addFlags(
									Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra(
									"dm", dm));
				}

				container.addView(
						getLocalActivityManager().getActivity(JJFH_ACTIVITY)
								.getWindow().getDecorView(),
						new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
			}
		});

		// ������
		ImageButton jjcfBtn = (ImageButton) findViewById(R.id.FundInfo_Jjcf_Btn);
		jjcfBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				container.removeAllViews();
				Activity jjcfActivity = FundInfoActivityGroup.this
						.getLocalActivityManager().getActivity(JJCF_ACTIVITY);
				if (jjcfActivity == null) {
					getLocalActivityManager().startActivity(
							JJCF_ACTIVITY,
							new Intent(FundInfoActivityGroup.this,
									JjcfActivity.class).addFlags(
									Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra(
									"dm", dm));
				}

				container.addView(
						getLocalActivityManager().getActivity(JJCF_ACTIVITY)
								.getWindow().getDecorView(),
						new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
			}
		});

		// ����˾
		ImageButton jjgsBtn = (ImageButton) findViewById(R.id.FundInfo_Jjgs_Btn);
		jjgsBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				container.removeAllViews();
				Activity jjgsActivity = FundInfoActivityGroup.this
						.getLocalActivityManager().getActivity(JJGS_ACTIVITY);
				if (jjgsActivity == null) {
					getLocalActivityManager().startActivity(
							JJGS_ACTIVITY,
							new Intent(FundInfoActivityGroup.this,
									JjgsActivity.class).addFlags(
									Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra(
									"dm", dm));
				}

				container.addView(
						getLocalActivityManager().getActivity(JJGS_ACTIVITY)
								.getWindow().getDecorView(),
						new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
			}
		});
	}

	/**
	 * ��ʼ��Activity��������
	 */
	private void initContainerView() {
		getLocalActivityManager().startActivity(
				JJJZS_ACTIVITY,
				new Intent(FundInfoActivityGroup.this, JjjzsActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
						.putExtra("jjjc", jjjc).putExtra("dm", dm));

		// getLocalActivityManager()
		// .startActivity(
		// JJGK_ACTIVITY,
		// new Intent(
		// FundInfoActivityGroup.this,
		// JjgkActivity.class)
		// .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

		container.addView(getLocalActivityManager().getActivity(JJJZS_ACTIVITY)
				.getWindow().getDecorView(), new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
}
