package com.shenmao.archive_management_system.controller;

import com.shenmao.archive_management_system.pojo.History;
import com.shenmao.archive_management_system.pojo.PageBean;
import com.shenmao.archive_management_system.pojo.Result;
import com.shenmao.archive_management_system.service.HistoryService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    //借阅申请
    @PostMapping("/borrow")
    public Result borrow(Integer archiveId, String archiveUrl){
        historyService.borrow(archiveId, archiveUrl);
        return Result.success();
    }

   // 查列表详情
    @GetMapping("/list")
    public Result<PageBean<History>>list(Integer pageNum, Integer pageSize, String status){
        PageBean<History> pb = historyService.list(pageNum, pageSize, status);
        return Result.success(pb);
    }

    //审批
    @PutMapping("/approval")
    public Result approval(@RequestBody History history){
        historyService.approval(history);
        return Result.success();
    }

    // 删
    @DeleteMapping("/delete")
    public Result delete(Integer recordId){
        historyService.delete(recordId);
        return Result.success();
    }

    //查原因
    @GetMapping("/reason")
    public Result<History> getReason(Integer archiveId){
        return Result.success(historyService.getReason(archiveId));
    }

}
