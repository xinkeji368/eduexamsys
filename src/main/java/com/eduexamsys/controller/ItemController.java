package com.eduexamsys.controller;

import com.eduexamsys.entity.ApiResult;
import com.eduexamsys.entity.PaperManage;
import com.eduexamsys.service.PaperService;
import com.eduexamsys.service.impl.FillQuestionServiceImpl;
import com.eduexamsys.service.impl.JudgeQuestionServiceImpl;
import com.eduexamsys.service.impl.MultiQuestionServiceImpl;
import com.eduexamsys.util.ApiResultHandler;
import com.eduexamsys.vo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    MultiQuestionServiceImpl multiQuestionService;
    @Autowired
    FillQuestionServiceImpl fillQuestionService;
    @Autowired
    JudgeQuestionServiceImpl judgeQuestionService;
    @Autowired
    PaperService paperService;



    @PostMapping("/item")
    public ApiResult ItemController(@RequestBody Item item) {
        Integer changeNumber = item.getChangeNumber();
        Integer fillNumber = item.getFillNumber();
        Integer judgeNumber = item.getJudgeNumber();
        Integer paperId = item.getPaperId();

        // 选择题数据库获取
        List<Integer>  changeNumbers = multiQuestionService.findBySubject(item.getSubject(), changeNumber);
        if(changeNumbers==null){
            return ApiResultHandler.buildApiResult(400,"选择题数据库获取失败",null);
        }
        for (Integer number : changeNumbers) {
            PaperManage paperManage = new PaperManage(paperId,1,number);
            int index = paperService.add(paperManage);
            if(index==0)
                return ApiResultHandler.buildApiResult(400,"选择题组卷保存失败",null);
        }

        List<Integer> fills = fillQuestionService.findBySubject(item.getSubject(), fillNumber);
        if(fills==null)
            return ApiResultHandler.buildApiResult(400,"填空题数据库获取失败",null);
        for (Integer fillNum : fills) {
            PaperManage paperManage = new PaperManage(paperId,2,fillNum);
            int index = paperService.add(paperManage);
            if(index==0)
                return ApiResultHandler.buildApiResult(400,"填空题题组卷保存失败",null);
        }
        List<Integer> judges = judgeQuestionService.findBySubject(item.getSubject(), judgeNumber);
        if(fills==null)
            return ApiResultHandler.buildApiResult(400,"判断题数据库获取失败",null);
        for (Integer judge : judges) {
            PaperManage paperManage = new PaperManage(paperId,3,judge);
            int index = paperService.add(paperManage);
            if(index==0)
                return ApiResultHandler.buildApiResult(400,"判断题题组卷保存失败",null);
        }


          return ApiResultHandler.buildApiResult(200,"试卷组卷成功",null);
    }
    @PostMapping("/expurgate")
    public String ExpurgateController(@RequestBody Item item) {
        //出卷id
        Integer paperId = item.getPaperId();

        if (paperId.equals(null)){
            return "题库还没有组卷记";
        }else {
            int dex = paperService.expurgate(paperId);
            return "请重新组";
        }


    }
}
