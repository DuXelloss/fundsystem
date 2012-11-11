package com.chenjun.activities;

import android.app.Activity;
import android.app.ProgressDialog;

public class DownloadActivity extends Activity{
		
	protected String messageWhenDownlonding = "���������У���ȴ�";
	
	protected void setMessageWhenDownloading(String str){
		this.messageWhenDownlonding = str;
	}
	
	protected ProgressDialog waitDialog;
	
	protected void showWaitDialog(){
		if(waitDialog == null){
			waitDialog = new ProgressDialog(this);
			waitDialog.setMessage(messageWhenDownlonding);
		}
		
		waitDialog.show();
	}
	
	protected void hideWaitDialog(){
		if(waitDialog != null){
			waitDialog.hide();
		}
	}
}
