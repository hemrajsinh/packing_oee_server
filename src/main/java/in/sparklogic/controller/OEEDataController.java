package in.sparklogic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sparklogic.params.MachineDTO;
import in.sparklogic.params.OEEDataDTO;
import in.sparklogic.params.ParamsDTO;
import in.sparklogic.service.OEEDataService;

@RestController
// @EnableAutoConfiguration
@Scope("request")
@RequestMapping("/oee")
public class OEEDataController {


	@Autowired
	OEEDataService oeeDataService;
	


	@PostMapping()
	public ResponseEntity<List<OEEDataDTO>> getMachineOverview(@RequestBody ParamsDTO params) {
		return ResponseEntity.ok(oeeDataService.getData(params.getDashboardType().equals("live") ? "line"+params.getLineId()+"_machine"+params.getMachineId()+"_overview_dashboard" : "line"+params.getLineId()+"_machine"+params.getMachineId()+"_overview_history_dashboard","machine", params));
	}
	
	@PostMapping(path = "lineOverview")
	public ResponseEntity<List<OEEDataDTO>> getLineOverview(@RequestBody ParamsDTO params) {
		return ResponseEntity.ok(oeeDataService.getData(params.getDashboardType().equals("live") ? "line"+params.getLineId()+"_line_overview_dashboard" : "line"+params.getLineId()+"_line_overview_history_dashboard", "line", params));
	}

	@GetMapping(path="machines")
	public ResponseEntity<List<MachineDTO>> getMachineMaster() {
		return ResponseEntity.ok(oeeDataService.getaMachineData());
	}
}
