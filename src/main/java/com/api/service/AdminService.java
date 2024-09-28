package com.api.service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.dto.AdminDto;
import com.api.dto.AuthResponse;
import com.api.exception.BadRequestException;
import com.api.exception.ResourceNotFoundException;
import com.api.model.Admin;
import com.api.repository.AdminRepository;
import com.api.security.JwtTokenProvider;

@Service
public class AdminService {

	@Autowired
	private AdminRepository repository;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtTokenProvider provider;

	@Autowired
	private ModelMapper mapper;
	
	
	public AuthResponse loginAdmin(AdminDto admin) {
		Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
				admin.getUsername(), admin.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = provider.generateToken(authentication);
		
		return new AuthResponse(token, "Logged In Successfully!");
	}
	
	public AuthResponse registerAdmin(AdminDto dto) {
		
		if (repository.existsByUsername(dto.getUsername())) {
			throw new BadRequestException("Username already exists!");
		}
		
		Admin admin = new Admin();
		admin.setUsername(dto.getUsername());
		admin.setPassword(encoder.encode(dto.getPassword()));
		
		repository.save(admin);
		return new AuthResponse("", "User Added Successfully!");
	}

	public AdminDto getAdminByUsername(String username) {
		Admin admin = repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Admin User Not Found wih this Username: "+ username));
		return mapper.map(admin, AdminDto.class);
	}

	public List<AdminDto> getAllAdmins() {
		List<Admin> admins = repository.findAll();
		List<AdminDto> dtos = admins.stream().map(admin -> mapper.map(admin, AdminDto.class)).collect(Collectors.toList());
		if (dtos.isEmpty()) {
			throw new ResourceNotFoundException("There are no admins");
		}
		return dtos;
	}

	public AdminDto updateAdminByUsername(AdminDto dto, String username) {
		Admin admin = repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Admin User Not Found wih this Username: "+ username));
		
		admin.setUsername(dto.getUsername());

		AdminDto updatedAdmin = mapper.map(repository.save(admin), AdminDto.class);
		return updatedAdmin;
	}

	public AdminDto resetPassword(AdminDto dto, String username) {
		Admin admin = repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Admin User Not Found wih this Username: "+ username));
		admin.setPassword(encoder.encode(dto.getPassword()));
		AdminDto updatedAdmin = mapper.map(repository.save(admin), AdminDto.class);
		return updatedAdmin;
	}

	public Boolean deleteAdminById(int id) {
		Admin admin = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
		if (admin != null) {
			repository.delete(admin);
			return true;
		}
		return false;
	}
}
