package com.chenjun.fund.model;



/**
 * ������װ����ֵ����Ϣ��
 * ʵ�������Ķ��������ΪXYͼ��ĵ㣬���ʵ��XYPoint�ӿ�
 * @author zet
 *
 */
public class Jjjz implements Model{
	String dm;			//����
	String rq;			//����
	String jz;			//��ֵ
	String ljjz;		//�ۼƾ�ֵ
	String fqjz;		//��Ȩ��ֵ
	
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public String getRq() {
		return rq;
	}
	public void setRq(String rq) {
		this.rq = rq;
	}
	public String getJz() {
		return jz;
	}
	public void setJz(String jz) {
		this.jz = jz;
	}
	public String getLjjz() {
		return ljjz;
	}
	public void setLjjz(String ljjz) {
		this.ljjz = ljjz;
	}
	public String getFqjz() {
		return fqjz;
	}
	public void setFqjz(String fqjz) {
		this.fqjz = fqjz;
	}
	
	public double getJzForPoint() {
		return Double.parseDouble(jz);
	}
}
