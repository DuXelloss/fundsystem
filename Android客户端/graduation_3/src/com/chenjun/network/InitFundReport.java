package com.chenjun.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.chenjun.fund.model.FundReportList;
import com.chenjun.fund.model.LoginStatus;
import com.chenjun.sax.handler.FundReportContentHandler;


/**
 * ������ṩһ����̬�����ͷ������ܹ�ÿ�����г���ʱ��ȡ���µĻ�����������
 * @author zet
 *
 */
public class InitFundReport {
	public static File reportFile;
	public static FundReportList fundReportList;
	public static LoginStatus loginStatus;
	
//	static{
//		try {
//			reportFile = HttpDownloader.downloadAsFile(NetWorkConfig.getReportUrl(), "androidFund/cache", "Report.xml");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * ������������ˡ�
	 * @param fundReportList
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void parseXML(FundReportList fundReportList) throws SAXException, ParserConfigurationException, FileNotFoundException, IOException{
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		XMLReader xmlReader = saxFactory.newSAXParser().getXMLReader();
		xmlReader.setContentHandler(new FundReportContentHandler(fundReportList));
		xmlReader.parse(new InputSource(new FileReader(reportFile)));
		
		System.out.println(fundReportList.getDate());
	}
}
