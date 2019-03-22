package com.weston.study.boot.mongo.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "mongodb", name = "enable", havingValue = "true")
@ConfigurationProperties(prefix = "mongodb")
public class MongodbProperties {

    private String uri;

    private int minConnectionsPerHost = 5;

    private int maxConnectionsPerHost = 20;

    private int connectionTimeout = 60000;

    private int socketTimeout = 300000;

    private int threadsAllowedToBlockForConnectionMultiplier = 10;

    /**
     * 要扫描并映射的包
     */
    private String mapPackages;

    /**
     * 要映射的类
     */
    private String mapClasses;

    /**
     * 扫描包时，是否忽略不映射的类 这里按照Morphia的原始定义，默认设为false
     */
    private boolean ignoreInvalidClasses;

    private String dbName;

    private boolean ensureIndexes;

    private boolean ensureCaps;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getMinConnectionsPerHost() {
        return minConnectionsPerHost;
    }

    public void setMinConnectionsPerHost(int minConnectionsPerHost) {
        this.minConnectionsPerHost = minConnectionsPerHost;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getThreadsAllowedToBlockForConnectionMultiplier() {
        return threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    public String getMapPackages() {
        return mapPackages;
    }

    public void setMapPackages(String mapPackages) {
        this.mapPackages = mapPackages;
    }

    public String getMapClasses() {
        return mapClasses;
    }

    public void setMapClasses(String mapClasses) {
        this.mapClasses = mapClasses;
    }

    public boolean isIgnoreInvalidClasses() {
        return ignoreInvalidClasses;
    }

    public void setIgnoreInvalidClasses(boolean ignoreInvalidClasses) {
        this.ignoreInvalidClasses = ignoreInvalidClasses;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean isEnsureIndexes() {
        return ensureIndexes;
    }

    public void setEnsureIndexes(boolean ensureIndexes) {
        this.ensureIndexes = ensureIndexes;
    }

    public boolean isEnsureCaps() {
        return ensureCaps;
    }

    public void setEnsureCaps(boolean ensureCaps) {
        this.ensureCaps = ensureCaps;
    }
}
