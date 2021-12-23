package com.harry.mall.service;

import com.harry.mall.nosql.mongodb.document.MemberReadHistory;

import java.util.List;

public interface MemberReadHistoryService {

    int create(MemberReadHistory history);

    int delete(List<String> ids);

    List<MemberReadHistory> list(Long memberId);
}
