package dao;

import java.sql.*;
import javax.naming.NamingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.*;
import java.util.*;

public class UserDAO {

	
	public boolean insert(String uid, String jsonstr) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get(); 
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO user(id, jsonstr) VALUES(?, ?)";
		
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			stmt.setString(2, jsonstr);
			int count = stmt.executeUpdate();
			return (count == 1) ? true : false;
		} finally {
		if (stmt != null) stmt.close();
		if (conn != null) conn.close(); 
		}
	}
	
	public boolean exists(String uid) throws NamingException, SQLException { 
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
	
		try {
			String sql = "SELECT id FROM user WHERE id = ?"; 
			
			stmt = conn.prepareStatement(sql); stmt.setString(1, uid);
			rs = stmt.executeQuery(); 
			return rs.next();
		} finally {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close(); 
				if (conn != null) conn.close();
		} 
	}
	
	public int check(String uid,String upass) throws NamingException, SQLException,ParseException { 
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
		String sql = "SELECT jsonstr FROM user WHERE id = ?";
		
		conn = ConnectionPool.get();
		stmt = conn.prepareStatement(sql); 
		stmt.setString(1, uid);
		rs = stmt.executeQuery();
		
		if (!rs.next()) return 1;
		
		String jsonstr = rs.getString("jsonstr");
		JSONObject obj = (JSONObject) (new JSONParser()).parse(jsonstr); 
		String pass = obj.get("password").toString();
		
		if (!upass.equals(pass)) return 2; 
		
		return 0;
		
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close(); 
			if (conn != null) conn.close();
		}
	}
	
	
	public boolean delete(String uid) throws NamingException, SQLException { 
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		try {
			String sql = "DELETE FROM user WHERE id = ?"; 
			stmt = conn.prepareStatement(sql); 
			stmt.setString(1, uid);
			
			int count = stmt.executeUpdate(); 
			return (count > 0) ? true : false;
			} finally {
				if (stmt != null) stmt.close(); 
				if (conn != null) conn.close();
			} 
		}
	
	public int login(String uid, String upass) throws NamingException, SQLException,ParseException { 
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
		String sql = "SELECT jsonstr FROM user WHERE id = ?";
		
		conn = ConnectionPool.get();
		stmt = conn.prepareStatement(sql); 
		stmt.setString(1, uid);
		rs = stmt.executeQuery();
		
		if (!rs.next()) return 1;
		
		String jsonstr = rs.getString("jsonstr");
		JSONObject obj = (JSONObject) (new JSONParser()).parse(jsonstr); 
		String pass = obj.get("password").toString();
		
		if (!upass.equals(pass)) return 2; 
		
		return 0;
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close(); 
			if (conn != null) conn.close();
		}
	}
	
	public String getList() throws NamingException, SQLException {
		Connection conn = ConnectionPool.get(); 
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		
		try {
			String sql = "SELECT jsonstr FROM user";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			String str = "["; int cnt = 0;
			
			while(rs.next()) {
				if (cnt++ > 0) str += ", ";
				str += rs.getString("jsonstr"); 
			}
			
			return str + "]";
		}
		
		finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close(); 
			if (conn != null) conn.close();
		}
	}
	
	public String get(String uid) throws NamingException, SQLException { 
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
	
		try {
		String sql = "SELECT jsonstr FROM user WHERE id = ?"; 
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, uid);
		rs = stmt.executeQuery();
		
		return rs.next() ? rs.getString("jsonstr") : "{}";
		
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close(); 
			if (conn != null) conn.close();
		}
	}
	
	
	
	public boolean update(String uid, String jsonstr) throws NamingException, SQLException {
		 Connection conn = ConnectionPool.get();
		 PreparedStatement stmt = null;
		 
		 try {
			 String sql = "UPDATE user SET jsonstr = ? WHERE id = ?";
			 stmt = conn.prepareStatement(sql);
			 stmt.setString(1, jsonstr);
			 stmt.setString(2, uid);
			
			 int count = stmt.executeUpdate();
			 return (count == 1) ? true : false;
		 } finally {
				if (stmt != null) stmt.close();
			 	if (conn != null) conn.close();
			 }
		 }
	
}