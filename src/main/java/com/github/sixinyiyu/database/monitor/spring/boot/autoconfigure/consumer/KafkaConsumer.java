package com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.consumer;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import com.alibaba.fastjson.JSONObject;
import com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.model.DatabaseMessageDTO;
import com.github.sixinyiyu.database.monitor.spring.boot.autoconfigure.service.IMonitorConsumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaConsumer extends AbstartConsumer {

	public KafkaConsumer(String filer, IMonitorConsumer monitorConsumer) {
		super(filer, monitorConsumer);
	}

	@KafkaListener(id = "${database.monitor.kafka.id}", topics = {"${database.monitor.kafka.topic}"}, groupId = "${database.monitor.kafka.group-id}")
	public void consumeSoundMessage(ConsumerRecord<?, ?> record) {
		Optional<?> kafkaMessage = Optional.ofNullable(record.value());
		if (kafkaMessage.isPresent()) {
			String message = kafkaMessage.get().toString();
			log.info("收到Kafka广播消息: {}", message);
			
			DatabaseMessageDTO messageDTO = JSONObject.parseObject(message, DatabaseMessageDTO.class);
			
			if (!doFilter(messageDTO.getDatabase(), messageDTO.getTable())) {
				pubMessage(messageDTO);
			}
		}
	}
}
