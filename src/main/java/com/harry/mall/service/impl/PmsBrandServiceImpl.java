package com.harry.mall.service.impl;

import com.harry.mall.mbg.mapper.PmsBrandMapper;
import com.harry.mall.mbg.model.PmsBrand;
import com.harry.mall.mbg.model.PmsBrandExample;
import com.harry.mall.service.PmsBrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    private final PmsBrandMapper brandMapper;

    public PmsBrandServiceImpl(PmsBrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    @Override
    public List<PmsBrand> listAllBrand() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int createBrand(PmsBrand brand) {
        return 0;
    }

    @Override
    public int updateBrand(long id, PmsBrand brand) {
        return 0;
    }

    @Override
    public int deleteBrand(PmsBrand brand) {
        return 0;
    }

    @Override
    public List<PmsBrand> listBrand(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public PmsBrand getBrand(long id) {
        return null;
    }
}
