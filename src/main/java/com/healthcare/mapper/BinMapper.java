package com.healthcare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthcare.entity.Bin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BinMapper extends BaseMapper<Bin> {
    Bin getOneBin(Integer id);

    List<Bin> getAllBin();

    List<Bin> getWarnBin();

    void changeLevel(int id, double level);

    void changeBinInf(int id, int state, double level, String otherThing, String latitude, String longitude);

    void changeOtherThing(int id, String otherThing);

    void deleteBinByID(int id);
}
