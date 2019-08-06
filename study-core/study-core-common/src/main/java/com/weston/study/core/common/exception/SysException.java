package com.weston.study.core.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysException extends Exception {

    private static final long serialVersionUID = 8241210212000079359L;

    private String msg;

    public SysException(String msg) {
        super();
        this.msg = msg;
    }

    public SysException(Throwable e) {
        super(e);
    }

}
