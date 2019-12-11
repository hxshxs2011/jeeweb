package cn.jeeweb.web.modules.kaifasamples.controller;

import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseController;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.common.constant.otherDBConstant;
import cn.jeeweb.web.common.operateDB.OracleHelper;
import cn.jeeweb.web.modules.sys.entity.Organization;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by xianshun.huang on 2018/12/10.
 */

//注意映射返回的页面的路径必须是/绝对路径,否则会把类的RequestMapping作为路径，如return new ModelAndView("/modules/DataTables_1/datatables_list");
@RestController
@RequestMapping("DataTables_1")
@Log(title = "获取oralce数据库")
public class DataTables_1 extends BaseController {
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response)  {
        //System.out.println("okok1223657");
        request.setAttribute("ctitle","显示标题");
        return new ModelAndView("/modules/DataTables_1/datatables_list");
    }

    @RequestMapping("getshuj")
    public Map<String, Object> getshuj(){
        List< Map<String, String>> list=new ArrayList<>();
        for(int i=0;i<=99;i++) {
            Map<String, String> map = new HashMap<>();
            map.put("APPROVAL_PERSON", "菜单-"+Integer.toString(i));
            map.put("REMARK", "用户名-"+Integer.toString(i));
            map.put("APPROVAL_DATE", "时间-"+Integer.toString(i));
            list.add(map);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data",list);
        return  result;
    }

    @RequestMapping("getshuj_from_oracle")
    @Log(logType = LogType.SELECT)
    public void getshuj_from_oracle(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int draw = Integer.valueOf(request.getParameter("draw"));//第页数
        int start = Integer.valueOf(request.getParameter("start"));// 每页起始位置
        int length = Integer.valueOf(request.getParameter("length"));// 每页显示条数
        int start_add_length = start + length;// 起始+每页长度
        int totalLength = 0;// 计算总条数
        int sorted_xh = 0;//
        sorted_xh = Integer.parseInt(request.getParameter("order[0][column]"));
        // "columns[" +sorted_xh+"][data]"
        String sortedObject = request.getParameter("columns[" + sorted_xh + "][data]").toString();
        String sortedValue = request.getParameter("order[0][dir]");
        String order_by_str = " order by " + sortedObject + " " + sortedValue;
        String search_str = request.getParameter("search[value]").toString();
        String individualSearch = "";
        if (search_str.trim().length() > 0) {

            // 获取用户过滤框里的字符
            List<String> sArray = new ArrayList<String>();
            sArray.add(" XM_NAME like '%" + search_str + "%'");
            sArray.add(" XM_B_DATE like '%" + search_str + "%'");
            sArray.add(" XM_HYZKSJ like '%" + search_str + "%'");

            if (sArray.size() > 0) {
                for (int i = 0; i < sArray.size() - 1; i++) {
                    individualSearch += sArray.get(i) + " or ";
                }
                individualSearch += sArray.get(sArray.size() - 1);
            }
            individualSearch = " and " + individualSearch;
        }
        //
        String sqlstr_for_download = "select * from XM_MANAGE where 1=1 " + individualSearch + order_by_str;
        HttpSession session = request.getSession();
        session.setAttribute("XM_MANAGE", sqlstr_for_download);// 定义下载的SQL语句；

        OracleHelper oh = new OracleHelper();
        List zong_count = (List) oh.getTableFromSQL("select count(*) co from XM_MANAGE where 1=1 " + individualSearch, otherDBConstant.CONN_118);
        totalLength = Integer.parseInt(oh.get_xy_fromList(zong_count, 0, "co").toString());

        String tsql = "select * from (select rownum as rowno,t.*  from ( select XM_NAME,XM_B_DATE,XM_HYZKSJ from XM_MANAGE where 1=1  " + individualSearch + order_by_str + ") t where rownum <= " + start_add_length + ") tt where rowno>" + start;

        List all_list = (List) oh.getTableFromSQL(tsql, otherDBConstant.CONN_118);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("draw", draw);
        result.put("recordsTotal", totalLength);//
        result.put("recordsFiltered", totalLength);//
        result.put("data", all_list);
        responseOutWithJson(response,result);

    }

    //@GetMapping(value = "add")
    @RequestMapping("new")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        //model.addAttribute("data", new Organization());
        return new ModelAndView("/modules/DataTables_1/new");
        //return displayModelAndView ("edit");
    }

    @RequestMapping("add_s")
    public Response  add_s(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<String,Object>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length >0) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

        Set<Map.Entry<String, Object>> set = map.entrySet();
        System.out.println("==============================================================");
        for (Map.Entry entry : set) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("=============================================================");
         return  Response.ok("保存成功！！");
    }
}





