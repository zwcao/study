package com.weston.study.core.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {

    SUCCESS("0", "操作成功"),
    FAILED("1", "操作失败"),

    UNAVAILABLE("404", "服务不可用"),
    EXP("500", "系统繁忙, 稍后再试"),
    EXP_AUTHORITY("501", "系统限制访问"),
    EXP_DEGRADE("502", "系统降级, 稍后再试"),
    EXP_FLOW("503", "系统限流, 稍后再试"),
    EXP_SYSTEM_BLOCK("504", "系统保护, 稍后再试");

    private String code;

    private String msg;

}
