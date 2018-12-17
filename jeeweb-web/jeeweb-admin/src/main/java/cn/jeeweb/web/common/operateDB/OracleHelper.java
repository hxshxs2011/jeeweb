package cn.jeeweb.web.common.operateDB;

import java.io.*;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import  cn.jeeweb.web.common.constant.otherDBConstant;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 * 
 */
public class OracleHelper {

	Connection con = null;
	PreparedStatement pre = null;
	ResultSet result = null;
	static List<?> rtnList = null;

	public  Connection getConn(String DB) {
	  connDB(DB);
      return con;
	}
	public OracleHelper() {

	}
	public List<?> getTableFromSQL(String TSQL, String DB) throws SQLException {

		try {
			connDB(DB);
			pre = con.prepareStatement(TSQL);
			result = pre.executeQuery();
			rtnList = resultSetToList(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtnList;
	}

	private void connDB(String DB) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = getConnXML();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("unchecked")
		Map<String, String> map2 = (Map<String, String>) map.get(DB);
		String url = map2.get("url").toString();
		String user = map2.get("user").toString();
		String password = map2.get("password").toString();
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map> resultSetToList(ResultSet rs)
			throws java.sql.SQLException {
		if (rs == null)
			return (List<Map>) Collections.EMPTY_LIST;
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		List<Map> list = (List<Map>) new ArrayList();
		Map<String, Object> rowData = new HashMap<String, Object>();
		while (rs.next()) {
			rowData = new HashMap<String, Object>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				//rowData.put(md.getColumnName(i), rs.getObject(i));
				if (rs.getObject(i) != null) {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				} else {
					rowData.put(md.getColumnName(i), "");
				}
			}
			list.add(rowData);
		}
		return list;
	}
	private Map<String, Object> getConnXML() throws DocumentException {

		InputStream is= OracleHelper.class.getClassLoader().getResourceAsStream("connDB.xml");
		SAXReader reader = new SAXReader();
		Document doc = reader.read(is);
		Element root = doc.getRootElement();
		Element foo;

		Map<String, Object> map = new HashMap<String, Object>();

		for (Iterator<?> i = root.elementIterator("dbname"); i.hasNext();) {
			foo = (Element) i.next();
			String name = foo.attributeValue("name");
			Map<String, String> childmap = new HashMap<String, String>();
			childmap.put("url", foo.attributeValue("url"));
			childmap.put("user", foo.attributeValue("user"));
			childmap.put("password", foo.attributeValue("password"));
			map.put(name, childmap);

			// list.add(map);
		}

		// System.out.println(((Map<String,
		// String>)map.get("jrmes")).get("url"));

		return map;

	}

	//
	/**
	 * 获取所有表格字段名称
	 * 
	 * @param tablelist
	 * @return
	 */
	public String[] getAllCollumns(List<?> tablelist) {
		int ii = 0;
		ii = tablelist.size();
		if (ii <= 0)
			return null;
		JSONArray jsarry = JSONArray.fromObject(tablelist);
		JSONObject jobject = jsarry.getJSONObject(0); // 取第一行
		Iterator<?> it = jobject.keys();

		List<String> li = new ArrayList<String>();
		while (it.hasNext()) {

			String key = it.next().toString();
			li.add(key);
		}
		String[] storeStr = (String[]) li.toArray(new String[li.size()]);
		return storeStr;
	}
	/*
	 * 执行 长文本插入
	 * 
	 * @param tsql
	 * 
	 * @return
	 */
	public void exec_sqll(String tsql, String DB,String maincontent) throws SQLException
	{
		connDB(DB);
		try {
		PreparedStatement state=con.prepareStatement(tsql);
		state.setString(1,maincontent);
		state.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	/*
	 * 执行 update,insert,delete
	 * 
	 * 只有执行insert时才返回唯一主键
	 * id  主键的名称
	 * sqlType 执行种类 0：insert ，1：update，2：delete
	 * @param tsql
	 * 
	 * @return
	 */
	public Map<String,Object> return_exec_sql_key(String sqlType ,String id,String tsql, String DB) {
		Map<String, Object> result = new HashMap<String, Object>();
		connDB(DB);
		PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String generatedColumns[] = { id };  //获得指定ID
            pst = con.prepareStatement(tsql, generatedColumns); // 指定返回生成的主键
            pst.executeUpdate();
            if(sqlType!=null&&sqlType.equals("0")){
                // 检索对象生成的键
                rs = pst.getGeneratedKeys();
                if (rs.next()) {
                	result.put("ID", rs.getString(1));
                }
            }
			result.put("zt", "ok");
			result.put("zt_cause", "成功");
        } catch (SQLException e) {
            e.printStackTrace();
			result.put("zt", "error");
			result.put("zt_cause", e.getMessage());
        }finally {
        	if(rs!=null){
            	try {
            		rs.close();
    			} catch (SQLException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
        	}
        	if(pst!=null){
            	try {
    				pst.close();
    			} catch (SQLException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
        	}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return result;
		}
	}
	
	
	/*
	 * 执行 update,insert,delete
	 * 
	 * @param tsql
	 * 
	 * @return
	 */
	public void exec_sql(String tsql, String DB) {
		connDB(DB);
		try {
			Statement s = con.createStatement();
			s.execute(tsql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	/*
	 * 执行 update,insert,delete
	 * 
	 * @param tsql
	 * 
	 * @return 返回返回执行结果状态
	 */
	public Map<String,Object> returnWrong_exec_sql(String tsql, String DB) {
		Map<String, Object> result = new HashMap<String, Object>();
		connDB(DB);
		try {
			Statement s = con.createStatement();
			s.execute(tsql);
			result.put("zt", "ok");
			result.put("zt_cause", "成功");
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("zt", "error");
			result.put("zt_cause", e.getMessage());
			return result;
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
//	/**
//	 * 获取list的X行X列的数据，从0开始
//	 * 
//	 * @param tablelist
//	 *            ,row,col,如：get_xy_FromJSONArray(jsarry, i, 0).toString()
//	 *            JSONArray jsarry = JSONArray.fromObject(tablelist);
//	 * @return string
//	 */
//	public String get_xy_FromJSONArray(JSONArray jsarry, int row, int col) {	
//		String cols[] = getAllCollumns(tablelist);
//
//		return jsarry.getJSONObject(row).get(cols[col]).toString();
//	}
	
	/**最好不用，性能不好
	 * 获取list的X行X列的数据，从0开始
	 * 
	 * @param tablelist
	 *            ,row,col,如：get_xy_FromList(list, i, 0).toString()
	 * @return string
	 */
	public String get_xy_FromList(List<?> tablelist, int row, int col) {
		JSONArray jsarry = JSONArray.fromObject(tablelist);
		String cols[] = getAllCollumns(tablelist);

		return jsarry.getJSONObject(row).get(cols[col]).toString();
	}

	/**最好不用，性能不好
	 * 获取list的X行和列的名称获取数据，从行0开始，列名用大写字母
	 * 
	 * @param tablelist
	 *            ,row,col，如：get_xy_FromList(list, i,"YEARM").toString()
	 * @return String
	 */
	public String get_xy_FromListByName(List<?> tablelist, int row, String col) {
		JSONArray jsarry = JSONArray.fromObject(tablelist);
		try {
			if (jsarry.getJSONObject(row).get(col).toString() == null) {
				return "";
			} else {
				return jsarry.getJSONObject(row).get(col).toString();
			}
		} catch (NullPointerException e) {
			return "";
		}
	}
	
	/**
	 * 获取JSONArray的X行和列的名称获取数据，从行0开始，列名用大写字母(这样可以提高读取速度)==推荐使用
	 * 
	 * @param tablelist
	 *            ,row,col，如：
	 *            JSONArray jsarry = JSONArray.fromObject(tablelist);
	 *            get_xy_FromJsonArrayByName(JSONArray, i,"YEARM").toString()
	 * @return String
	 */
	public String get_xy_FromJsonArrayByName(JSONArray jsarry, int row, String col) {
		try {
			if (jsarry.getJSONObject(row).get(col).toString() == null) {
				return "";
			} else {
				return jsarry.getJSONObject(row).get(col).toString();
			}
		} catch (NullPointerException e) {
			return "";
		}
	}

	/**
	 * 获取list表中的列数
	 * 
	 * @param tablelist
	 *            ,
	 * @return int
	 */
	public int getColNum(List<?> tablelist) {
		String cols[] = getAllCollumns(tablelist);
		return cols.length;

	}

	/**
	 * 获取SQL语句
	 * 
	 * @param SQL_ID
	 *            ，Constant.CONN_DB218
	 * @return SQLString
	 */
	public String getSQLStringFromDB(int SQL_ID, String DB) throws SQLException {
		String TSQL = "select * from T_EXECUTE_SQL where sql_id=" + SQL_ID;
		List<?> list = this.getTableFromSQL(TSQL, DB);
		if (list.size() > 0) {
			String rtnSQL = this.get_xy_FromListByName(list, 0, "SQL_TEXT");
			return rtnSQL;
		} else
			return "";

	}
	/****
	 * 有Clob类型字段  clobNum为clob的位置和sql语句中相对应
	 * 
	 * **/
	public List<?> exec_sqlClob(String TSQL, String DB,String ClobStr, int clobNum) throws SQLException {
		connDB(DB);
	    PreparedStatement stmt = con.prepareStatement(TSQL);
	     // 将clob转成流形式
	    Reader clobReader = new StringReader(ClobStr);
		try {
		    stmt.setCharacterStream(clobNum,clobReader,ClobStr.length());// 替换sql语句中的？
		    stmt.executeUpdate(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    try {
				clobReader.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    stmt.close();
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return rtnList;
	}
	/****
	 * Clob转为String clobNum为clob的位置和sql语句中相对应
	 * 
	 * **/
	public List<?> getTableFromSQLClob(String TSQL, String DB, int clobNum) throws SQLException {

		try {
			connDB(DB);
			pre = con.prepareStatement(TSQL);
			result = pre.executeQuery();
			rtnList = resultSetToListClob(result,clobNum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtnList;
	}
	//clob转为string
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map> resultSetToListClob(ResultSet rs,int clobNum)
			throws java.sql.SQLException, IOException {
		if (rs == null)
			return (List<Map>) Collections.EMPTY_LIST;
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		List<Map> list = (List<Map>) new ArrayList();
		Map<String, Object> rowData = new HashMap<String, Object>();
		while (rs.next()) {
			rowData = new HashMap<String, Object>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				//rowData.put(md.getColumnName(i), rs.getObject(i));
				if (rs.getObject(i) != null) {
					if(i!=clobNum){
					rowData.put(md.getColumnName(i), rs.getObject(i));
					}else{
					rowData.put(md.getColumnName(i), ClobToString(rs.getClob(clobNum)));
					}
				} else {
					rowData.put(md.getColumnName(i), "");
				}
			}
			list.add(rowData);
		}
		return list;
	}
	// 将字CLOB转成STRING类型   
    public String ClobToString(Clob clob) throws SQLException, IOException {   
          
        String reString = "";   
        java.io.Reader is = clob.getCharacterStream();// 得到流   
        BufferedReader br = new BufferedReader(is);   
        String s = br.readLine();   
        StringBuffer sb = new StringBuffer();   
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING   
            sb.append(s);   
            s = br.readLine();   
        }   
        reString = sb.toString();   
        return reString;   
    }  
    public   List<?> getTableFromSQLReturnException(String TSQL, String DB,Map<String, Object> map) throws SQLException {
		try {
			connDB(DB);
			pre = con.prepareStatement(TSQL);
			result = pre.executeQuery();
			rtnList = resultSetToList(result);
			map.put("zt", "ok");
			map.put("zt_cause", "成功");
		} catch (Exception e) {
			map.put("zt", "error");
			map.put("zt_cause", e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtnList;
	}
}
