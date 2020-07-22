package com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.filter;

import java.util.Objects;
import java.util.regex.Pattern;

public class FilterPattern {

	/**database、table*/
	private final Pattern dbPattern, tablePattern;
	
	public FilterPattern(Pattern dbPattern, Pattern tablePattern) {
		this.dbPattern = dbPattern;
		this.tablePattern = tablePattern;
	}
	
	public boolean match(String database, String table) {
		if (Objects.isNull(database) || Objects.isNull(table)) return false;
		//*.* ;sound.*; order.item;  所有库所有表   某个库下所有表; 具体库表
		return dbPattern.matcher(database).find() && tablePattern.matcher(table).find();
	}
	
}
