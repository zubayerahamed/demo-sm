package com.zayaanit.sm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Data;


@Data
@Service
public class AppConfig {

	@Value("${app.tomcat-service:Tomcat9}")
	private String tomcatServiceName;

	@Value("${app.database-name:demo}")
	private String databaseName;

}
