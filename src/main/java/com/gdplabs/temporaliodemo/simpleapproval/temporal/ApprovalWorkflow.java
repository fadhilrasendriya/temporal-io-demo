package com.gdplabs.temporaliodemo.simpleapproval.temporal;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.util.List;
import java.util.Map;

@WorkflowInterface
public interface ApprovalWorkflow {

    String QUEUE_NAME = "Approval";

    @WorkflowMethod
    void startWorkflow(List<String> approvers);

    @SignalMethod
    void approve(String approver, String taskId);

    @SignalMethod
    void validate();

    @SignalMethod
    void deleteApprover(String approver, String workflowId);

    @SignalMethod
    void addApprover(String approver, String workflowId);

    @QueryMethod
    Map<String, Boolean> getStatus();

}
