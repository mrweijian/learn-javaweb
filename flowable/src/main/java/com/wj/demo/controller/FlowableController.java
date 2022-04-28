package com.wj.demo.controller;

import com.wj.demo.domain.FlowableDomain;
import com.wj.demo.service.FlowableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MyRestController
 * @Author weijian
 * @Date 2022/3/14
 */

@RestController
@RequestMapping("/flow")
public class FlowableController {

    @Autowired
    private FlowableService flowableService;

    /**
     * 流程启动创建流程实例
     *
     * @author weijian
     * @description 启动流程
     */
    @PostMapping(value = "/startProcess")
    public Map startLeaveProcess(@RequestBody FlowableDomain domain) {
        return flowableService.startLeaveProcess(domain);
    }

    /**
     * 获取任务列表
     *
     * @param assignee 流程图中定义的assignee
     * @return
     */
    @GetMapping(value = "/getTasks")
    public List<String> getTasks(@RequestParam String assignee) {
        return flowableService.getTasks(assignee);
    }

    /**
     * 执行流程节点
     *
     * @param domain
     * @return
     */
    @PostMapping(value = "/applyTask")
    public String applyTask(@RequestBody FlowableDomain domain) {
        return flowableService.applyTask(domain);
    }


    /**
     * 流程历史信息
     *
     * @param processId 流程实例节点
     * @return
     */
    @RequestMapping(value = "/getHistory")
    public List<Map<String, String>> getHistory(@RequestParam String processId) {
        return flowableService.getHistory(processId);

    }

    /**
     * 获取所有节点
     *
     * @param proDefId 流程自身id
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/getAllNode")
    public Map<String, Object> getAllNode(@RequestParam String proDefId) {
        return flowableService.getAllNode(proDefId);
    }

    /**
     * 上传model
     *
     * @param file
     */
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return flowableService.uploadFile(file);

    }

    /**
     * 修改model
     *
     * @param file
     */
    @PostMapping("/editorFile")
    public void editorFile(@RequestParam("file") MultipartFile file) {
//        repositoryService.addModelEditorSource();
    }


    /**
     * 生成流程图
     *
     * @param httpServletResponse
     * @param processId           流程实例id
     * @throws Exception
     */
    @GetMapping(value = "/createProcessDiagramPic")
    public void createProcessDiagramPic(HttpServletResponse httpServletResponse, String processId) {
        flowableService.createProcessDiagramPic(httpServletResponse, processId);
    }
}
