package th.cu.thesis.fur.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import th.cu.thesis.fur.api.model.FlowConfig;
import th.cu.thesis.fur.api.util.FlowconfigSqlProvider;

public interface FlowConfigRepository {
	
	@SelectProvider(type = FlowconfigSqlProvider.class, method = "getSearchFlowConfig")
	public List<FlowConfig> getSearchFlowConfig(@Param("flowname") String flowname,@Param("flowtype") String flowtype,@Param("usertype") String urtype,@Param("startRow") int startRow,@Param("endRow") int endRow) throws RuntimeException;
	
	@SelectProvider(type = FlowconfigSqlProvider.class, method = "getCountSearchFlowConfig")
	public Integer getCountSearchFlowConfig(@Param("flowname") String flowname,@Param("flowtype") String flowtype,@Param("usertype") String usertype) throws RuntimeException;
	
}
