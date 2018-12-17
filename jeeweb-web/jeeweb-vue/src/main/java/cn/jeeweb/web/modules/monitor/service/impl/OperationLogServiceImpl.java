package cn.jeeweb.web.modules.monitor.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.modules.monitor.entity.OperationLog;
import cn.jeeweb.web.modules.monitor.mapper.OperationLogMapper;
import cn.jeeweb.web.modules.monitor.service.IOperationLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.web.modules.sys.service.impl
* @title: 操作日志服务实现
* @description: 操作日志服务实现
* @author: 王存见
* @date: 2018-09-30 15:53:25
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("operationlogService")
public class OperationLogServiceImpl  extends CommonServiceImpl<OperationLogMapper,OperationLog> implements IOperationLogService {

}