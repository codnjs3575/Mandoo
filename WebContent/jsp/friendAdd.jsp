<%@ page contentType="text/html" pageEncoding="utf-8" %> 
<%@ page import="dao.*" %>
<%
	String uid = request.getParameter("id"); 
	String frid = request.getParameter("frid"); 
	
	if(new UserDAO().exists(frid)){
		out.print(new FriendDAO().insert(uid, frid));
		return;
	}
	else {
		out.print("NEX");
		
	}
%>