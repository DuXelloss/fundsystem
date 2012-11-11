package com.chenjun.fund.model;

import java.util.List;
import java.util.TreeMap;

import com.chenjun.fund.drawable.ChartInfo;
import com.chenjun.fund.drawable.ChartPoints;
import com.chenjun.fund.drawable.ValueRange;
import com.chenjun.utils.StringUtils;
/**
 * ������װ�����һ��ʱ���ڣ�����ֵ����Ϣ.
 * ʵ�������Ķ��������ΪXYͼ���ļ���,���ʵ��XYPointSeries�ӿ�
 * @author zet
 *
 */
public class Jjjzs implements Model, ChartPoints{
	private List<Jjjz> jjjzs;		//����ֵ����
	private boolean isSameFund = true;	//�Ƿ���ͬ������ֵ��Ϣ
	private int count;					//����ֵ�ĸ���
	//private ValueRange range;
	
	/**
	 * ���췽���������ṩ�������ֵ����Ϣ��LinkedList<Jjjz>���У�
	 * @param jjjzs LinkedList<Jjjz>����
	 */
	public Jjjzs(List<Jjjz> jjjzs){
		this.jjjzs = jjjzs;
		count = jjjzs.size();
		checkSameFund();
		//sort();
		//this.range = getJjjzsRange(this.jjjzs.size());
	}
	
	public Jjjz getJjjz(int location){
		if(location > jjjzs.size() -1){
			return null;
		}
		return jjjzs.get(location);
	}
	/**
	 * ���ط�װ�Ķ������ֵ
	 * @return
	 */
	public List<Jjjz> getJjjzs() {
		return jjjzs;
	}
	
	/**
	 * ���ط�װ�Ļ����Ƿ�Ϊͬһ������
	 * @return boolean,true������ͬһ�����𣬷�����ͬһ��
	 */
	public boolean isSameFund() {
		return isSameFund;
	}

	
	/**
	 * ��װ�Ļ������
	 * @return int
	 */
	public int getCount() {
		return count;
	}

	/**
	 * �Է�װ�Ļ�����Ϣ��ʱ���С��������
	 */
//	private void sort(){
//		Jjjz temp = null;
//		for(int i = 0; i < count; i++){
//			for(int j = i + 1; j < count; j++){
//				if(Integer.parseInt(jjjzs.get(i).getRq()) > Integer.parseInt(jjjzs.get(j).getRq())){
//					temp = jjjzs.get(i);
//					jjjzs.set(i, jjjzs.get(j));
//					jjjzs.set(j, temp);
//				}
//			}
//		}
//	}
	
	/**
	 * ����Ƿ�Ϊͬһ�����𣬲���ͬһ������isSameFound��Ϊfalse
	 */
	private void checkSameFund(){
		String dm = null;
		for(int i = 0; i < count; i ++){
			if(i == 0){
				dm = jjjzs.get(0).getDm();
			}else{
				if(!dm.equals(jjjzs.get(i).getDm())){
					isSameFund = false;
					break;
				}
			}
		}
	}
	
	
	public int search(String date){
		int dateInt = Integer.valueOf(date);
		return search(dateInt, 0, jjjzs.size() -1);
	}
	
	private int search(int date, int from, int end){
		if(jjjzs.size() == 0){
			return 0;
		}
		
		if(from == (end - 1)){
			int tempDate = Integer.valueOf(jjjzs.get(from).getRq());
			if(tempDate == date){
				return from;
			}
			else 
				return end;
		}
		
		int half = (end - from) / 2 + from;
		
		int tempDate = Integer.valueOf(jjjzs.get(half).getRq());
//		System.out.println(from);
//		System.out.println(tempDate);
//		System.out.println(end);
		if(tempDate == date){
			return half;
		}
		else if(tempDate > date){
			return search(date, from, half);
		}
		else{
			return search(date, half, end);
		}
	}
	
	/**
	 * ��û���ֵ������
	 */
	public ValueRange getJjjzsRange(int count){
		double min = 0;				//Y����Сֵ
		double max = 0;				//Y�����ֵ
		int pointCount = getPointCount(count);
		int offset= this.count - 1;
		for(int i = 0; i < pointCount; i++){
			//System.out.println(jjjzs.get(i).getRq());
			if(i == 0){
				min = jjjzs.get(offset).getJzForPoint();
			}
			if(jjjzs.get(offset).getJzForPoint() < min){
				min = jjjzs.get(offset).getJzForPoint();
			}
			if(jjjzs.get(offset).getJzForPoint() > max){
				max = jjjzs.get(offset).getJzForPoint();
			}
			offset -= 1;
		}
		return new ValueRange(min, max);
	}
	
