package com.shenmao.archive_management_system.controller;

import com.shenmao.archive_management_system.pojo.*;
import com.shenmao.archive_management_system.service.ArchiveService;
import com.shenmao.archive_management_system.service.UserService;
import com.shenmao.archive_management_system.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/archive")
public class ArchiveController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArchiveService archiveService;

    //1.用户新增文章
    @PostMapping("/addArchive")
    public Result add(@RequestBody @Validated Archive archive){
        archiveService.add(archive);
        return Result.success();
    }



    //2.获取所有档案列表(管理员 & 普通用户→通过)
    //2.1 通过
    //pageNum: 当前页码 pageSize：每页条数
    @GetMapping("/list/success")
    public Result<PageBean<Archive>> listSuccess(Integer pageNum, Integer pageSize,
        @RequestParam(required = false) String archiveName, @RequestParam(required = false) String archiveCategory, @RequestParam(required = false) LocalDate archiveDate){
        System.out.println(pageNum);
        PageBean<Archive> pb = archiveService.list(pageNum, pageSize, archiveName, archiveCategory, archiveDate,"已通过");
        System.out.println(pb);
        return Result.success(pb);
    }

    //2.2 失败（管理员）
    //pageNum: 当前页码 pageSize：每页条数
    @GetMapping("/list/fail")
    public Result<PageBean<Archive>> listFail(Integer pageNum, Integer pageSize,
        @RequestParam(required = false) String archiveName, @RequestParam(required = false) String archiveCategory, @RequestParam(required = false) LocalDate archiveDate){
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        if (user.getPermission().equals("管理员")){
         PageBean<Archive> pb = archiveService.list(pageNum, pageSize, archiveName, archiveCategory, archiveDate,"未通过");
         return Result.success(pb);
        }
        else {
            return Result.error("非法访问路径");
        }
    }

    //2.3 审核中（管理员）
    //pageNum: 当前页码 pageSize：每页条数
    @GetMapping("/list/review")
    public Result<PageBean<Archive>> listReview(Integer pageNum, Integer pageSize,
        @RequestParam(required = false) String archiveName, @RequestParam(required = false) String archiveCategory, @RequestParam(required = false) LocalDate archiveDate){
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        if (user.getPermission().equals("管理员")){
            PageBean<Archive> pb = archiveService.list(pageNum, pageSize, archiveName, archiveCategory, archiveDate,"审核中");
            return Result.success(pb);
        }
        else {
            return Result.error("非法访问路径");
        }
    }



    //3.用户获取自己档案列表
    //3.1 成功
    @GetMapping("/itselfSuccess")
    public Result<List<Archive>> itselfSuccess(){
        System.out.println("wewe");
        List<Archive> archives = archiveService.itself("已通过");
        return Result.success(archives);
    }

    //3.2 失败
    @GetMapping("/itselfFail")
    public Result<List<Archive>> itselfFail(){
        List<Archive> archives = archiveService.itself("未通过");
        return Result.success(archives);
    }

    //3.3 审核中
    @GetMapping("/itselfReview")
    public Result<List<Archive>> itselfReview(){
        List<Archive> archives = archiveService.itself("审核中");
        return Result.success(archives);
    }


    //4.删除档案列表(用户&管理员)
    @DeleteMapping("/deleteArchive")
    public Result delete(@RequestParam Integer archiveId){
        archiveService.delete(archiveId);
        return Result.success();
    }


    //5.统计分类  bug:日期必须连续
    //5.1.1 日期:年
    @GetMapping("/count/DateYear")
    public Result<List<Integer>> countYear(String archiveDate){
        return Result.success(archiveService.countYear(archiveDate));
    }

    //5.1.2 日期:月
    @GetMapping("/count/DateMonth")
    public Result<List<Integer>> countMonth(String year, String month){
        return Result.success(archiveService.countMonth(year, month));
    }

    //5.2 分类
    @GetMapping("/count/archiveCategory")
    public Result<List<Category>> countCategory(){
        List<Category> list = archiveService.countCategory();
        return Result.success(list);
    }

    //5.3 姓名
    @GetMapping("/count/username")
    public Result<Integer> countName(String username){
        return Result.success(archiveService.countName(username));
    }

    //6.审批
    @PutMapping("/update")
    public Result updateStatus(Integer archiveId, String status, Integer secrecy, Integer userId){
        archiveService.updateStatus(archiveId, status, secrecy,userId);
        return Result.success();
    }

    //6.档案保密修改
    @PutMapping("/updateSecrecy")
    public Result updateSecrecy(Integer archiveId, Integer secrecy){
        archiveService.updateSecrecy(archiveId, secrecy);
        return Result.success();
    }


}


