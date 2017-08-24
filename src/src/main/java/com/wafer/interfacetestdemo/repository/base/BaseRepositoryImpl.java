package com.wafer.interfacetestdemo.repository.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements BaseRepository<T, ID> {

  private final EntityManager em;

  public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
    super(domainClass, em);
    this.em = em;
  }


  @Override
  public void createQuery(String sql) {
    if (sql.contains("ps_")) {
      em.createNativeQuery(sql);
    } else {
      em.createQuery(sql);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> listBySql(String sql) {
    if (sql.contains("ps_")) {
      return em.createNativeQuery(sql).getResultList();
    } else {
      return em.createQuery(sql).getResultList();
    }
  }

  @Override
  public Object queryBySql(String sql) {
    if (sql.contains("ps_")) {
      return em.createNamedQuery(sql).getSingleResult();
    } else {
      return em.createQuery(sql).getSingleResult();
    }
  }
}
