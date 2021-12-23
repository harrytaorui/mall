package com.harry.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.harry.mall.mbg.mapper.PmsBrandMapper;
import com.harry.mall.mbg.model.PmsBrand;
import com.harry.mall.mbg.model.PmsBrandExample;
import com.harry.mall.service.PmsBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PmsBrandServiceImpl implements PmsBrandService {

    private final PmsBrandMapper brandMapper;

    @Override
    public List<PmsBrand> listAllBrand() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int createBrand(PmsBrand brand) {
        return brandMapper.insertSelective(brand);
    }

    @Override
    public int updateBrand(long id, PmsBrand brand) {
        brand.setId(id);
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public int deleteBrand(long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> listBrand(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public PmsBrand getBrand(long id) {
        return brandMapper.selectByPrimaryKey(id);
    }
}
