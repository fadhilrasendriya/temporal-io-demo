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

    @QueryMethod
    Map<String, Boolean> getStatus();

}
