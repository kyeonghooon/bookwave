package com.library.bookwave.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.bookwave.repository.model.Faq;

@Mapper
public interface SupportRepository {

	public int createFaq(Faq faq);

	public List<Faq> readAllFaq();

	public Faq readFaqById(Integer id);

	public int updateFaqById(Faq faq);

	public int deleteFaqById(Integer id);

	public List<String> readAllCategory();
}
