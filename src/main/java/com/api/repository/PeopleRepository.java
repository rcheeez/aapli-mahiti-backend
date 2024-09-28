package com.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.model.People;

@Repository
public interface PeopleRepository extends JpaRepository<People, Integer> {
	
	List<People> findByZone(String zone);

	@Query("SELECT COUNT(p) FROM People p")
	Integer getTotalCountOfPeopleData();

	@Query("SELECT COUNT(p) FROM People p WHERE p.zone = :zone")
	Integer getCountByZone(@Param("zone")String zone);

	@Query("SELECT COUNT(p) FROM People p WHERE p.createdAt BETWEEN :startDate AND :endDate")
	Integer getTotalCountbetweenTwoDates(@Param("startDate") LocalDate starDate, @Param("endDate") LocalDate enDate);

}
