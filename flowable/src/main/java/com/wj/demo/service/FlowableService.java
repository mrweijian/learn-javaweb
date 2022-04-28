package com.wj.demo.service;

import com.wj.demo.domain.FlowableDomain;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FlowableService
 * @Author weijian
 * @Date 2022/3/24
 */
public interface FlowableService {

    /**
     * 流程启动创建流程实例
     *
     * @return
     */
    Map<String, String> startLeaveProcess(FlowableDomain domain);

    /**
     * 获取任务名称
     *
     * @param assignee
     * @return
     */
    List<String> getTasks(String assignee);

    /**
     * 执行流程节点
     *
     * @param domain
     * @return
     */
    String applyTask(FlowableDomain domain);

    /**
     * 获取执行任务
     *
     * @param processId
     * @return
     */
    List<Map<String, String>> getHistory(String processId);

    /**
     * 获取流程所有节点
     *
     * @param proDefId
     * @return
     */
    Map<String, Object> getAllNode(String proDefId);

    /**
     * 上传model
     *
     * @param file
     */
    String uploadFile(MultipartFile file);

    /**
     * 生成流程图
     *
     * @param httpServletResponse
     * @param processId           流程实例id
     * @throws Exception
     */
    void createProcessDiagramPic(HttpServletResponse httpServletResponse, String processId);
}
