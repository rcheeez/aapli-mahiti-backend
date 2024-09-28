package com.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.service.ExcelService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin("${cors.allowed.origins}")
public class ExcelController {
	
	@Autowired
	private ExcelService service;

	@GetMapping("/download/excel")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> downloadExcel(HttpServletResponse response) throws IOException {
		service.generateExcelFile(response);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
