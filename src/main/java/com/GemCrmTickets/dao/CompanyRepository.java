package com.GemCrmTickets.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.GemCrmTickets.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	
	/* find all companies */
	@Query("select c from Company c where c.visibility = true order by c.id desc")
	public Page<Company> getAllCompanies(Pageable pageable);
	
	
	/*search company by name*/
	@Query("select c from Company c where c.name like %:key% order by c.created_at desc")
	public Page<Company> findCompanyByKey(@Param("key") String key, Pageable pageable);
	
	
	/* update / delete => company */
	@Modifying
	@Transactional
	@Query("update Company c set c.name = :n, c.email = :e, c.legal_status = :ls, c.logo = :l , c.turnover = :t, "
			+ "c.number_employees = :num_emp, c.sector = :sec, c.nationality = :na, c.visibility = :v, c.updated_at = :ud where c.id = :id")
	public void modifyCompany(
				@Param("n") String name,
				@Param("e") String email,
				@Param("ls") String legal_status,
				@Param("l") String logo,
				@Param("t") double turnover,
				@Param("num_emp") int number_employees,
				@Param("sec") String sector,
				@Param("na") String nationality,
				@Param("v") boolean visibility,
				@Param("ud") Date updated_at,
				@Param("id") int id
			);
	
	
}
