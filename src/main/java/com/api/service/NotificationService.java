package com.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.dto.NotificationDto;
import com.api.exception.ResourceNotFoundException;
import com.api.model.Notification;
import com.api.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	public void sendNotification(Notification notification) {
		repository.save(notification);
	}
	
	public List<NotificationDto> getAllNotifications() {
		List<NotificationDto> notifications = repository.findAll()
				.stream()
				.map(notification -> mapper.map(notification, NotificationDto.class))
				.collect(Collectors.toList());
		
		if (notifications.isEmpty()) {
			throw new ResourceNotFoundException("There are no notifications");
		}
		
		return notifications;
	}
}
