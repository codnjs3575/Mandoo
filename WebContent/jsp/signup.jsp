<%@ page contentType="text/html" pageEncoding="utf-8" %> 
<%@ page import="java.sql.*" %>
<%@ page import="util.ConnectionPool" %>
<%@ page import="dao.*" %>

<%
request.setCharacterEncoding("utf-8");

String uid = request.getParameter("id");
String jsonstr = request.getParameter("jsonstr");

UserDAO dao = new UserDAO();

if (dao.exists(uid)) {
	out.print("EX"); 
	return;
	}
if (dao.insert(uid, jsonstr)){
	session.setAttribute("id", uid);
	out.print("OK");
}
else {
	out.print("ER");
	}

%>