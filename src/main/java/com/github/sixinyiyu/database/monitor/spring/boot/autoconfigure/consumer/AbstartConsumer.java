package com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.consumer;

import java.util.List;

import com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.filter.FilterParser;
import com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.filter.FilterPattern;
import com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.model.DatabaseMessageDTO;
import com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.service.IMonitorConsumer;

public abstract class AbstartConsumer {
	
	private List<FilterPattern> filterPatterns;
	
	private IMonitorConsumer monitorConsumer;
	
	public AbstartConsumer(String filer, IMonitorConsumer monitorConsumer) {
		
		FilterParser parse = new FilterParser(filer);
		
		this.filterPatterns = parse.parse();
		
		this.monitorConsumer = monitorConsumer;
	}
	
	/**true 表示过滤掉*/
	boolean doFilter(String database , String table) {
		return !filterPatterns.stream().filter(pattern -> pattern.match(database, table)).findAny().isPresent();
	}

	/**发布消息供上层消费者处理*/
	void pubMessage(DatabaseMessageDTO messageDTO) {
		monitorConsumer.monitorMessage(messageDTO);
	}
}
