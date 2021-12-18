package com.harry.mall.controller;

import com.harry.mall.common.CommonLog;
import com.harry.mall.common.CommonPage;
import com.harry.mall.common.CommonResult;
import com.harry.mall.mbg.model.PmsBrand;
import com.harry.mall.service.PmsBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Tag(name = "PmsBrandController", description = "brand management")
@RestController
@RequestMapping(value = "/brands")
public class PmsBrandController {
    private final PmsBrandService brandService;

    private static final Logger logger = LogManager.getLogger(PmsBrandController.class);

    public PmsBrandController(PmsBrandService brandService) {
        this.brandService = brandService;
    }

    @Operation(description = "get all brands")
    @GetMapping(value = "/list")
    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(brandService.listAllBrand());
    }

    @Operation(description = "get paginated brands")
    @GetMapping
    public CommonResult<CommonPage<PmsBrand>> listBrand(@PageableDefault(page=1, size=20) Pageable pageable) {
        List<PmsBrand> brandList = brandService.listBrand(pageable.getPageNumber(), pageable.getPageSize());
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @Operation(description = "get specific brand with id")
    @GetMapping("/{id}")
    public CommonResult<PmsBrand> getBrand(@PathVariable("id")long id) {
        return CommonResult.success(brandService.getBrand(id));
    }

    @Operation(description = "create a new brand")
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

    @Operation(description = "update a specific brand")
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

    @Operation(description = "delete a specific brand")
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
