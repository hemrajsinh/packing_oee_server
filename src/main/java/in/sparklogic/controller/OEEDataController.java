package in.sparklogic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sparklogic.params.OEEDataDTO;
import in.sparklogic.service.OEEDataService;

@RestController
// @EnableAutoConfiguration
@Scope("request")
@RequestMapping("/oee")
public class OEEDataController {


	@Autowired
	OEEDataService oeeDataService;
	


	@GetMapping()
	public ResponseEntity<List<OEEDataDTO>> getAllUser() {
		return ResponseEntity.ok(oeeDataService.getData());
	}


}
