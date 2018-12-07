package com.weston.study.boot.druid.starter;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.weston.study.boot.druid.starter.DBContextHolder.DbType;

public class RoutingDataSource extends AbstractRoutingDataSource {
	
	private int slaveCount;
    private AtomicInteger count = new AtomicInteger(0);
    
    public RoutingDataSource(int slaveCount) {
    		this.slaveCount = slaveCount;
    }

	@Override
	protected Object determineCurrentLookupKey() {
		if(slaveCount == 0) {
			return Integer.valueOf(0);
		}
		DbType dbType = DBContextHolder.getDbType();
		if(dbType == DbType.SLAVE) {
			return Integer.valueOf(0);
		}
		return count.incrementAndGet() % slaveCount + 1;
	}

}
