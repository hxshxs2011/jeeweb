package cn.jeeweb.web.modules.kaifasamples.controller;

import cn.jeeweb.web.common.constant.otherDBConstant;
import cn.jeeweb.web.common.operateDB.OracleHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xianshun.huang on 2018/12/12.
 */
@RestController
@RequestMapping("jqGrid")
public class jqGrid {
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        //System.out.println("okok1223657");
        request.setAttribute("ctitle","jqGrid显示标题");
        return new ModelAndView("/modules/jqGrid/jqGrid_list");
    }


    @RequestMapping("getshuj_from_oracle")
    public Map<String, Object> getshuj_from_oracle() throws SQLException {
        OracleHelper oh=new OracleHelper();
        List all_list=(List)oh.getTableFromSQL("select * from USERORACLE", otherDBConstant.CONN_118);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows",all_list);
        return  result;
    }
}
