package com.wafer.interfacetestdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wafer.interfacetestdemo.domain.RequestParam;
import com.wafer.interfacetestdemo.repository.base.BaseRepository;

public interface RequestParamRepository extends BaseRepository<RequestParam, Long> {

	@Query(value="from RequestParam rp where rp.interfaceId = :interfaceId ")
	List<RequestParam> getRequestParamByInterfaceId(@Param("interfaceId") long interfaceId);

	void deleteRequestParamByRequestParamId(long requestParamId);

	RequestParam findRequestParamByRequestParamId(long requestParamId);

}
