package com.gdplabs.temporaliodemo.simpleapproval.service;

import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ApprovalService {

    private static String ID_PREFIX = "Approval_";

    @Autowired
    private WorkflowClient workflowClient;

    @Autowired
    private WorkflowServiceStubs workflowServiceStubs;

    public void registerApprovalWorkflow(String workflowId, List<String> approvers) {
        ApprovalWorkflow workflow = createWorkflowConnection(workflowId);
        WorkflowClient.start(workflow::startWorkflow, approvers);
    }

    public void approve(String workflowId, String approver) {
        ApprovalWorkflow workflow = workflowClient.newWorkflowStub(ApprovalWorkflow.class, ID_PREFIX + workflowId);
        workflow.approve(approver, ID_PREFIX + workflowId);
    }

    public void deleteApprover(String workflowId, String approver) {
        ApprovalWorkflow workflow = workflowClient.newWorkflowStub(ApprovalWorkflow.class, ID_PREFIX + workflowId);
        workflow.deleteApprover(approver, workflowId);
    }

    public void addApprover(String workflowId, String approver) {
        ApprovalWorkflow workflow = workflowClient.newWorkflowStub(ApprovalWorkflow.class, ID_PREFIX + workflowId);
        workflow.addApprover(approver, workflowId);
    }

    public Map<String, Boolean> getStatus(String workflowId) {
        ApprovalWorkflow workflow = workflowClient.newWorkflowStub(ApprovalWorkflow.class, ID_PREFIX + workflowId);
        return workflow.getStatus();
    }

    private ApprovalWorkflow createWorkflowConnection(String workflowId) {
        WorkflowOptions options = WorkflowOptions
                .newBuilder()
                .setWorkflowId(ID_PREFIX + workflowId)
                .setTaskQueue(ApprovalWorkflow.QUEUE_NAME)
                .build();
        return workflowClient.newWorkflowStub(ApprovalWorkflow.class, options);
    }
}
