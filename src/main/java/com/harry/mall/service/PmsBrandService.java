package com.harry.mall.service;

import com.harry.mall.mbg.model.PmsBrand;

import java.util.List;

public interface PmsBrandService {
    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrand brand);

    int updateBrand(long id, PmsBrand brand);

    int deleteBrand(long id);

    List<PmsBrand> listBrand(int pageNumber, int pageSize);

    PmsBrand getBrand(long id);
}
