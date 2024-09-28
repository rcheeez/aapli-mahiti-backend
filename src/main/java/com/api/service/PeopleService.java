package com.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.api.dto.PeopleDto;
import com.api.dto.PeopleResponse;
import com.api.exception.ResourceNotFoundException;
import com.api.model.Notification;
import com.api.model.People;
import com.api.repository.PeopleRepository;

@Service
public class PeopleService {

	@Autowired
	private PeopleRepository repository;
	
	@Autowired
	private NotificationService service;
	
	@Autowired
	private ModelMapper mapper;
	
	public PeopleResponse getAllPeopleData(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<People> peoples = repository.findAll(pageable);
		
		List<People> listOfPeoples = peoples.getContent();
		
		List<PeopleDto> content = listOfPeoples.stream().map(people -> mapper.map(people, PeopleDto.class)).collect(Collectors.toList());
		
		PeopleResponse response = new PeopleResponse();
		response.setContent(content);
		response.setPageNo(peoples.getNumber());
		response.setPageSize(peoples.getSize());
		response.setTotalElements(peoples.getTotalElements());
		response.setTotalPages(peoples.getTotalPages());
		response.setLast(peoples.isLast());
		
		return response;
	}

	public List<PeopleDto> getAllData() {
		List<People> peoples = repository.findAll();
		List<PeopleDto> content = peoples.stream().map(people -> mapper.map(people,PeopleDto.class)).collect(Collectors.toList());
		if (content.isEmpty()) {
			throw new ResourceNotFoundException("Data Not Available");
		}
		return content;
	}
	
	public List<PeopleDto> listPeopleDataByZone(String zone) {
		List<People> peoples = repository.findByZone(zone);
		if (peoples.isEmpty()) {
			throw new ResourceNotFoundException("There is no data in this zone: "+zone);
		}
		List<PeopleDto> peopleList = peoples.stream().map(people -> mapper.map(people, PeopleDto.class)).collect(Collectors.toList());
		return peopleList;
	}

	public Integer getAllCount() {
		Integer count = repository.getTotalCountOfPeopleData();
		if (count == 0) {
			return 0;
		}
		return count;
	}

	public Integer getCountByZone(String zone) {
		Integer count = repository.getCountByZone(zone);
		if (count == 0) {
			return 0;
		}
		return count;
	}

	public Integer getAllCountsBetweenTwoDates(LocalDate starDate, LocalDate enDate) {
		Integer count = repository.getTotalCountbetweenTwoDates(starDate, enDate);
		if (count == 0) {
			return 0;
		}
		return count;
	}
	
	public PeopleDto addNewPeople(PeopleDto peopleDto) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = "";
		
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			username = userDetails.getUsername();
		} else {
			throw new IllegalStateException("User not authenticated!");
		}
		
		People people = mapper.map(peopleDto, People.class);
		people.setCreatedAt(LocalDate.now());
		people.setCreatedBy(username);
		
		PeopleDto newPerson = mapper.map(repository.save(people), PeopleDto.class);
		
		String message = "New Data has been added by "+username;
		Notification notification = new Notification();
		notification.setMessage(message);
		notification.setTimestamp(LocalDate.now());
		service.sendNotification(notification);
	
		return newPerson;
	}
	
	public PeopleDto getPeopleById(int id) {
		People people = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Person's Details Not Found with this Id: "+id));
		return mapper.map(people, PeopleDto.class);
	}
	
	public PeopleDto updatePeopleDataById(PeopleDto peopleDto, int id) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = "";
		
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			username = userDetails.getUsername();
		} else {
			throw new IllegalStateException("User not authenticated!");
		}
		
		People people = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Person's Details Not Found with this Id: "+id));
		
		people.setFullName(peopleDto.getFullName());
		people.setPhoneNumber(peopleDto.getPhoneNumber());
		people.setAddress(peopleDto.getAddress());
		people.setZone(peopleDto.getZone());
		people.setCreatedAt(LocalDate.now());
		
		PeopleDto updatedPeople = mapper.map(repository.save(people), PeopleDto.class);
		
		String message = people.getFullName()+ "'s data has been updated by "+username;
		Notification notification = new Notification();
		notification.setMessage(message);
		notification.setTimestamp(LocalDate.now());
		service.sendNotification(notification);
		
		return updatedPeople;
	}
	
	public boolean deletePeopleDataById(int id) {
		People people = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Person's Details Not Found with this Id: "+id));
		if (people != null) {
			repository.delete(people);
			return true;
		}
		return false;
	}
}
