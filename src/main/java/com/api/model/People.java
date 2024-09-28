package com.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "people_data")
public class People {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "people_id")
	private int id;
	
	@Column(nullable = false)
	private String fullName;
	
	@Column(nullable = false)
	private String phoneNumber;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String zone;
	
	@Column(nullable = false)
	private LocalDate createdAt = LocalDate.now();
	
	@Column(nullable = false)
	private String createdBy;
}
