package cn.jeeweb.web.modules.kaifasamples.controller;

import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.common.constant.otherDBConstant;
import cn.jeeweb.web.common.operateDB.OracleHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xianshun.huang on 2018/12/10.
 */

//注意映射返回的页面的路径必须是/绝对路径,否则会把类的RequestMapping作为路径，如return new ModelAndView("/modules/DataTables_1/datatables_list");
@RestController
@RequestMapping("DataTables_1")
public class DataTables_1 {
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
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
    public Map<String, Object> getshuj_from_oracle() throws SQLException {
        OracleHelper oh=new OracleHelper();
        List all_list=(List)oh.getTableFromSQL("select APPROVAL_PERSON,REMARK,APPROVAL_DATE from TT_VEHICLE_CHECKAUTHITEM", otherDBConstant.CONN_DMS);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data",all_list);
        return  result;
    }
}
