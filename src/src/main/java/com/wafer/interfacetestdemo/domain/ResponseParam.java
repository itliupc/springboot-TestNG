package com.wafer.interfacetestdemo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="ps_response_param")
public class ResponseParam {
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "native")
	@Column(name = "response_param_id", unique = true, nullable = false)
	private long responseParamId;
	@Column(name = "response_param_name")
	private String responseParamName;
	@Column(name = "response_param_type")
	private String responseParamType;
	@Column(name = "response_param_description")
	private String responseParamDescription;
	@Column(name = "interface_id")
	private long interfaceId;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;
	
	public long getResponseParamId() {
		return responseParamId;
	}
	public void setResponseParamId(long responseParamId) {
		this.responseParamId = responseParamId;
	}
	public String getResponseParamName() {
		return responseParamName;
	}
	public void setResponseParamName(String responseParamName) {
		this.responseParamName = responseParamName;
	}
	public String getResponseParamType() {
		return responseParamType;
	}
	public void setResponseParamType(String responseParamType) {
		this.responseParamType = responseParamType;
	}
	public String getResponseParamDescription() {
		return responseParamDescription;
	}
	public void setResponseParamDescription(String responseParamDescription) {
		this.responseParamDescription = responseParamDescription;
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
	
	public ResponseParam() {
		super();
	}
	
	public ResponseParam(long responseParamId, String responseParamName, String responseParamType,
			String responseParamDescription, long interfaceId, Date createTime, Date updateTime) {
		super();
		this.responseParamId = responseParamId;
		this.responseParamName = responseParamName;
		this.responseParamType = responseParamType;
		this.responseParamDescription = responseParamDescription;
		this.interfaceId = interfaceId;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
		
}
