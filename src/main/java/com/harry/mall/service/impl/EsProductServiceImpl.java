package com.harry.mall.service.impl;

import com.harry.mall.dao.EsProductDao;
import com.harry.mall.nosql.elasticsearch.document.EsProduct;
import com.harry.mall.nosql.elasticsearch.repository.EsProductRepository;
import com.harry.mall.service.EsProductService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EsProductServiceImpl implements EsProductService {
  private static final Logger logger = LogManager.getLogger(EsProductServiceImpl.class);

  private final EsProductDao productDao;
  private final EsProductRepository productRepository;

  @Override
  public int importAll() {
    List<EsProduct> esProductList = productDao.getAllEsProductList(null);
    Iterable<EsProduct> esProductIterable = productRepository.saveAll(esProductList);
    Iterator<EsProduct> iterator = esProductIterable.iterator();
    int result = 0;
    while (iterator.hasNext()) {
      result++;
      iterator.next();
    }
    return result;
  }

  @Override
  public void delete(long id) {
    productRepository.deleteById(id);
  }

  @Override
  public EsProduct create(long id) {
    EsProduct result = null;
    List<EsProduct> esProductList = productDao.getAllEsProductList(id);
    if (esProductList.size() > 0) {
      EsProduct esProduct = esProductList.get(0);
      result = productRepository.save(esProduct);
    }
    return result;
  }

  @Override
  public void delete(List<Long> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      List<EsProduct> esProductList = new ArrayList<>();
      for (Long id: ids) {
        EsProduct esProduct = new EsProduct();
        esProduct.setId(id);
        esProductList.add(esProduct);
      }
      productRepository.deleteAll(esProductList);
    }
  }

  @Override
  public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNum, pageSize);
    return productRepository.findByNameOrSubTitleOrKeywords(keyword,keyword,keyword,pageable);
  }
}
