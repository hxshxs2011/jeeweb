package publicTest;

import cn.jeeweb.web.common.constant.otherDBConstant;
import cn.jeeweb.web.common.operateDB.OracleHelper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by xianshun.huang on 2018/12/11.
 */

public class ptest {

        @Test
        public void getshuj(){
            List< Map<String, String>> list=new ArrayList<>();

            for(int i=0;i<=9;i++) {
                //map.clear();
                Map<String, String> map = new HashMap<>();
                map.put("MENUNAME", "MENUNAME-"+Integer.toString(i));
                map.put("USERNAME", "USERNAME-"+Integer.toString(i));
                map.put("TTIME", "TTIME-"+Integer.toString(i));
                list.add(map);
            }
           System.out.println(list);

        }

        @Test
        public void testconDB() throws SQLException {
            OracleHelper oh=new OracleHelper();
            List all_list=(List)oh.getTableFromSQL("select APPROVAL_PERSON,REMARK,APPROVAL_DATE from TT_VEHICLE_CHECKAUTHITEM", otherDBConstant.CONN_DMS);

            System.out.println("[20,APPROVAL_PERSON]="+((Map)all_list.get(20)).get("APPROVAL_PERSON"));
            for(int i=0;i<=all_list.size()-1;i++) {
                //all_list.get(i).get("APPROVAL_PERSON");
                System.out.println(((Map)all_list.get(i)).get("APPROVAL_PERSON"));
                System.out.println(((Map)all_list.get(i)).get("APPROVAL_DATE"));
                System.out.println(((Map)all_list.get(i)).get("REMARK"));
                System.out.println("=============================");
            }
            //System.out.println(all_list);
        }
        @Test
        public  void getConnXML() throws DocumentException {

            // File file = new File(System.getProperty("user.dir")
            // + "/resources/connDB.xml");

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

        }
}
