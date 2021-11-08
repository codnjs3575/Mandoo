<%@ page contentType="text/html" pageEncoding="utf-8" %> 
<%@ page import="dao.*" %>
<%
	String no = request.getParameter("no");
	int fno = Integer.parseInt(no);
	
	out.print((new FeedDAO()).delete(fno));
%>