package com.gdplabs.temporaliodemo.simpleapproval.controller;


import com.gdplabs.temporaliodemo.simpleapproval.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    private ApprovalService approvalService;

    @PostMapping("/requestApproval")
    public String requestApproval(
            @RequestParam(name = "id") String id
    ) {
        List<String> demoApprover = new ArrayList<>();
        demoApprover.add("approver1");
        demoApprover.add("approver2");
        demoApprover.add("approver3");

        approvalService.registerApprovalWorkflow(id, demoApprover);

        return "Approval Workflow Registered.";
    }

    @PostMapping("/approve")
    public String approve(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "approver") String approver
    ) {
        approvalService.approve(id, approver);
        return "Workflow Approved";
    }

}
