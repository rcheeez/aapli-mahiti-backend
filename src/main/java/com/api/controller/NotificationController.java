package com.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.NotificationDto;
import com.api.service.NotificationService;

@RestController
@CrossOrigin("${cors.allowed.origins}")
@RequestMapping("/api")
public class NotificationController {

	@Autowired
	private NotificationService service;
	
	@GetMapping("/notifications")
	public ResponseEntity<List<NotificationDto>> showAllNotifications() {
		List<NotificationDto> notifications = service.getAllNotifications();
		return new ResponseEntity<List<NotificationDto>>(notifications, HttpStatus.OK);
	}
}
