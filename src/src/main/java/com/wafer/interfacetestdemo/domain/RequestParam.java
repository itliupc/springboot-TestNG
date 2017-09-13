package com.wafer.interfacetestdemo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ps_request_param")
public class RequestParam {
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "native")
	@Column(name = "request_param_id", unique = true, nullable = false)
	private long requestParamId;
	@Column(name = "request_param_name")
	private String requestParamName;
	@Column(name = "request_param_type")
	private String requestParamType;
	@Column(name = "request_param_description")
	private String requestParamDescription;
	@Column(name = "interface_id")
	private long interfaceId;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;
	public long getRequestParamId() {
		return requestParamId;
	}
	public void setRequestParamId(long requestParamId) {
		this.requestParamId = requestParamId;
	}
	public String getRequestParamName() {
		return requestParamName;
	}
	public void setRequestParamName(String requestParamName) {
		this.requestParamName = requestParamName;
	}
	public String getRequestParamType() {
		return requestParamType;
	}
	public void setRequestParamType(String requestParamType) {
		this.requestParamType = requestParamType;
	}
	public String getRequestParamDescription() {
		return requestParamDescription;
	}
	public void setRequestParamDescription(String requestParamDescription) {
		this.requestParamDescription = requestParamDescription;
	}
	public long getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(long interfaceId) {
		this.interfaceId = interfaceId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public RequestParam(){
		super();
	}
	public RequestParam(long requestParamId, String requestParamName, String requestParamType,
			String requestParamDescription, long interfaceId, Date createTime, Date updateTime) {
		super();
		this.requestParamId = requestParamId;
		this.requestParamName = requestParamName;
		this.requestParamType = requestParamType;
		this.requestParamDescription = requestParamDescription;
		this.interfaceId = interfaceId;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}	
}
