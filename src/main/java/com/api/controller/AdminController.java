package com.api.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.AdminDto;
import com.api.dto.AuthResponse;
import com.api.service.AdminService;

@RestController
@CrossOrigin("${cors.allowed.origins}")
@RequestMapping("/api/auth")
public class AdminController {

	@Autowired
	private AdminService service;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AdminDto admin) {
		AuthResponse response = service.loginAdmin(admin);
		return new ResponseEntity<AuthResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody AdminDto admin) {
		AuthResponse response = service.registerAdmin(admin);
		return new ResponseEntity<AuthResponse>(response, HttpStatus.CREATED);
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdminDto> showAdminByUsername(@PathVariable String username) {
		AdminDto admin = service.getAdminByUsername(username);
		return new ResponseEntity<AdminDto>(admin, HttpStatus.OK);
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<AdminDto>> showAllAdmins() {
		List<AdminDto> response = service.getAllAdmins();
		return new ResponseEntity<List<AdminDto>>(response, HttpStatus.OK);
	}

	@PutMapping("/update/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdminDto> updateAdmin(@RequestBody AdminDto dto, @PathVariable String username) {
		AdminDto admin = service.updateAdminByUsername(dto, username);
		return new ResponseEntity<AdminDto>(admin, HttpStatus.OK);
	}

	@PutMapping("/resetpassword/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdminDto> resetPasswordByUsername(@RequestBody AdminDto dto, @PathVariable String username) {
		AdminDto admin = service.resetPassword(dto, username);
		return new ResponseEntity<AdminDto>(admin, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteAdmin(@PathVariable int id) {
		Boolean response = service.deleteAdminById(id);
		return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}
}
