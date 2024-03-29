package cn.jeeweb.bbs.modules.posts.controller;

import cn.jeeweb.bbs.common.bean.ResponseError;
import cn.jeeweb.bbs.modules.posts.service.IPostsCommentService;
import cn.jeeweb.bbs.modules.posts.entity.PostsComment;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.bbs.controller
 * @title: 评论控制器
 * @description: 评论控制器
 * @author: 王存见
 * @date: 2018-08-29 17:51:13
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/comment")
public class PostsCommentController extends BaseBeanController<PostsComment> {

    @Autowired
    private IPostsCommentService postsCommentService;


    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        return display("list");
    }

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "id=desc")
    private void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        EntityWrapper<PostsComment> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<PostsComment> pagejson = new PageResponse<>(postsCommentService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

    @PostMapping("add")
    public Response add(PostsComment entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        return doSave(entity, request, response, result);
    }

    @PostMapping("{id}/update")
    public Response update(PostsComment entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        return doSave(entity, request, response, result);
    }

    @PostMapping("/save")
    public Response doSave(PostsComment entity, HttpServletRequest request, HttpServletResponse response,
                           BindingResult result) {
        if (hasError(entity, result)) {
            // 错误提示
            String errorMsg = errorMsg(result);
            if (!StringUtils.isEmpty(errorMsg)) {
                return Response.error(ResponseError.NORMAL_ERROR,errorMsg);
            } else {
                return Response.error(ResponseError.NORMAL_ERROR,"保存失败");
            }
        }
        try {
            if (StringUtils.isEmpty(entity.getId())) {
                entity.setDefault();
                postsCommentService.insert(entity);
            } else {
                postsCommentService.insertOrUpdate(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"保存失败!<br />原因:" + e.getMessage());
        }
        return Response.ok("保存成功");
    }

    @GetMapping("{id}/delete")
    public Response delete(@PathVariable("id") String id) {
        try {
            postsCommentService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"删除失败");
        }
        return Response.ok("删除成功");
    }


}