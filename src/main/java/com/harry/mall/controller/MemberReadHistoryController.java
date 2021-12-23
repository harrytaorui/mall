package com.harry.mall.controller;

import com.harry.mall.common.CommonException;
import com.harry.mall.common.CommonResult;
import com.harry.mall.common.ResultCode;
import com.harry.mall.nosql.mongodb.document.MemberReadHistory;
import com.harry.mall.service.MemberReadHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MemberReadHistoryController", description = "product read history of member management")
@RestController
@RequestMapping("/readHistories")
@RequiredArgsConstructor
public class MemberReadHistoryController {

    private final MemberReadHistoryService memberReadHistoryService;

    @Operation(description = "create read history")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResult create(@RequestBody MemberReadHistory memberReadHistory) {
        int count = memberReadHistoryService.create(memberReadHistory);
        if (count <= 0) {
            throw new CommonException(ResultCode.CREATE_FAILED.getCode(), "createReadHistoryFailed");
        }
        return CommonResult.success(count + " read history record created");
    }

    @Operation(description = "delete read history")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CommonResult delete(@RequestParam("ids") List<String> ids) {
        int count = memberReadHistoryService.delete(ids);
        if (count <= 0) {
            throw new CommonException(ResultCode.CREATE_FAILED.getCode(), "deleteReadHistoryFailed");
        }
        return CommonResult.success(count + " read history records deleted");
    }

    @Operation(description = "list all read history")
    @GetMapping
    public CommonResult<List<MemberReadHistory>> list(Long memberId) {
        List<MemberReadHistory> readHistoryList = memberReadHistoryService.list(memberId);
        return CommonResult.success(readHistoryList);
    }

}
