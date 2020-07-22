package com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class DatabaseMessageDTO implements Serializable {

	private static final long serialVersionUID = 6527443613506862114L;

	private String database;
	
	private String table;
	
	/**消息内容*/
	private JSONObject body;
	
	/**insert/update/delete*/
	private String type;
	
	public boolean delete() {
		return "delete".equals(this.type);
	}
	
	public boolean insert() {
		return "insert".equals(this.type);
	}
	
	public boolean update() {
		return "update".equals(this.type);
	}
}
