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
class JpaMemoRepositoryTest {

	@Autowired
	JpaMemoRepository jpaMemoRepository;
	
	@Test
	void insertMemoTest() throws Exception {
		//given
		Memo newMemo = new Memo(1, "hello tenki!");
		
		//when
		jpaMemoRepository.save(newMemo);
		
		//then
		List<Memo> memoList = jpaMemoRepository.findAll();
		assertTrue(memoList.size() > 0);
	}
	
	@Test
	void findByIdTest() throws Exception {
		//given
		Memo newMemo = new Memo(1, "hello tenki!");
		
		//when
		Memo memo = jpaMemoRepository.save(newMemo);
		System.out.println(memo.getId());
		
		//then
		Optional<Memo> result = jpaMemoRepository.findById(memo.getId());
		assertEquals(result.get().getText(), "hello tenki!");
		
	}
}
