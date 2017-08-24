package com.wafer.interfacetestdemo.repository.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

  void createQuery(String sql);

  List<T> listBySql(String sql);

  Object queryBySql(String sql);

}
