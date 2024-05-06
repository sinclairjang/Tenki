package com.zerobase.tenki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.tenki.domain.Memo;

@Repository
public interface JpaMemoRepository extends JpaRepository<Memo, Integer> {
	
}
