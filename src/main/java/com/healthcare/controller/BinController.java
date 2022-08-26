package com.healthcare.controller;

import com.healthcare.entity.Bin;
import com.healthcare.mapper.BinMapper;
import com.healthcare.response.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/Bin")
public class BinController {
    @Autowired
    BinMapper binMapper;

    @ApiOperation("获得所有垃圾桶列表,data里返回一个名称为“Bin”，垃圾桶的list")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = Bin.class),
    })
    @PostMapping("/getAllBin")
    public Result getAllBin() {
        List<Bin> bin = binMapper.getAllBin();
        Result result = Result.ok();
        result.data("Bin", bin);
        result.message("success");
        return result;
    }

    @ApiOperation("获得所有状态为异常的垃圾桶列表,data里返回一个名称为“Bin”，垃圾桶的list")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = Bin.class),
    })
    @PostMapping("/getAllWarnBin")
    public Result getAllWarnBin() {
        List<Bin> bin = binMapper.getWarnBin();
        Result result = Result.ok();
        result.data("Bin", bin);
        result.message("success");
        return result;
    }

    @ApiOperation("获得经纬度差不超过1的垃圾桶列表,data里返回一个名称为“Bin”，垃圾桶的list")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = Bin.class),
    })
    @PostMapping("/getNearestBin")
    public Result getNearestBin(@RequestParam("latitude") String latitude,
                                @RequestParam("longitude") String longitude) {
        List<Bin> Bin = binMapper.getAllBin();
        Result result;
        if (Bin != null) {
            Iterator<Bin> iterator = Bin.iterator();
            double[] arr = new double[Bin.size()];
            int index = 0;
            while (iterator.hasNext()) {
                Bin b1 = iterator.next();
                if (Math.abs(Float.parseFloat(b1.getLatitude()) - Float.parseFloat(latitude)) > 1 || Math.abs(Float.parseFloat(b1.getLongitude()) - Float.parseFloat(longitude)) > 1) {
                    iterator.remove();//使用迭代器的删除方法删除
                }
            }
        } else {
            result = Result.ok().message("没有查询到任何信息");
        }
        result = Result.ok().message("success");
        result.data("Bin", Bin);
        return result;
    }

    @PreAuthorize("hasRole('admin')")//只有admin才可
    @ApiOperation("（仅管理员权限可用）添加新垃圾桶,成功后message返回“垃圾桶添加成功”")
    @PostMapping("/addNewBin")
    public Result addNewBin(@ApiParam("纬度位置") @RequestParam("latitude") String latitude,
                            @ApiParam("经度位置") @RequestParam("longitude") String longitude) {
        Bin b1 = new Bin();
        b1.setState(1);
        b1.setLatitude(latitude);
        b1.setLongitude(longitude);
        b1.setLevel(0);
        binMapper.insert(b1);
        Result result = Result.ok().message("垃圾桶添加成功");
        return result;
    }

    @ApiOperation("更改垃圾桶位置描述信息")
    @PostMapping("/changeBinDescribe")
    public Result changeBinLevel(@ApiParam("垃圾桶id") @RequestParam("id") int id,
                                 @ApiParam("垃圾描述信息") @RequestParam("description") String description) {
        binMapper.changeOtherThing(id, description);
        Result result = Result.ok().message("更改成功");
        return result;
    }

    @ApiOperation("（仅暑假测试用，实际项目可以使用IOT硬件实时更新，不需要手动添加）更改垃圾桶量")
    @PostMapping("/changeBinLevel")
    public Result changeBinLevel(@ApiParam("垃圾桶id") @RequestParam("id") int id,
                                 @ApiParam("垃圾量") @RequestParam("level") double level) {
        binMapper.changeLevel(id, level);
        Result result = Result.ok().message("更改成功");
        return result;
    }

    @ApiOperation("更改垃圾桶信息")
    @PostMapping("/changeBinInf")
    public Result changeBinInf(@ApiParam("垃圾桶id") @RequestParam("id") int id,
                               @ApiParam("垃圾量") @RequestParam("level") double level,
                               @ApiParam("状态") @RequestParam("state") int state,
                               @ApiParam("垃圾描述信息") @RequestParam("description") String description,
                               @ApiParam("经度") @RequestParam("latitude") String latitude,
                               @ApiParam("纬度") @RequestParam("longitude") String longitude
    ) {
        binMapper.changeBinInf(id,state, level, description, latitude, longitude);
        Result result = Result.ok().message("更改成功");
        return result;
    }

    @ApiOperation("按照ID删除垃圾桶")
    @PostMapping("/deleteBinByID")
    public Result deleteBinByID(@ApiParam("垃圾桶id") @RequestParam("id") int id
    ) {
        binMapper.deleteBinByID(id);
        Result result = Result.ok().message("更改成功");
        return result;
    }
}
