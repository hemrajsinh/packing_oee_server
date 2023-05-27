package in.sparklogic.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import in.sparklogic.params.MachineDTO;
import in.sparklogic.params.OEEDataDTO;
import in.sparklogic.params.ParamsDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OEEDataService {

	@PersistenceContext
	private EntityManager entityManager;
	private final JdbcTemplate jdbcTemplate;
	 public OEEDataService(JdbcTemplate jdbcTemplate) {
	     this.jdbcTemplate = jdbcTemplate;
	 }
	 
	 private void callLineOverviewSP(String lineId) {
		 System.out.println("in sp call");
	        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
	        		.withProcedureName("RECKITT_Line5_Line_GenerateOLEDashboard");; //line5_machine_generateoeedashboard

	        // Set the input parameters for the stored procedure
	        MapSqlParameterSource inParams = new MapSqlParameterSource()
	            .addValue("lineid", lineId);
//	            .addValue("machineid", param2);

	        // Execute the stored procedure
	        call.execute(inParams);
	    }
	 
	 private void callMachineOverviewSP(String lineId, String machineId) {
		 System.out.println("in sp call");
	        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
	        		.withProcedureName("RECKITT_Line5_Machine_GenerateOEEDashboard");; //line5_machine_generateoeedashboard

	        // Set the input parameters for the stored procedure
	        MapSqlParameterSource inParams = new MapSqlParameterSource()
	            .addValue("lineid", lineId)
	            .addValue("machineid", machineId);

	        // Execute the stored procedure
	        call.execute(inParams);
	    }
	 
	 private void callLineHistoryOverviewSP(String lineId, String startDt, String endDt, String forShift, String interval) {
		 System.out.println("in sp call");
	        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
	        		.withProcedureName("RECKITT_Line5_Line_History_GenerateOLEDashboard");; //line5_machine_generateoeedashboard

	        // Set the input parameters for the stored procedure
	        MapSqlParameterSource inParams = new MapSqlParameterSource()
	            .addValue("lineid", lineId)
	            .addValue("startDt", startDt)
	            .addValue("endDt", endDt)
	            .addValue("forShift", forShift)
	            .addValue("interval", interval.isEmpty() ? "" : interval);

	        // Execute the stored procedure
	        call.execute(inParams);
	    }
	 
	 private void callMachineHistoryOverviewSP(String lineId, String machineId, String startDt, String endDt, String forShift, String interval) {
		 System.out.println("in sp call");
	        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
	        		.withProcedureName("RECKITT_Line5_Machine_History_GenerateOEEDashboard");; //line5_machine_generateoeedashboard

	        // Set the input parameters for the stored procedure
	        MapSqlParameterSource inParams = new MapSqlParameterSource()
	        		.addValue("lineid", lineId)
	        		.addValue("machineid", machineId)
		            .addValue("startDt", startDt)
		            .addValue("endDt", endDt)
		            .addValue("forShift", forShift)
		            .addValue("interval", interval.isEmpty() ? "" : interval);

	        // Execute the stored procedure
	        call.execute(inParams);
	    }
	 
	public List<OEEDataDTO> getData(String tableName, String lineOEE, ParamsDTO params) {
		
		if("live".equals(params.getDashboardType()) && "line".equals(lineOEE) ) {
			this.callLineOverviewSP(params.getLineId());
		} else if("live".equals(params.getDashboardType()) && "oee".equals(lineOEE) ) {
			this.callMachineOverviewSP(params.getLineId(), params.getMachineId());
		}else if("history".equals(params.getDashboardType()) && "line".equals(lineOEE) ) {
			this.callLineHistoryOverviewSP(params.getLineId(), params.getFromDate(), params.getToDate(), params.getShiftSelection(), params.getDaySelection());
		}else if("history".equals(params.getDashboardType()) && "oee".equals(lineOEE) ) {
			this.callMachineHistoryOverviewSP(params.getLineId(), params.getMachineId(), params.getFromDate(), params.getToDate(), params.getShiftSelection(), params.getDaySelection());
		}

		List<OEEDataDTO> listData = new ArrayList<OEEDataDTO>();
		try {
			
			Query query = entityManager.createNativeQuery("SELECT dateandtime, tagindex, convert(varchar(255), tagdescription) as tagdescription, val, convert(varchar(255), varname) as varname FROM "+tableName);
			// query.setParameter(2, version);

			List<Object[]> list = query.getResultList();

			for (int i = 0; i < list.size(); i++) {
				Object[] objects = list.get(i);
				OEEDataDTO oeeData = new OEEDataDTO();
				
				if (!StringUtils.isEmpty(objects[0])) {
					Timestamp cts = (Timestamp) objects[0];
					oeeData.setDateAndTime(cts);
				}
				oeeData.setTagIndex((short) objects[1]);
				oeeData.setTagDescription((String) objects[2]);
				oeeData.setVal((String) objects[3]);
				oeeData.setVarName((String) objects[4]);
				
				listData.add(oeeData);
			}
			return listData;
		} catch (NoResultException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listData;
	}
	
	public List<MachineDTO> getaMachineData() {

		List<MachineDTO> listData = new ArrayList<MachineDTO>();
		try {
			
			Query query = entityManager.createNativeQuery("SELECT machine_id, convert(varchar(255), plant_name) as plant_name, line_id, convert(varchar(255), line_name) as line_name, convert(varchar(255), machine_alias) as machine_alias  FROM machine_master");
			// query.setParameter(2, version);

			List<Object[]> list = query.getResultList();

			for (int i = 0; i < list.size(); i++) {
				Object[] objects = list.get(i);
				MachineDTO oeeData = new MachineDTO();
				
				oeeData.setMachineId((BigDecimal)objects[0]);
				oeeData.setPlantName((String) objects[1]);
				oeeData.setLineId((BigDecimal) objects[2]);
				oeeData.setLineName((String) objects[3]);
				oeeData.setMachineAlias((String) objects[4]);
//				oeeData.setOnOverview((String) objects[4]);
				
				listData.add(oeeData);
			}
			return listData;
		} catch (NoResultException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listData;
	}
}
