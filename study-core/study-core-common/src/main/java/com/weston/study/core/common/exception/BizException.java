package com.weston.study.core.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 8241210212000079359L;

    /** 异常码(RtnCode) */
    private String code;

    /** 接口(服务)返回信息 */
    private String msg;

    /** 日志信息 */
    private String loginfo;

    public BizException(ReturnCode returnCode) {
        this(returnCode.getCode(), returnCode.getMsg(), null, null);
    }

    public BizException(ReturnCode returnCode, String loginfo, Throwable e) {
        this(returnCode.getCode(), returnCode.getMsg(), loginfo, e);
    }

    public BizException(ReturnCode returnCode, Throwable e) {
        this(returnCode.getCode(), returnCode.getMsg(), null, e);
    }

    public BizException(String msg) {
        this(ReturnCode.FAILED.getCode(), msg);
    }

    public BizException(String code, String msg) {
        this(code, msg, null, null);
    }

    public BizException(String code, String msg, String loginfo) {
        this(code, msg, loginfo, null);
    }

    public BizException(String code, String msg, Throwable e) {
        this(code, msg, null, e);
    }

    public BizException(String code, String msg, String loginfo, Throwable e) {
        super(e);
        this.code = code;
        this.msg = msg;
        this.loginfo = loginfo;
    }
}
