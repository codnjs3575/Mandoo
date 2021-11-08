<%@ page contentType="text/html" pageEncoding="utf-8" %> 
<%@ page import="dao.*" %>
<%
	String uid = request.getParameter("id");
	String fno = request.getParameter("no");
	
	out.print((new FeedDAO()).get(uid,fno)); 
%>