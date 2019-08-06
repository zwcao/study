package com.weston.study.core.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 8241210212000079359L;

    private String code;

    private String msg;

    public BizException(String msg) {
        this(null, msg);
    }

    public BizException(String code, String msg) {
        super();
        this.code = code == null ? ReturnCode.EXP.getCode() : code;
        this.msg = msg;
    }

    public BizException(Throwable e) {
        super(e);
    }

}
