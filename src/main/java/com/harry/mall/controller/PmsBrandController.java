package com.harry.mall.controller;

import com.harry.mall.common.CommonLog;
import com.harry.mall.common.CommonPage;
import com.harry.mall.common.CommonResult;
import com.harry.mall.mbg.model.PmsBrand;
import com.harry.mall.service.PmsBrandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/brands")
public class PmsBrandController {
    private final PmsBrandService brandService;

    private static final Logger logger = LogManager.getLogger(PmsBrandController.class);

    public PmsBrandController(PmsBrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(brandService.listAllBrand());
    }

    @GetMapping
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNumber") Integer pageNumber,
                                                        @RequestParam(value = "pageSize") Integer pageSize) {
        List<PmsBrand> brandList = brandService.listBrand(pageNumber, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @GetMapping("/{id}")
    public CommonResult<PmsBrand> getBrand(@PathVariable("id")long id) {
        return CommonResult.success(brandService.getBrand(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
        int count = brandService.createBrand(pmsBrand);
        if (count == 1) {
            logger.debug(CommonLog.builder().event("pmsBrandCreated"));
            return CommonResult.success(pmsBrand);
        } else {
            logger.error(CommonLog.builder().event("pmsBrandCreateFailed"));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "createBrandFailed");
        }
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResult updateBrand(@PathVariable Long id, @RequestBody PmsBrand pmsBrand) {
        int count = brandService.updateBrand(id, pmsBrand);
        if (count == 1) {
            logger.debug(CommonLog.builder().event("pmsBrandUpdated"));
            return CommonResult.success(pmsBrand);
        } else {
            logger.debug(CommonLog.builder().event("pmsBrandUpdateFailed"));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "updateBrandFailed");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CommonResult deleteBrand(@PathVariable Long id) throws ResponseStatusException {
        int count = brandService.deleteBrand(id);
        if (count == 1) {
            logger.debug(CommonLog.builder().event("pmsBrandUpdated"));
            return CommonResult.success(null);
        } else {
            logger.debug(CommonLog.builder().event("pmsBrandUpdateFailed"));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pmsBrandUpdateFailed");
        }
    }


}
