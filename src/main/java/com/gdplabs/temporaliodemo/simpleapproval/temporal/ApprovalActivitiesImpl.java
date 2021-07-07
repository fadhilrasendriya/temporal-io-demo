package com.gdplabs.temporaliodemo.simpleapproval.temporal;

public class ApprovalActivitiesImpl implements ApprovalActivities{
    @Override
    public void approve(String approverId, String taskId) {
        System.out.println(approverId + " has approved task " + taskId);
    }
}
