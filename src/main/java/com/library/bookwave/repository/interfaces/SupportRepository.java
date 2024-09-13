package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.bookwave.dto.QnaDTO;
import com.library.bookwave.repository.model.Answer;
import com.library.bookwave.repository.model.Faq;

@Mapper
public interface SupportRepository {

	// faq 추가
	public int createFaq(Faq faq);

	// faq 전체 조회
	public List<Faq> readAllFaq();

	// faq 아이디로 조회
	public Faq readFaqById(Integer id);

	// faq 갱신
	public int updateFaqById(Faq faq);

	// faq 삭제
	public int deleteFaqById(Integer id);

	// faq 카테고리 불러오기
	public List<String> readAllCategory();

	// qna 전체 조회
	public List<QnaDTO> readAllQna(@Param("limit") int limit, @Param("offset") int offset);

	// qna 아이디로 조회
	public QnaDTO readQnaById(Integer id);

	// qna 답변 (answer 추가)
	public int createAnswerByQid(Answer answer);

	// qna 카운트
	public int countAllQna();

	// qna 수정
	public int updateQnaById(Answer answer);

	// qna 검색
	public List<QnaDTO> findQnaByKeyword(@Param("keyword") String keyword, @Param("limit") int limit, @Param("offset") int offset);

	// qna 검색 카운트
	public int countQnaByKeyword();

}
