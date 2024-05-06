package com.zerobase.tenki.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.zerobase.tenki.domain.Memo;

@SpringBootTest
@Transactional
class JdbcMemoRepositoryTest {
	@Autowired
	private JdbcMemoRepository jdbcMemoRepository;
	
	@Test
	void insertMemoTest() throws Exception {
		
		//given
		Memo newMemo = new Memo(1, "hello tenki!");
		
		//when
		jdbcMemoRepository.save(newMemo);
		
		//test
		Optional<Memo> result = jdbcMemoRepository.findById(1);
		assertEquals(result.get().getText(), "hello tenki!");
	}
		
	@Test
	void findAllMemoTest() throws Exception {
		//given, when
		List<Memo> memoList = jdbcMemoRepository.findAll();
		
		//test
		assertNotNull(memoList);
	}
}
