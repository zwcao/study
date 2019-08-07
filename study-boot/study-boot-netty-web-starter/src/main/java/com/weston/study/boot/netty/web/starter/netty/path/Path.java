package com.weston.study.boot.netty.web.starter.netty.path;

import com.weston.study.boot.netty.web.starter.netty.annotation.NettyHttpHandler;

public class Path {
    private String method;
    private String uri;
    private boolean equal;

    public static Path make(NettyHttpHandler annotation){
        return new Path(annotation);
    }
    public Path(NettyHttpHandler annotation){
        method = annotation.method();
        uri = annotation.path();
        equal = annotation.equal();
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    @Override
    public String toString(){
        return  method.toUpperCase() + " " + uri.toUpperCase();
    }
    @Override
    public int hashCode(){
        return  ("HTTP " + method.toUpperCase() + " " + uri.toUpperCase()).hashCode();
    }
    @Override
    public boolean equals(Object object){
        if (object instanceof  Path){
            Path path = (Path) object;
            return method.equalsIgnoreCase(path.method) && uri.equalsIgnoreCase(path.uri);
        }
        return false;
    }
}
