package com.chenjun.fund.drawable;

import java.util.TreeMap;

/**
 * ��װһ����������ͼ����Ҫ��ȫ����Ϣ
 * ���ͼ�е�ÿһ���㣬��X�������ġ�
 * @author zet
 *
 */
public class ChartInfo {
	private TreeMap<Integer, Double> point;				//Y���Ӧ��ֵ
	private TreeMap<Integer, String> pointDomainInfo;	//X���Ӧ����Ϣ
	private TreeMap<Integer, Object> pointExtraInfo;	//������Ϣ
	private int piontCount;								//ͼ���е�ĸ���
	private ValueRange valueRange;						//Y��ֵ����
	private int domainCount;							//X���ĸ���
	private String[] valueTick;							//Y���ʶ
	private String[] domainTick;						//X���ʶ
	
	public TreeMap<Integer, Object> getPointExtraInfo() {
		return pointExtraInfo;
	}
	public void setPointExtreInfo(
			TreeMap<Integer, Object> pointExtraInfo) {
		this.pointExtraInfo = pointExtraInfo;
	}
	public TreeMap<Integer, String> getPointDomainInfo() {
		return pointDomainInfo;
	}
	public void setPointDomainInfo(TreeMap<Integer, String> pointDomainInfo) {
		this.pointDomainInfo = pointDomainInfo;
	}
	public String[] getValueTick() {
		return valueTick;
	}
	public void setValueTick(String[] valueTick) {
		this.valueTick = valueTick;
	}
	public String[] getDomainTick() {
		return domainTick;
	}
	public void setDomainTick(String[] domainTick) {
		this.domainTick = domainTick;
	}
	public int getPiontCount() {
		return piontCount;
	}
	public void setPiontCount(int piontCount) {
		this.piontCount = piontCount;
	}
	public TreeMap<Integer, Double> getPoint() {
		return point;
	}
	public void setPoint(TreeMap<Integer, Double> point) {
		this.point = point;
	}
	public ValueRange getValueRange() {
		return valueRange;
	}
	public void setValueRange(ValueRange valueRange) {
		this.valueRange = valueRange;
	}
	public int getDomainCount() {
		return domainCount;
	}
	public void setDomainCount(int keyRange) {
		domainCount = keyRange;
	}
}