	/**
	 * ��û���ֵ������
	 */
	public ValueRange getJjjzsRange(int from, int end){
		double min = 0;				//Y����Сֵ
		double max = 0;				//Y�����ֵ
		//int pointCount = getPointCount(end - from);
		
//		int offset ;
//		if(end >  this.count){
//			offset = this.count - 1;
//		} else { 
//			offset = end - 1;
//		}
		
		for(int i = from; i < end; i++){
			//System.out.println(jjjzs.get(i).getRq());
			if(i == from){
				min = jjjzs.get(i).getJzForPoint();
			}
			if(jjjzs.get(i).getJzForPoint() < min){
				min = jjjzs.get(i).getJzForPoint();
			}
			if(jjjzs.get(i).getJzForPoint() > max){
				max = jjjzs.get(i).getJzForPoint();
			}
			//offset -= 1;
		}
		
//		System.out.println(min);
//		System.out.println(max);
		return new ValueRange(min, max);
	}
	
	
	/**
	 * ����X��ĳ���
	 * @param count Ҫ��ʾ�������ڵĻ���
	 * @return Range����
	 */
	public int getPointCount(int count) {
		if(this.count < count){
			return this.count;
		}
		else{
			return count;
		}
	}
	
	
	/**
	 * ʵ��ChartPoints�ӿڵķ���������ͼ�����Ϣ
	 * @param count ��ʾͼ����Ҫ��ʾ�������ڵĻ������������ӵ�еľ�ֵ�����������򷵻�����
	 * @return ͼ����Ϣ
	 */
	@Override
	public ChartInfo getChartInfo(int count) {
		ChartInfo chartInfo = new ChartInfo();
		TreeMap<Integer, Double> pointMap = new TreeMap<Integer, Double>(); 
		TreeMap<Integer, String> pointDomainMap = new TreeMap<Integer, String>(); 
		TreeMap<Integer, Object> extraInfoMap = new TreeMap<Integer, Object>();
		int pointCount = getPointCount(count);
		ValueRange jjjzsRange = this.getJjjzsRange(count);
		int yOffset = this.count - 1;
		int xOffset = pointCount -1;
		
		String[] valueTick = getValueTick(jjjzsRange);
		
		String domainMax = jjjzs.get(yOffset).getRq();
		String domainHalf = jjjzs.get((yOffset - xOffset) + (pointCount / 2)).getRq();
		String domainMin = jjjzs.get(yOffset - xOffset).getRq();
		String[] domainTick = new String[]{domainMin, domainHalf, domainMax}; 
		domainTick = StringUtils.dateStringArrayChange(domainTick);
		
		for(int i = 0; i < pointCount; i++){
			pointMap.put(xOffset, jjjzs.get(yOffset).getJzForPoint());
			pointDomainMap.put(xOffset, jjjzs.get(yOffset).getRq());
			extraInfoMap.put(xOffset, jjjzs.get(yOffset));
			//System.out.println(xOffset + "--->" + jjjzs.get(yOffset).getY());
			yOffset -= 1;
			xOffset -= 1;
		}
		chartInfo.setPoint(pointMap);
		chartInfo.setPointDomainInfo(pointDomainMap);
		chartInfo.setPointExtreInfo(extraInfoMap);
		chartInfo.setPiontCount(pointCount);
		chartInfo.setDomainCount(count);
		chartInfo.setValueRange(jjjzsRange);
		chartInfo.setValueTick(valueTick);
		chartInfo.setDomainTick(domainTick);
		/*
		for (String string : valueTick) {
			System.out.println(string);
		}
		for (String string : domainTick) {
			System.out.println(string);
		}*/
		return chartInfo;
	}

	/**
	 * ���������ʱ�ò���
	 */
	@Override
	public ChartInfo getChartInfo(int from, int end) {
		if(from > this.count -1){
			return null;
		}
		
		ChartInfo chartInfo = new ChartInfo();
		TreeMap<Integer, Double> pointMap = new TreeMap<Integer, Double>(); 
		TreeMap<Integer, String> pointDomainMap = new TreeMap<Integer, String>(); 
		TreeMap<Integer, Object> extraInfoMap = new TreeMap<Integer, Object>();
		
		int pointCount = getPointCount(end - from);
		ValueRange jjjzsRange = this.getJjjzsRange(from, end);
		int yOffset = end - 1;
		int xOffset = pointCount -1;
		int startOffset = from;
		
		String[] valueTick = getValueTick(jjjzsRange);
		
		String domainMax = jjjzs.get(yOffset).getRq();
		String domainHalf = jjjzs.get((yOffset - xOffset) + (pointCount / 2)).getRq();
		String domainMin = jjjzs.get(yOffset - xOffset).getRq();
		String[] domainTick = new String[]{domainMin, domainHalf, domainMax}; 
		domainTick = StringUtils.dateStringArrayChange(domainTick);
		
		for(int i = 0; i < pointCount; i++){
			pointMap.put(i, jjjzs.get(startOffset).getJzForPoint());
			pointDomainMap.put(i, jjjzs.get(startOffset).getRq());
			extraInfoMap.put(i, jjjzs.get(startOffset));
			//System.out.println(xOffset + "--->" + jjjzs.get(yOffset).getY());
			startOffset++;
		}
		chartInfo.setPoint(pointMap);
		chartInfo.setPointDomainInfo(pointDomainMap);
		chartInfo.setPointExtreInfo(extraInfoMap);
		chartInfo.setPiontCount(pointCount);
		chartInfo.setDomainCount(end - from);
		chartInfo.setValueRange(jjjzsRange);
		chartInfo.setValueTick(valueTick);
		chartInfo.setDomainTick(domainTick);
		/*
		for (String string : valueTick) {
			System.out.println(string);
		}
		for (String string : domainTick) {
			System.out.println(string);
		}*/
		return chartInfo;
	}
	
	/**
	 * ��ֵ��������Y��ı�����������������5������
	 * @param range
	 * @return
	 */
	private String[] getValueTick(ValueRange range){
		String valueMin = String.valueOf(range.getMin());
		String valueQuarter = String.valueOf((range.getMax() - range.getMin()) / 4 + range.getMin());
		String valueHalf = String.valueOf((range.getMax() - range.getMin()) / 2 + range.getMin());
		String value3Quarter = String.valueOf((range.getMax() - range.getMin()) *3 / 4 + range.getMin());
		String valueMax = String.valueOf(range.getMax());
		String[] valueTick = new String[]{valueMin, valueQuarter, valueHalf, value3Quarter, valueMax};
		valueTick = StringUtils.stringArrayLengthFormat(valueTick, 6, '0');
		return valueTick;
	}
	
}
