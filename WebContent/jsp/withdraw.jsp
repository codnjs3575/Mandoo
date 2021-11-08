<%@ page contentType="text/html" pageEncoding="utf-8" %> 
<%@ page import="java.sql.*" %>
<%@ page import="dao.*" %>

<%
	request.setCharacterEncoding("utf-8");
	
	String uid = request.getParameter("id");
	String upass = request.getParameter("pw");
	
	UserDAO dao = new UserDAO();
	
	if (dao.check(uid,upass) == 2) {
		out.print("PW"); 
	}
	else {		
		if(dao.delete(uid) == false){
			out.print("ER"); 
		}
		else out.print("OK");
	}

	
	
	
%>