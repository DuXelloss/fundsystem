package com.chenjun.struts.filter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * ���������ֻ�ṩFundReport��ʼ����ά�����ܣ��������κ�����
 * 
 * @author zet
 * 
 */
public class FundReportServiceFilter implements Filter {

	private String fundReportPath;
	private long refleshTime;

	private String date;
	private File fundReportFile;
	private byte[] fundReportFileBytes;

	private ScheduledExecutorService threadPool;

	public void destroy() {
		threadPool.shutdown();
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	public void init(FilterConfig arg0) throws ServletException {
		fundReportPath = arg0.getServletContext().getRealPath(
				arg0.getInitParameter("relativeFundReportPath"));
		refleshTime = Integer.parseInt(arg0.getInitParameter("refleshTime"));

		fundReportFile = new File(fundReportPath);
		
		threadPool = Executors.newScheduledThreadPool(2);
		threadPool.scheduleAtFixedRate(new RefleshRunnable(arg0.getServletContext()), 0, refleshTime, TimeUnit.MINUTES);
	}

	// ���report xml�ļ����date���ݣ�����������Ƿ���µ�����
	private String getDate() {
		Document doc;
		String date = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(fundReportFile);
			date = doc.getElementsByTagName("Date").item(0).getTextContent();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	// ���report xml�ļ�ת�����ֽ�����
	private void getFundReportBytes() throws Exception {
		InputStream is = new FileInputStream(fundReportFile);
		byte[] temp = new byte[400];
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		int length = 0;

		while ((length = is.read(temp)) != -1) {
			byteBuffer.write(temp, 0, length);
		}

		is.close();
		byteBuffer.flush();
		byteBuffer.close();

		fundReportFileBytes = byteBuffer.toByteArray();
	}

	// ���report�߳�
	private class RefleshRunnable implements Runnable {

		private ServletContext application;

		public RefleshRunnable(ServletContext servltContext) {
			this.application = servltContext;
		}

		public void run() {

			String newDate = getDate();

			// ���������ȣ����ʾ�޸���
			if (newDate.equals(date)) {
				// do nothing
				System.out.println("Report�ļ�û�и��£�");
			}

			else {
				try {
					date = newDate;

					if (fundReportFileBytes == null) {
						getFundReportBytes();
					} else {
						synchronized (fundReportFileBytes) {
							getFundReportBytes();
						}
					}

					application
							.setAttribute("reportBytes", fundReportFileBytes);
					application.setAttribute("date", date);

					System.out.println("Report�ļ��Ѿ����£���");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

}
