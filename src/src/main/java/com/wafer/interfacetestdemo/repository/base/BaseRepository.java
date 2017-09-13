package com.wafer.interfacetestdemo.repository.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

  Query createQuery(String sql);

  List<T> listBySql(String sql);
  
  Object queryBySql(String sql);

}
