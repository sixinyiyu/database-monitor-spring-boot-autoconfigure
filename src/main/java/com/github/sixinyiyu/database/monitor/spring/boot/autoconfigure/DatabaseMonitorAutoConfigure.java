/**
 * Project Name: file-storage-spring-boot-autoconfigure
 * File Name:    FileStorageAutoConfigure.java
 * Package Name: com.mgtj.storage.spring.boot.autoconfigure
 * Date:         2019年6月5日上午11:27:51
 * Copyright (c) 2019, 湖南芒果听见科技有限公司 All Rights Reserved.
*/

package com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.consumer.KafkaConsumer;
import com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.service.IMonitorConsumer;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: FileStorageAutoConfigure <br/>
 * Function: TODO ADD FUNCTION <br/>
 * Reason: TODO ADD REASON <br/>
 * Date: 2019年6月5日 上午11:27:51 <br/>
 * 
 * @author qingquanzhong@himango.cn
 * @version 1.0
 * @since JDK 1.8
 */
@Configuration
@EnableConfigurationProperties(KafkaMonitorConfigure.class)
@ConditionalOnClass(value = IMonitorConsumer.class)
@Slf4j
public class DatabaseMonitorAutoConfigure {

	@Autowired
	private KafkaMonitorConfigure fileStorageConfigure;
	
	@Autowired
	private IMonitorConsumer monitorConsumer;

	@Bean("databaseMonitorKafkaConsumer")
	public KafkaConsumer consume() {
		log.info("Initializing database monitor consumer kafka ");
		return new KafkaConsumer(fileStorageConfigure.getFilter(), monitorConsumer);
	}

}
