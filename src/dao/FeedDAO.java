package dao;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.*;

public class FeedDAO {

	public boolean insert(String jsonstr) throws NamingException, SQLException,ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			synchronized(this) {
				// phase 1. add "no" property -----------------------------
				String sql = "SELECT no FROM community ORDER BY no DESC LIMIT 1"; 
				 stmt = conn.prepareStatement(sql);
				 rs = stmt.executeQuery();
				 
				 int max = (!rs.next()) ? 0 : rs.getInt("no");
				 stmt.close(); rs.close();
				 
				 JSONParser parser =  new JSONParser();
				 JSONObject jsonobj = (JSONObject) parser.parse(jsonstr); 
				 jsonobj.put("no", max + 1); 
				 
				 stmt.close();
				
				// phase 2. add "user" property ------------------------------
				 String uid = jsonobj.get("id").toString();
				 
				 sql = "SELECT jsonstr FROM user WHERE id = ?";
				 stmt = conn.prepareStatement(sql);
				 stmt.setString(1, uid);
				 rs = stmt.executeQuery();
				 
				 if (rs.next()) {
					 String usrstr = rs.getString("jsonstr");
					 JSONObject usrobj = (JSONObject) parser.parse(usrstr);
					 usrobj.remove("password");
					 usrobj.remove("ts");
					 jsonobj.put("user", usrobj);
				}
				stmt.close(); rs.close();
				 
				// phase 3. insert jsonobj to the table ------------------------
				sql = "INSERT INTO community(no, id, jsonstr) VALUES(?,?,?)";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, max + 1);
				stmt.setString(2, uid); 
				stmt.setString(3, jsonobj.toJSONString());
						
				int count = stmt.executeUpdate();
				return (count == 1) ? true : false;
			}
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
			
	public String getList() throws NamingException, SQLException { 
		Connection conn = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		
		try {
			String sql = "SELECT jsonstr FROM community ORDER BY no DESC";
			
			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			String str = "["; int cnt = 0; 
			while(rs.next()) {
				if (cnt++ > 0) str += ", ";
					str += rs.getString("jsonstr"); 
				}
				return str + "]";
			
			
		} finally {
			if (rs != null) rs.close(); 
			if (stmt != null) stmt.close(); 
			if (conn != null) conn.close();
		} 
	}
	
	public String getGroup(String maxNo) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get(); 
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		try {
			String sql = "SELECT jsonstr FROM community"; 
			
			if (maxNo != null) {
				sql += " WHERE no < " + maxNo; 
			}
			
			sql += " ORDER BY no DESC LIMIT 5";
			stmt = conn.prepareStatement(sql); 
			rs = stmt.executeQuery();
			
			String str = "["; int cnt = 0; 
			
			while(rs.next()) {
				if (cnt++ > 0) str += ", ";
					str += rs.getString("jsonstr"); 
				}
				return str + "]";
				
		} finally {
			if (rs != null) rs.close(); 
			if (stmt != null) stmt.close(); 
			if (conn != null) conn.close();
		} 
	}
	
	public String get(String uid,String fno) throws NamingException, SQLException { 
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
	
		try {
		String sql = "SELECT jsonstr FROM community WHERE id = ? and no = ?"; 
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, uid);
		stmt.setString(2, fno);
		rs = stmt.executeQuery();
		
		return rs.next() ? rs.getString("jsonstr") : "{}";
		
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close(); 
			if (conn != null) conn.close();
		}
	}
	
	
	public boolean update(int no, String jsonstr) throws NamingException, SQLException, ParseException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			synchronized(this) {
				JSONParser parser = new JSONParser();
				JSONObject jsonobj = (JSONObject) parser.parse(jsonstr);
				
				String uid = jsonobj.get("id").toString();
				 
				String sql = "SELECT jsonstr FROM user WHERE id = ?";
				 stmt = conn.prepareStatement(sql);
				 stmt.setString(1, uid);
				 rs = stmt.executeQuery();
				 
				 if (rs.next()) {
					 String usrstr = rs.getString("jsonstr");
					 JSONObject usrobj = (JSONObject) parser.parse(usrstr);
					 usrobj.remove("password");
					 usrobj.remove("ts");
					 jsonobj.put("user", usrobj);
				}
				stmt.close(); rs.close();
				
				sql = "UPDATE community SET jsonstr = ? WHERE no = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, jsonobj.toJSONString());
				stmt.setInt(2, no);
				
				int count = stmt.executeUpdate();
				return (count == 1) ? true : false;
			}
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
	public boolean delete(int no) throws NamingException, SQLException, ParseException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		try {
			String sql = "DELETE FROM community WHERE no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, no);
	
			int count = stmt.executeUpdate();
			return (count == 1) ? true : false;
			
		} finally {
			if (stmt != null) stmt.close(); 
			if (conn != null) conn.close();
		}
	}
	
	
	
	
	
	
	

	
	
	
	
}
