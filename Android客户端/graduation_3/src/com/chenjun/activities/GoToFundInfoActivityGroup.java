package com.chenjun.activities;

import android.content.Intent;

/**
 * �ܽ���鿴ĳ�����������Ϣ�ĸ���Activity����ʵ�ִ˽ӿ�
 * Ŀǰ��ʵ������ӿڵ�����������Activity����������������Activity�͡���������Activity��
 * @author zet
 *
 */
public interface GoToFundInfoActivityGroup {
	/**
	 * ÿ���ܲ�ѯ���������Ϣ������ת��FundInfoActivityGroup���Activity���ĸ���Activity��Ҫʵ�����������
	 * �Ա���鿴��������𲻴���������������������ͻ������ǿɿ��ģ��ͻ��˲���������ɿ��Ĵ���ȥ�������ľ�����Ϣ
	 * 
	 * @param intent
	 */
	public abstract void handle(Intent intent);
}
