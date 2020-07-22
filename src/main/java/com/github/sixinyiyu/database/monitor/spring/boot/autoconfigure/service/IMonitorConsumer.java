package com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.service;

import com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.model.DatabaseMessageDTO;

public interface IMonitorConsumer {

	void monitorMessage(DatabaseMessageDTO dto) ;
}
