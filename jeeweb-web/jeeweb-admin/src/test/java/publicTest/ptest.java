package publicTest;

import cn.jeeweb.web.common.constant.otherDBConstant;
import cn.jeeweb.web.common.operateDB.OracleHelper;
import com.alibaba.fastjson.JSON;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

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
    @Test
    //直接路径
    public void tet2()
    {
        String json = "{\"showQueryLabel\":true,\"gridtype\":\"jqgrid\",\"appPath\":\"\",\"queryList\":[],\"toobarList\":[],\"datatype\":\"json\",\"ajaxType\":\"get\",\"rowNum\":10,\"staticPath\":\"/static\",\"sortorder\":\"asc\",\"id\":\"codegenGrid\",\"editurl\":\"clientArray\",\"treeGrid\":false,\"adminPath\":\"\",\"height\":\"450\",\"ctx\":\"\",\"editable\":false,\"columnList\":[],\"gridShowType\":\"list\",\"pageable\":true,\"buttonList\":[],\"sortable\":true,\"url\":\"/generator/table/ajaxList?gridtype=jqgrid\",\"columnDictMap\":{},\"multiselect\":true,\"sortname\":\"id\",\"async\":false,\"baseUrl\":\"/generator/table\",\"width\":\"auto\",\"multiSort\":true,\"page\":1}";
        Map<String,Object> dataMap = JSON.parseObject(json,Map.class);
        try {
            ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate("samples/testfragment.txt");
            t.binding(dataMap);
            String str = t.render();
            System.out.println(str);
        }catch (Exception e){
            e.printStackTrace();
        }


    }



}
