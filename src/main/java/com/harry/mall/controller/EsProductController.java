package com.harry.mall.controller;

import com.harry.mall.common.CommonException;
import com.harry.mall.common.CommonPage;
import com.harry.mall.common.CommonResult;
import com.harry.mall.common.ResultCode;
import com.harry.mall.nosql.elasticsearch.document.EsProduct;
import com.harry.mall.service.EsProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Tag(name = "EsProductController", description = "search products management")
@RequestMapping(value = "/esProducts")
@RequiredArgsConstructor
public class EsProductController {

  private final EsProductService esProductService;

  @Operation(description = "import product from db to elasticsearch")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CommonResult<Integer> importAllProducts() {
    int count = esProductService.importAll();
    return CommonResult.success(count);
  }

  @Operation(description = "delete product by id")
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public CommonResult<Object> delete(@PathVariable Long id) {
    esProductService.delete(id);
    return CommonResult.success(null);
  }

  @Operation(description = "delete products by ids")
  @DeleteMapping(value = "/batch")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public CommonResult<Object> delete(@RequestParam(value = "ids") List<Long> ids) {
    esProductService.delete(ids);
    return CommonResult.success(null);
  }

  @Operation(description = "create product by id")
  @PostMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public CommonResult<EsProduct> create(@PathVariable Long id) {
    EsProduct esProduct = esProductService.create(id);
    if (Objects.isNull(esProduct)) {
      throw new CommonException(ResultCode.CREATE_FAILED.getCode(), "create esProduct failed");
    }
    return CommonResult.success("esProduct created",esProduct);
  }

  @Operation(description = "search product by keyword")
  @GetMapping
  public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                    @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                    @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
    Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
    return CommonResult.success(CommonPage.restPage(esProductPage));
  }

}
