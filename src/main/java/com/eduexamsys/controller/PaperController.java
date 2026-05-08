package com.eduexamsys.controller;

import com.eduexamsys.entity.*;
import com.eduexamsys.service.impl.FillQuestionServiceImpl;
import com.eduexamsys.service.impl.JudgeQuestionServiceImpl;
import com.eduexamsys.service.impl.MultiQuestionServiceImpl;
import com.eduexamsys.service.impl.PaperServiceImpl;
import com.eduexamsys.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaperController {

    @Autowired
    private PaperServiceImpl paperService;

    @Autowired
    private JudgeQuestionServiceImpl judgeQuestionService;

    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;

    @Autowired
    private FillQuestionServiceImpl fillQuestionService;
    @GetMapping("/papers")
    public ApiResult<PaperManage> findAll() {
       ApiResult res =  ApiResultHandler.buildApiResult(200,"请求成功",paperService.findAll());
       return  res;
    }

    @GetMapping("/paper/{paperId}")
    public Map<Integer, List<?>> findById(@PathVariable("paperId") Integer paperId) {
        List<MultiQuestion> multiQuestionRes = multiQuestionService.findByIdAndType(paperId);
        List<FillQuestion> fillQuestionsRes = fillQuestionService.findByIdAndType(paperId);
        List<JudgeQuestion> judgeQuestionRes = judgeQuestionService.findByIdAndType(paperId);
        Map<Integer, List<?>> map = new HashMap<>();
        map.put(1,multiQuestionRes);
        map.put(2,fillQuestionsRes);
        map.put(3,judgeQuestionRes);
        return  map;
    }

    @PostMapping("/paperManage")
    public ApiResult add(@RequestBody PaperManage paperManage) {
        int res = paperService.add(paperManage);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"添加失败",res);
    }
}
