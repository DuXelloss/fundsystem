package com.chenjun.fund.drawable;

/**
 * ��װֵ�����䣬��ͼʱ����������������Ƶ�Ԫ��Ĵ�С
 * @author zet
 *
 */
public class ValueRange {
	double min;
	double max;
	public ValueRange(double min, double max){
		this.min = min;
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
}
