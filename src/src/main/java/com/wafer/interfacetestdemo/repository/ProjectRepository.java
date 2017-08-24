package com.wafer.interfacetestdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wafer.interfacetestdemo.domain.Project;
import com.wafer.interfacetestdemo.repository.base.BaseRepository;
import com.wafer.interfacetestdemo.vo.ProjectVo;

public interface ProjectRepository extends BaseRepository<Project, Long> {

  @Query(value = "Select new com.wafer.interfacetestdemo.vo.ProjectVo (p.projectId, p.projectName, p.createUserId, u.userName, p.deptId) from Project p, User u where u.userId = p.createUserId order by p.projectId asc")
  List<ProjectVo> getProjectList();
  
  @Query(value = "Select new com.wafer.interfacetestdemo.vo.ProjectVo (p.projectId, p.projectName, p.createUserId, u.userName, p.deptId) from Project p, User u where u.userId = p.createUserId and p.deptId = :deptId order by p.projectId asc")
  List<ProjectVo> getProjectByDeptId(@Param("deptId") long deptId);
}
