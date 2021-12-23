package com.harry.mall.service.impl;

import com.harry.mall.nosql.mongodb.document.MemberReadHistory;
import com.harry.mall.nosql.mongodb.repository.MemberReadHistoryRepository;
import com.harry.mall.service.MemberReadHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadHistoryServiceImpl implements MemberReadHistoryService {

    private final MemberReadHistoryRepository memberReadHistoryRepository;

    @Override
    public int create(MemberReadHistory memberReadHistory) {
        memberReadHistory.setId(null);
        memberReadHistory.setCreateTime(new Date());
        memberReadHistoryRepository.save(memberReadHistory);
        return 1;
    }

    @Override
    public int delete(List<String> ids) {
        List<MemberReadHistory> historyList = new ArrayList<>();
        ids.forEach(id->historyList.add(MemberReadHistory.builder().id(id).build()));
        memberReadHistoryRepository.deleteAll(historyList);
        return ids.size();
    }

    @Override
    public List<MemberReadHistory> list(Long memberId) {
        return memberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc(memberId);
    }
}
