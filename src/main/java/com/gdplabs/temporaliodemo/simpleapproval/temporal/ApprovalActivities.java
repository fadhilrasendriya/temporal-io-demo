package com.gdplabs.temporaliodemo.simpleapproval.temporal;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ApprovalActivities {

    @ActivityMethod
    void approve(String approverId, String taskId);
}
