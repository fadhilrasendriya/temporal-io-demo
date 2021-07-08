package com.gdplabs.temporaliodemo.simpleapproval.controller;


import com.gdplabs.temporaliodemo.simpleapproval.service.ApprovalService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/approval")
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

    @PostMapping("/delete")
    public String delete(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "approver") String approver
    ) {
        approvalService.deleteApprover(id, approver);
        return approver + " has been deleted";
    }

    @PostMapping("/add")
    public String add(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "approver") String approver
    ) {
        approvalService.addApprover(id, approver);
        return approver + " has been added";
    }

    @RequestMapping(path = "/getStatus", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public String getStatus(@RequestParam(name = "id") String id) {
        Map<String, Boolean> map = approvalService.getStatus(id);
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toJSONString();
    }

}
