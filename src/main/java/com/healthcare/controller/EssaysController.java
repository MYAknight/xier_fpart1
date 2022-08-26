package com.healthcare.controller;

import com.healthcare.entity.Collections;
import com.healthcare.entity.Essays;
import com.healthcare.mapper.CollectionMapper;
import com.healthcare.mapper.EssaysMapper;
import com.healthcare.mapper.TUserMapper;
import com.healthcare.response.Result;
import com.healthcare.service.dao.UploadService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/essays")
public class EssaysController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    EssaysMapper essaysMapper;
    @Autowired
    TUserMapper tUserMapper;
    @Autowired
    CollectionMapper collectionMapper;

    @PostMapping("/getEssayByKey")
    public Result getEssayByKey(@RequestParam("searchKey") String searchKey) {
        List<Essays> essays = essaysMapper.selectByKey("%" + searchKey + "%");
        Result result = Result.ok().message("success");
        result.data("essay", essays);
        return result;
    }

    @PostMapping("/getEssayById")
    public Result getEssayById(@RequestParam("essay_id") String essay_id) {
        Essays essays = essaysMapper.selectById(essay_id);
        Result result = Result.ok().message("success");
        result.data("essay", essays);
        return result;
    }

    @PostMapping("/getEssayByType")
    public Result getEssayBytype(@RequestParam("type") String type) {
        List<Essays> essays = essaysMapper.selectByType(type);
        Result result = Result.ok().message("success");
        result.data("essay", essays);
        return result;
    }

    @ApiOperation("获取推荐文章，如果已经有收藏会返回与收藏同类的文章，否则获取第一篇文章同类文章,成功后message返回“success”和一个essay的list")
    @PostMapping("/recommend")
    public Result recommend(@RequestParam("user_id") Integer user_id) {
        List<Collections> collections = collectionMapper.selectCollections(user_id);
        Result result = null;
        if (collections.size() != 0) {
            System.out.println("11111111111111111111111111111111");
            Collections collection = collections.get(collections.size() - 1);
            Essays recommend_essay = essaysMapper.selectById(collection.getEssayId());
            List<Essays> recommend_essays = essaysMapper.selectByType(recommend_essay.getClassification());
            result = Result.ok().message("success");
            result.data("essay", recommend_essays);
        }else{
            System.out.println("22222222222222222222222222222222222");
            Essays recommend_essay = essaysMapper.selectById(1);
            List<Essays> recommend_essays = essaysMapper.selectByType(recommend_essay.getClassification());
            result = Result.ok().message("success");
            result.data("essay", recommend_essays);
        }
        return result;
    }


    @ApiOperation("编写新文章,成功后message返回“文章添加成功”")
    @PostMapping("/writeNewEssays")
    public Result writeNewEssays(@ApiParam("标题") @RequestParam("title") String title,
                                 @ApiParam("文本") @RequestParam("text") String text,
                                 @ApiParam("作者") @RequestParam("writer") String writer,
                                 @ApiParam("类别") @RequestParam("classification") String classification,
                                 @ApiParam("商品照片（支持jpg，bmp，png）") @RequestParam("file") MultipartFile file) throws Exception {
        String fileName = uploadService.uploadImage(file);
        String baseString = "http://47.107.225.197:8080/sk/image/";
        fileName = baseString + fileName;
        Essays essay = new Essays();
        essay.setTitle(title);
        essay.setText(text);
        essay.setWriter(writer);
        essay.setClassification(classification);
        essay.setPicture(fileName);
        essaysMapper.insert(essay);
        Result result = Result.ok().message("文章添加成功");
        return result;
    }
}

