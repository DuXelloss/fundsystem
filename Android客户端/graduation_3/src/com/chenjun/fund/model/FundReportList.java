package com.chenjun.fund.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FundReportList {
	private String date;
	private String dateOld;
	private List<String> fundReportStrList;
	private List<FundReport> fundReportList;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDateOld() {
		return dateOld;
	}

	public void setDateOld(String dateOld) {
		this.dateOld = dateOld;
	}

	public void addFundReport(String fundReport) {
		if(null == fundReportStrList){
			fundReportStrList =  new ArrayList<String>();
		}
		fundReportStrList.add(fundReport);
	}

	public List<String> getFundReportStrList() {
		return fundReportStrList;
	}

	public void setFundReportStrList(List<String> fundReportStrList) {
		this.fundReportStrList = fundReportStrList;
	}

	public List<FundReport> getFundReportList() {
		return fundReportList;
	}

	public void setFundReportList(List<FundReport> fundReportList) {
		this.fundReportList = fundReportList;
	}

	public void initFundReportList() {
		if(fundReportStrList == null){
			return;
		}
		
		if(null == fundReportList){
			fundReportList = new ArrayList<FundReport>();
		}
		
		for (int i = 0; i < fundReportStrList.size(); i++) {
			FundReport fundReport = new FundReport();
			
			String fundReportStr = fundReportStrList.get(i);
			
			String[] infoArray = fundReportStr.split(",");
			
			if(infoArray.length != 45){
				continue;
			}

			String dm = infoArray[0]; // �������,
			fundReport.setDm(dm);
			
			String jjjc = infoArray[1]; // ������,
			fundReport.setJjjc(jjjc);
			
			String tzlx = infoArray[2]; // Ͷ������,
			fundReport.setTzlx(tzlx);
			
			String dycjjrq = infoArray[3]; // ��һ�εľ�ֵ����,
			fundReport.setDycjjrq(dycjjrq);
			
			String zxrq = infoArray[4]; // ��������,
			fundReport.setZxrq(zxrq);
			
			String zxjz = infoArray[5]; // ���¾�ֵ,
			fundReport.setZxjz(zxjz);
			
			String ljjz = infoArray[6]; // �ۼƾ�ֵ,
			fundReport.setLjjz(ljjz);
			
			String fqjz = infoArray[7]; // ��Ȩ��ֵ,
			fundReport.setFqjz(fqjz);
			
			String sqrq = infoArray[8]; // ��������,
			fundReport.setSqrq(sqrq);
			
			String sqjz = infoArray[9]; // ���ھ�ֵ,
			fundReport.setSqjz(sqjz);
			
			String sqljjz = infoArray[10]; // �����ۼƾ�ֵ,
			fundReport.setSqljjz(sqljjz);
			
			String sqfqjz = infoArray[11]; // ���ڸ�Ȩ��ֵ,
			fundReport.setSqfqjz(sqfqjz);
			
			String rzd = infoArray[12]; // ���ǵ�,
			fundReport.setRzd(rzd);
			
			String rzf = infoArray[13]; // ���Ƿ�,
			fundReport.setRzf(rzf);
			
			String zzf = infoArray[14]; // ���Ƿ�,
			fundReport.setZzf(zzf);
			
			String yzf = infoArray[15]; // ���Ƿ�,
			fundReport.setYzf(yzf);
			
			String jzf = infoArray[16]; // ���Ƿ�,
			fundReport.setJzf(jzf);
			
			String bnzf = infoArray[17]; // �����Ƿ�,
			fundReport.setBnzf(bnzf);
			
			String ynzf = infoArray[18]; // һ���Ƿ�,
			fundReport.setYnzf(ynzf);
			
			String jnylzf = infoArray[19]; // ���������Ƿ�,
			fundReport.setJnylzf(jnylzf);
			
			String lnzf = infoArray[20]; // �����Ƿ�,
			fundReport.setLnzf(lnzf);
			
			String snzf = infoArray[21]; // �����Ƿ�,
			fundReport.setSnzf(snzf);
			
			String wnzf = infoArray[22]; // �����Ƿ�,
			fundReport.setWnzf(wnzf);
			
			String clylzf = infoArray[23]; // ���������Ƿ�,
			fundReport.setClylzf(clylzf);
			
			String rzftlxpm = infoArray[24]; // ���Ƿ�ͬ��������,
			fundReport.setRzftlxpm(rzftlxpm);
			
			String syrrzftlxpm = infoArray[25]; // ��һ�����Ƿ�ͬ��������(ע, ��Ϊ���һ�������,�൱����һ����������),
			fundReport.setSyrrzftlxpm(syrrzftlxpm);
			
			String zzftlxpm = infoArray[26]; // ���Ƿ�ͬ��������,
			fundReport.setZzftlxpm(zzftlxpm);
			
			String yzftlxpm = infoArray[27]; // ���Ƿ�ͬ��������,
			fundReport.setYzftlxpm(yzftlxpm);
			
			String jdzftlxpm = infoArray[28]; // �����Ƿ�ͬ��������,
			fundReport.setJdzftlxpm(jdzftlxpm);
			
			String bnzftlxpm = infoArray[29]; // �����Ƿ�ͬ��������,
			fundReport.setBnzftlxpm(bnzftlxpm);
			
			String nzftlxpm = infoArray[30]; // ���Ƿ�ͬ��������,
			fundReport.setNzftlxpm(nzftlxpm);
			
			String jnzftlxpm = infoArray[31]; // �����Ƿ�ͬ��������,
			fundReport.setJnzftlxpm(jnzftlxpm);
			
			String lnzftlxpm = infoArray[32]; // �����Ƿ�ͬ��������,
			fundReport.setLnzftlxpm(lnzftlxpm);
			
			String snzftlxpm = infoArray[33]; // �����Ƿ�ͬ��������,
			fundReport.setSnzftlxpm(snzftlxpm);
			
			String wnzftlxpm = infoArray[34]; // �����Ƿ�ͬ��������,
			fundReport.setWnzftlxpm(wnzftlxpm);
			
			String rzfzpm = infoArray[35]; // ���Ƿ�������,
			fundReport.setRzfzpm(rzfzpm);
			
			String zzfzpm = infoArray[36]; // ���Ƿ�������,
			fundReport.setZzfzpm(zzfzpm);
			
			String yzfzpm = infoArray[37]; // ���Ƿ�������,
			fundReport.setYzfzpm(yzfzpm);
			
			String jdzfzpm = infoArray[38]; // �����Ƿ�������,
			fundReport.setJdzfzpm(jdzfzpm);
			
			String bnzfzpm = infoArray[39]; // �����Ƿ�������,
			fundReport.setBnzfzpm(bnzfzpm);
			
			String nzfzpm = infoArray[40]; // ���Ƿ�������,
			fundReport.setNzfzpm(nzfzpm);
			
			String jnzfzpm = infoArray[41]; // �����Ƿ�������,
			fundReport.setJnzfzpm(jnzfzpm);
			
			String lnzfzpm = infoArray[42]; // �����Ƿ�������,
			fundReport.setLnzfzpm(lnzfzpm);
			
			String snzfzpm = infoArray[43]; // �����Ƿ�������,
			fundReport.setSnzfzpm(snzfzpm);
			
			String wnzfzpm = infoArray[44]; // �����Ƿ�������
			fundReport.setWnzfzpm(wnzfzpm);
			
			fundReportList.add(fundReport);
		}
	}
	
	public List<FundReport> search(String keyWord){
		List<FundReport> list = new ArrayList<FundReport>();
		
		FundReport temp = null;
		for(int i = 0; i < fundReportList.size(); i++){
			temp = fundReportList.get(i);
			
			if(temp.getDm().contains(keyWord) || temp.getJjjc().contains(keyWord)){
				list.add(temp);
			}
		}
		
		return list;
	}
	
	public List<FundReport> getSelfCheckFund(List<String> dms){
		List<FundReport> list = new ArrayList<FundReport>();
		List<String> tempDms = new ArrayList<String>(dms);
		
		FundReport temp = null;
		for(int i = 0; i < fundReportList.size(); i++){
			temp = fundReportList.get(i);
			
			for(int j = 0; j < tempDms.size(); j ++){
				if(temp.getDm().equals(tempDms.get(j))){
					list.add(temp);
					tempDms.remove(j);
				}
			}
		}
		
		return list;
	}
}
