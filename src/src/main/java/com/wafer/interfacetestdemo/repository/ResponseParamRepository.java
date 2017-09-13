package com.wafer.interfacetestdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wafer.interfacetestdemo.domain.ResponseParam;
import com.wafer.interfacetestdemo.repository.base.BaseRepository;

public interface ResponseParamRepository extends BaseRepository<ResponseParam, Long>{
	@Query(value="from ResponseParam rp where rp.interfaceId = :interfaceId ")
	List<ResponseParam> getResponseParamByInterfaceId(@Param("interfaceId") long interfaceId);

	void deleteResponseParamByResponseParamId(long responseParamId);

	ResponseParam findResponseParamByResponseParamId(long responseParamId);
}
