package com.gdplabs.temporaliodemo.simpleapproval.temporal;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApprovalWorkflowImpl implements ApprovalWorkflow{

    private Map<String, Boolean> approvalStatus;

    private int totalApproved;
    private int approvalsNeeded;

    private final RetryOptions retryoptions = RetryOptions.newBuilder().setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100)).setBackoffCoefficient(2).setMaximumAttempts(50000).build();
    private final ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(retryoptions).build();

    private final ApprovalActivities activities = Workflow.newActivityStub(ApprovalActivities.class, options);

    @Override
    public void startWorkflow(List<String> approvers) {
        this.approvalStatus = new HashMap<>();
        for (String approver:
             approvers) {
            this.approvalStatus.put(approver, false);
        }

        this.totalApproved = 0;
        this.approvalsNeeded = approvers.size();
        Workflow.await(() -> this.totalApproved == this.approvalsNeeded);

    }

    @Override
    public void approve(String approver, String taskId) {
        if(!this.approvalStatus.get(approver)) {
            activities.approve(approver, taskId);
            this.approvalStatus.put(approver, true);
            this.totalApproved++;
        }
    }

    @Override
    public void validate() {
        int approved = 0;
        for (Map.Entry<String, Boolean> entry:
             approvalStatus.entrySet()) {
            if(entry.getValue()) {
                approved++;
            }
        }
        this.totalApproved = approved;
    }

    @Override
    public void deleteApprover(String approver, String workflowId) {
        Boolean status = approvalStatus.get(approver);
        if(status == null) {
            return;
        }
        if(status) {
            totalApproved--;
        }
        approvalsNeeded--;
        approvalStatus.remove(approver);
    }

    @Override
    public void addApprover(String approver, String workflowId) {
        approvalStatus.put(approver, false);
        approvalsNeeded++;
    }

    @Override
    public Map<String, Boolean> getStatus() {
        return approvalStatus;
    }
}
