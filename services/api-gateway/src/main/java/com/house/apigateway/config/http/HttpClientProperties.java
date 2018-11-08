package com.house.apigateway.config.http;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix="spring.httpclient",ignoreInvalidFields = true,ignoreUnknownFields = false)
@PropertySource("classpath:/httpClient.properties")
@Configuration
public class HttpClientProperties {
	
	private Integer connectTimeOut;
	
	private Integer socketTimeOut;

	private String agent;
	private Integer maxConnPerRoute;
	private Integer maxConnTotaol;
	public Integer getConnectTimeOut() {
		return connectTimeOut;
	}
	public void setConnectTimeOut(Integer connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	public Integer getSocketTimeOut() {
		return socketTimeOut;
	}
	public void setSocketTimeOut(Integer socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public Integer getMaxConnPerRoute() {
		return maxConnPerRoute;
	}
	public void setMaxConnPerRoute(Integer maxConnPerRoute) {
		this.maxConnPerRoute = maxConnPerRoute;
	}
	public Integer getMaxConnTotaol() {
		return maxConnTotaol;
	}
	public void setMaxConnTotaol(Integer maxConnTotaol) {
		this.maxConnTotaol = maxConnTotaol;
	}
	
	
	
}
