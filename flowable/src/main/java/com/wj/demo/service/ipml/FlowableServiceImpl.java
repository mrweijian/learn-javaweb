package com.wj.demo.service.ipml;

import com.alibaba.fastjson.JSONArray;
import com.wj.demo.domain.FlowableDomain;
import com.wj.demo.service.FlowableService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FlowableServiceImpl
 * @Author weijian
 * @Date 2022/3/24
 */

@Service
public class FlowableServiceImpl implements FlowableService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;

    @Resource
    private ProcessEngine processEngine;


    /**
     * 流程启动创建流程实例
     *
     * @return
     */
    @Override
    public Map<String, String> startLeaveProcess(FlowableDomain domain) {
        Map<String, String> result = new HashMap<>();
        ProcessInstance processInstance;
        try {
            if (domain.getVariables() == null) {
                processInstance = runtimeService.startProcessInstanceByKey(domain.getParam(), domain.getVariables());
            } else {
                processInstance = runtimeService.startProcessInstanceByKey(domain.getParam());
            }

            result.put("流程实例id: ", processInstance.getId());
            result.put("流程id: ", processInstance.getProcessDefinitionId());
        } catch (Exception e) {
            result.put("未知错误", e.getMessage());
        }
        return result;
    }

    /**
     * 获取任务名称
     *
     * @param assignee
     * @return
     */
    @Override
    public List<String> getTasks(String assignee) {
        List<String> taskIds = new ArrayList<>();
        try {
            List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).orderByTaskCreateTime().desc().list();
            for (Task task : tasks) {
                taskIds.add(task.getAssignee() + ": " + task.getId());
            }
        } catch (Exception e) {
            taskIds.add("未知错误，错误信息-----" + e.getMessage());
        }
        return taskIds;
    }

    /**
     * 执行流程节点
     *
     * @param domain
     * @return
     */
    @Override
    public String applyTask(FlowableDomain domain) {
        Task task = taskService.createTaskQuery().taskId(domain.getParam()).singleResult();
        if (task == null) {
            return "流程不存在";
        }
        taskService.complete(domain.getParam(), domain.getVariables() == null ? new HashMap<>() : domain.getVariables());
        return "当前节点执行成功！";
    }

    /**
     * 获取执行任务
     *
     * @param processId
     * @return
     */
    @Override
    public List<Map<String, String>> getHistory(String processId) {

        List<HistoricActivityInstance> activities =
                historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processId)
                        .finished()
                        .orderByHistoricActivityInstanceEndTime().asc()
                        .list();

        List resultList = new ArrayList();
        for (HistoricActivityInstance activity : activities) {
            Map<String, String> map = new HashMap<>();
            map.put("ActivityId", activity.getActivityId());
            map.put("ActivityName", activity.getActivityName());
            map.put("ActivityType", activity.getActivityType());
            map.put("消耗时间", activity.getDurationInMillis().toString());
            map.put("开始时间", activity.getStartTime().toString());
            map.put("结束时间", activity.getEndTime().toString());
            resultList.add(map);
        }

        return resultList;
    }

    /**
     * 获取流程所有节点
     *
     * @param proDefId
     * @return
     */
    @Override
    public Map<String, Object> getAllNode(String proDefId) {
        Map<String, Object> resultMap = new HashMap<>();
        JSONArray dataArray = new JSONArray();
        try {
            //流程定义id
            BpmnModel bpmnModel = repositoryService.getBpmnModel(proDefId);
            Process process = bpmnModel.getProcesses().get(0);
            //获取所有节点
            Collection<FlowElement> flowElements = process.getFlowElements();

            for (FlowElement f : flowElements) {
                Map<String, Object> map = new HashMap<>();
                if (f instanceof UserTask) {
                    UserTask userTask = (UserTask) f;
                    Map<String, List<ExtensionAttribute>> extMap = userTask.getAttributes();
                    for (String extMapKey : extMap.keySet()) {
                        ExtensionAttribute extensionAttribute = extMap.get(extMapKey).get(0);
                        if (extensionAttribute.getValue().equals("true")) {
                            map.put(extensionAttribute.getName(), true);
                        } else if (extensionAttribute.getValue().equals("false")) {
                            map.put(extensionAttribute.getName(), false);
                        } else {
                            map.put(extensionAttribute.getName(), extensionAttribute.getValue());
                        }
                    }
                    map.put("nodeID", f.getId());
                    map.put("proDefKey", process.getId());
                    map.put("nodeName", f.getName());
                    dataArray.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", -1);
            resultMap.put("msg", "获取节点失败！" + e.getMessage());
            return resultMap;
        }
        resultMap.put("code", 0);
        resultMap.put("msg", "流程节点获取成功！");
        resultMap.put("data", dataArray);
        return resultMap;
    }

    /**
     * 上传model
     *
     * @param file
     */
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Deployment deployment = repositoryService.createDeployment().addInputStream(file.getOriginalFilename(), file.getInputStream()).deploy();
            return "上传成功:" + deployment.getId();
        } catch (Exception e) {
            return "上传失败" + e.getMessage();
        }

    }

    /**
     * 生成流程图
     *
     * @param httpServletResponse
     * @param processId           流程实例id
     * @throws Exception
     */
    @Override
    public void createProcessDiagramPic(HttpServletResponse httpServletResponse, String processId) {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        /**
         * 生成流程图
         */
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        byte[] buf = new byte[1024];
        int legth = 0;
        try (OutputStream out = httpServletResponse.getOutputStream();
             InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, true);
        ) {

            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } catch (Exception e) {
            return;
        }
    }

}
