package com.harry.mall.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonLog<T> {

    private String event;
    private T data;

    @Override
    public String toString() {
        return "Event: " + event + ", Data: " + data;
    }
}
