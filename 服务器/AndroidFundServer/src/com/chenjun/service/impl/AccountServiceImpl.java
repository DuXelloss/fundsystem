package com.chenjun.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.chenjun.dao.AccountDao;
import com.chenjun.dao.bean.Account;
import com.chenjun.dao.bean.SelfCheckJj;
import com.chenjun.dao.impl.AccountDaoImpl;
import com.chenjun.fund.model.AddSelfCheckStatus;
import com.chenjun.fund.model.LoginStatus;
import com.chenjun.service.AccountService;

public class AccountServiceImpl implements AccountService{
	
	private static final String ERROR_INFO = "��½ʧ�ܣ��û��������������";
	private static final String SUCCESS_INFO = "��½�ɹ�����ӭ����";
	
	private AccountDao accountDao = new AccountDaoImpl();
	
	/**
	 * ����ID��þ����˻�
	 */
	public Account get(Serializable accountId) {
		return accountDao.get(accountId);
	}
	
	/**
	 * �����û������������˻�
	 * ����˻������ڣ��򷵻�null
	 */
	public Account get(String username, String password) {
		return accountDao.get(username, password);
	}
	
	
	/**
	 * �����û����������¼
	 * ���ص�¼״������
	 */
	public LoginStatus login(String username, String password) {
		LoginStatus loginStatus = new LoginStatus();
		Account accountC = accountDao.get(username, password);
		
		if(null == accountC){
			loginStatus.setSussess(false);
			loginStatus.setExtraInfo(ERROR_INFO);
		}
		else{
			loginStatus.setSussess(true);
			loginStatus.setExtraInfo(SUCCESS_INFO);
			loginStatus.setAccountId(accountC.getId());
			
			List<String> list = new ArrayList<String>();
			for(Iterator<SelfCheckJj> it = accountC.getSelfCheckJjs().iterator(); it.hasNext();){
				list.add(it.next().getDm());
			}
			
			loginStatus.setSelfCheckJjDms(list);
		}
		
		return loginStatus;
	}
	
	/**
	 * �����û���������ע��
	 * ���ע��ɹ�������true
	 * ע��ʧ�ܷ���false��ʧ�ܶ����˻����Ѵ������
	 */
	public boolean register(String account, String password) {
		return accountDao.register(account, password);
	}
	
	/**
	 * �����˻���Ϣ
	 */
	public void update(Account account) {
		accountDao.update(account);
	}
	
	/**
	 * Ϊ�˻�����ĳ����ѡ����
	 */
	public AddSelfCheckStatus addSelfCheckFund(Serializable accountId, String dm) {
		AddSelfCheckStatus status = new AddSelfCheckStatus();
		Account account = get(accountId);
		
		if(account == null){
			status.setSuccess(false);
			return status;
		}
		
		SelfCheckJj selfCheckJj = new SelfCheckJj();
		selfCheckJj.setAccount(account);
		selfCheckJj.setDm(dm);
		
		account.getSelfCheckJjs().add(selfCheckJj);
		
		update(account);
		
		status.setSuccess(true);
		
		List<String> list = new ArrayList<String>();
		for(Iterator<SelfCheckJj> it = account.getSelfCheckJjs().iterator(); it.hasNext();){
			list.add(it.next().getDm());
		}
		status.setDms(list);
		
		return status;
	}
}
