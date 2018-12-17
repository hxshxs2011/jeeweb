package cn.jeeweb.web.common.operateDB;

import java.io.File;
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

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SQLHelper {
	Connection con = null;
	PreparedStatement pre = null;
	ResultSet result = null;
	static List<?> rtnList = null;

	public  Connection getConn(String DB) {
	  connDB(DB);
      return con;
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
	private void connDB(String DB) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
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
		//String user = map2.get("user").toString();
		//String password = map2.get("password").toString();
		try {
			con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Map<String, Object> getConnXML() throws DocumentException {

		// File file = new File(System.getProperty("user.dir")
		// + "/resources/connDB.xml");

		String pathstr = OracleHelper.class.getClassLoader()
				.getResource("connDB.xml").getPath();
		// Logger log=Logger.getLogger(getClass());
		// log.info(pathstr);
		// System.out.println(pathstr);
		File file = new File(pathstr);
		SAXReader reader = new SAXReader();
		Document doc = reader.read(file);
		Element root = doc.getRootElement();
		Element foo;

		Map<String, Object> map = new HashMap<String, Object>();

		for (Iterator<?> i = root.elementIterator("dbname"); i.hasNext();) {
			foo = (Element) i.next();
			String name = foo.attributeValue("name");
			Map<String, String> childmap = new HashMap<String, String>();
			childmap.put("url", foo.attributeValue("url"));
			//childmap.put("user", foo.attributeValue("user"));
			//childmap.put("password", foo.attributeValue("password"));
			map.put(name, childmap);

			// list.add(map);
		}

		// System.out.println(((Map<String,
		// String>)map.get("jrmes")).get("url"));

		return map;

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

}
