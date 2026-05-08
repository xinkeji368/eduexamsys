package com.eduexamsys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eduexamsys.entity.ApiResult;
import com.eduexamsys.entity.ExamManage;
import com.eduexamsys.service.impl.ExamManageServiceImpl;
import com.eduexamsys.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExamManageController {

    @Autowired
    private ExamManageServiceImpl examManageService;

    @GetMapping("/exams")
    public ApiResult findAll(){
        ApiResult apiResult;
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功", examManageService.findAll());
        return apiResult;
    }

    @GetMapping("/exams/{page}/{size}")
    public ApiResult findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        ApiResult apiResult;
        Page<ExamManage> examManage = new Page<>(page,size);
        IPage<ExamManage> all = examManageService.findAll(examManage);
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功", all);
        return apiResult;
    }

    @GetMapping("/exam/{examCode}")
    public ApiResult findById(@PathVariable("examCode") Integer examCode){
        ExamManage res = examManageService.findById(examCode);
        if(res == null) {
            return ApiResultHandler.buildApiResult(10000,"考试编号不存",null);
        }
        return ApiResultHandler.buildApiResult(200,"请求成功",res);
    }

    @DeleteMapping("/exam/{examCode}")
    public ApiResult deleteById(@PathVariable("examCode") Integer examCode){
        int res = examManageService.delete(examCode);
        return ApiResultHandler.buildApiResult(200,"删除成功",res);
    }

    @PutMapping("/exam")
    public ApiResult update(@RequestBody ExamManage exammanage){
        int res = examManageService.update(exammanage);
        System.out.print("更新操作执行---");
        return ApiResultHandler.buildApiResult(200,"更新成功",res);
    }

    @PostMapping("/exam")
    public ApiResult add(@RequestBody ExamManage exammanage){
        int res = examManageService.add(exammanage);
        if (res ==1) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        } else {
            return  ApiResultHandler.buildApiResult(400,"添加失败",res);
        }
    }

    @GetMapping("/examManagePaperId")
    public ApiResult findOnlyPaperId() {
        ExamManage res = examManageService.findOnlyPaperId();
        if (res != null) {
            return ApiResultHandler.buildApiResult(200,"请求成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"请求失败",res);
    }
}
