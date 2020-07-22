/**
 * Project Name: file-storage-spring-boot-autoconfigure
 * File Name:    QiniuFileStorageConfigure.java
 * Package Name: com.mgtj.storage.spring.boot.autoconfigure
 * Date:         2019年6月5日下午9:03:05
 * Copyright (c) 2019, 湖南芒果听见科技有限公司 All Rights Reserved.
*/

package com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * ClassName: KafkaMonitorConfigure <br/>
 * Function:  TODO kafka监听器相关配置 <br/>
 * Reason:    TODO ADD REASON <br/>
 * Date:      2020年5月11日 下午9:03:05 <br/>
 * @author    sixinyiyu@gmail.com
 * @version   1.0
 * @since     JDK 1.8
 */
@ConfigurationProperties("database.monitor")
@Data
public class KafkaMonitorConfigure {
	
	
	private String filter;
	
	@Data
	@ConfigurationProperties("database.monitor.kafka")
	class Kafka {
		
		private String id;

		private String topic;
		
		private String groupId;
	}
	
}

