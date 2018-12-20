package cn.jeeweb.tag.test;

import com.alibaba.fastjson.JSON;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.junit.Test;

import java.util.Map;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.tag.test
 * @title:
 * @description: Cache工具类
 * @author: 王存见
 * @date: 2018/8/10 11:27
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class TestFragment {
    public static void main(String[] args) {
        String json = "{\"showQueryLabel\":true,\"gridtype\":\"jqgrid\",\"appPath\":\"\",\"queryList\":[],\"toobarList\":[],\"datatype\":\"json\",\"ajaxType\":\"get\",\"rowNum\":10,\"staticPath\":\"/static\",\"sortorder\":\"asc\",\"id\":\"codegenGrid\",\"editurl\":\"clientArray\",\"treeGrid\":false,\"adminPath\":\"\",\"height\":\"450\",\"ctx\":\"\",\"editable\":false,\"columnList\":[],\"gridShowType\":\"list\",\"pageable\":true,\"buttonList\":[],\"sortable\":true,\"url\":\"/generator/table/ajaxList?gridtype=jqgrid\",\"columnDictMap\":{},\"multiselect\":true,\"sortname\":\"id\",\"async\":false,\"baseUrl\":\"/generator/table\",\"width\":\"auto\",\"multiSort\":true,\"page\":1}";
        Map<String,Object> dataMap = JSON.parseObject(json,Map.class);
        try {
            ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("samples");
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate("testfragment.txt");
            t.binding(dataMap);
            String str = t.render();
            System.out.println(str);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Test
    //classpath:注意是在src目录下的resources目录下classpath才起效果
    public  void tet()
    {
        String json = "{\"showQueryLabel\":true,\"gridtype\":\"jqgrid\",\"appPath\":\"\",\"queryList\":[],\"toobarList\":[],\"datatype\":\"json\",\"ajaxType\":\"get\",\"rowNum\":10,\"staticPath\":\"/static\",\"sortorder\":\"asc\",\"id\":\"codegenGrid\",\"editurl\":\"clientArray\",\"treeGrid\":false,\"adminPath\":\"\",\"height\":\"450\",\"ctx\":\"\",\"editable\":false,\"columnList\":[],\"gridShowType\":\"list\",\"pageable\":true,\"buttonList\":[],\"sortable\":true,\"url\":\"/generator/table/ajaxList?gridtype=jqgrid\",\"columnDictMap\":{},\"multiselect\":true,\"sortname\":\"id\",\"async\":false,\"baseUrl\":\"/generator/table\",\"width\":\"auto\",\"multiSort\":true,\"page\":1}";
        Map<String,Object> dataMap = JSON.parseObject(json,Map.class);
        try {
            ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("samples");//resources下目录
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate("testfragment.txt");
            t.binding(dataMap);
            String str = t.render();
            System.out.println(str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    //直接路径
    public void tet2()
    {
        String json = "{\"showQueryLabel\":true,\"gridtype\":\"jqgrid\",\"appPath\":\"\",\"queryList\":[],\"toobarList\":[],\"datatype\":\"json\",\"ajaxType\":\"get\",\"rowNum\":10,\"staticPath\":\"/static\",\"sortorder\":\"asc\",\"id\":\"codegenGrid\",\"editurl\":\"clientArray\",\"treeGrid\":false,\"adminPath\":\"\",\"height\":\"450\",\"ctx\":\"\",\"editable\":false,\"columnList\":[],\"gridShowType\":\"list\",\"pageable\":true,\"buttonList\":[],\"sortable\":true,\"url\":\"/generator/table/ajaxList?gridtype=jqgrid\",\"columnDictMap\":{},\"multiselect\":true,\"sortname\":\"id\",\"async\":false,\"baseUrl\":\"/generator/table\",\"width\":\"auto\",\"multiSort\":true,\"page\":1}";
        Map<String,Object> dataMap = JSON.parseObject(json,Map.class);

         try {
             String root = "D:\\git_s\\jeeweb\\jeeweb-ui\\jeeweb-ui-tag\\src\\test\\java\\resources\\sample";
             FileResourceLoader resourceLoader = new FileResourceLoader(root,"utf-8");
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



