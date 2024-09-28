package com.api.dto;

import java.time.LocalDate;

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
public class PeopleDto {

	private int id;
	private String fullName;
	private String phoneNumber;
	private String address;
	private String zone;
	private LocalDate createdAt;
	private String createdBy;
}
