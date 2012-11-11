package com.chenjun.xmlpull;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.chenjun.fund.model.FundReportList;

public class XmlPullReader {
	
	public static FundReportList parseFundReportListXml(File xmlFile) {
		XmlPullParser parser = Xml.newPullParser();
		try {
			InputStream is = new FileInputStream(xmlFile);
			parser.setInput(is, "utf-8");
			int eventType = parser.getEventType();
			FundReportList fundReportList = null;
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				
				case XmlPullParser.START_DOCUMENT:// �ĵ���ʼ�¼�,���Խ������ݳ�ʼ������
					fundReportList = new FundReportList();
					break;
					
				case XmlPullParser.START_TAG:// ��ʼԪ���¼�
					String name = parser.getName();
					if (name.equalsIgnoreCase("Date")) {
						fundReportList.setDate(parser.nextText());
					}
					else if (name.equalsIgnoreCase("DateOld")) {
						fundReportList.setDateOld(parser.nextText());
					}
					else if (name.equalsIgnoreCase("FundReport")) {
						fundReportList.addFundReport(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:// ����Ԫ���¼�
					
					break;
				}
				eventType = parser.next();
			}
			
			is.close();
			
			fundReportList.initFundReportList();
			
			return fundReportList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
