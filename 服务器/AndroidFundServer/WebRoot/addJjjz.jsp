<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>������ʷ����Json�����������ҳ��</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<h2>���»���ֵҳ��</h2>
    <form action="xxx" method="post">
      <table border="0">
        <tr>
          <td>����:</td>
          <td><input type="text" name="dm" /></td>
        </tr>
        
        <tr>
          <td>����:</td>
          <td><input type="text" name="date" /></td>
        </tr>
        
        <tr>
          <td>��ֵ:</td>
          <td><input type="text" name="jz" /></td>
        </tr>
        
        <tr>
          <td>�ۼƾ�ֵ:</td>
          <td><input type="text" name="ljjz" /></td>
        </tr>
        
        <tr>
          <td>��Ȩ��ֵ:</td>
          <td><input type="text" name="fqjz" /></td>
        </tr>
   
        <tr>
          <td colspan="2" align="center"><input type="submit" /></td>
        </tr>
      </table>
    </form>
  </body>
</html:html>
