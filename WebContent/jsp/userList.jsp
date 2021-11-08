<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="dao.*" %>
<%@ page import="util.ConnectionPool" %>

<%
	String str = (new UserDAO()).getList();
	out.print(str);
%>
