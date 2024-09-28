package com.api.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.PeopleDto;
import com.api.dto.PeopleResponse;
import com.api.service.PeopleService;
import com.api.utils.AppConstants;

@RestController
@CrossOrigin("${cors.allowed.origins}")
@RequestMapping("/api/people")
public class PeopleController {

	@Autowired
	private PeopleService service;
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PeopleResponse> showAllData(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
			) {
		PeopleResponse response = service.getAllPeopleData(pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<PeopleResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/alldata")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<PeopleDto>> showAllData() {
		List<PeopleDto> response = service.getAllData();
		return new ResponseEntity<List<PeopleDto>>(response, HttpStatus.OK);
	}

	@GetMapping("/count")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Integer> countAll() {
		Integer response = service.getAllCount();
		return new ResponseEntity<Integer>(response, HttpStatus.OK);
	}	

	@GetMapping("/count/{zone}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Integer> countByZone(@PathVariable String zone) {
		Integer response = service.getCountByZone(zone);
		return new ResponseEntity<Integer>(response, HttpStatus.OK);
	}

	@GetMapping("/count/date")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Integer> countBetweenTwoDates(@RequestParam String startDate, @RequestParam String endDate){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		LocalDate sDate = LocalDate.parse(startDate, formatter);
		LocalDate eDate = LocalDate.parse(endDate, formatter);
		Integer count = service.getAllCountsBetweenTwoDates(sDate, eDate);
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PeopleDto> getPeopleDataById(@PathVariable int id) {
		PeopleDto people = service.getPeopleById(id);
		return new ResponseEntity<PeopleDto>(people, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PeopleDto> savePeopleData(@RequestBody PeopleDto people) {
		PeopleDto peopleDto = service.addNewPeople(people);
		return new ResponseEntity<PeopleDto>(peopleDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/zone/{zone}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<PeopleDto>> getPeopleDataByZone(@PathVariable String zone) {
		List<PeopleDto> peoples = service.listPeopleDataByZone(zone);
		return new ResponseEntity<List<PeopleDto>>(peoples, HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PeopleDto> updatePeopleData(@RequestBody PeopleDto peopleDto, @PathVariable int id) {
		PeopleDto people = service.updatePeopleDataById(peopleDto, id);
		return new ResponseEntity<PeopleDto>(people, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Boolean> deletePeopleData(@PathVariable int id) {
		Boolean isDeleted = service.deletePeopleDataById(id);
		return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
	}
}
