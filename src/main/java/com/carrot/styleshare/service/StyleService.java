package com.carrot.styleshare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrot.styleshare.model.style.dto.ReqAllDto;
import com.carrot.styleshare.model.style.dto.ReqLikeRankingDto;
import com.carrot.styleshare.model.style.dto.RespDetailDto;
import com.carrot.styleshare.model.style.dto.RespWriteDto;
import com.carrot.styleshare.repository.StyleRepository;

@Service
public class StyleService {
	@Autowired
	private StyleRepository styleRepository;

	// 글쓰기
	public int write(RespWriteDto dto) {
		return styleRepository.write(dto);
	}
	
	//상세보기
	public RespDetailDto detail(int id) {
		return styleRepository.findByStyleId(id);
	}
	
	//수정하기
	public int update(String content, int id) {
		return styleRepository.update(content, id);
	}
	
	//삭제하기
	public int delete(int id) {
		return styleRepository.delete(id);
	}
	
	//전체 인기 게시글
	public List<ReqLikeRankingDto> allRank(){
		return styleRepository.findAllRanking();
	}
	
	//전체 피드 불러오기
	public List<ReqAllDto> findAll(){
		return styleRepository.findAll();
	}
	
	//전체 피드 무한스크롤
	public List<ReqAllDto> scrollDown(int id1, int id2){
		return styleRepository.scrollDown(id1, id2);
	}
	
	//카테고리 인기 게시글
	public List<ReqLikeRankingDto> CategoryRank(String gender){
		return styleRepository.findCategoryRanking(gender);
	}
	
	//카테고리 피드 불러오기
	public List<ReqAllDto> findCategory(String gender){
		return styleRepository.findCategory(gender);
	}	
	
	//카테고리 무한스크롤
	public List<ReqAllDto> scrollDownCategory(int id1, int id2, String gender){
		return styleRepository.scrollDownCategory(id1, id2, gender);
	}
	
	//팔로우 피드 불러오기
	public List<ReqAllDto> findByFollow(int userId){
		return styleRepository.findByFollow(userId);
	}
	
	//팔로우 무한스크롤
	public List<ReqAllDto> scrollDownFollow(int id1, int id2, int userId){
		return styleRepository.scrollDownFollow(id1, id2, userId);
	}
		
	//디테일 인기 게시글
	public List<ReqLikeRankingDto> detailRank(int userId){
		return styleRepository.findDetailRanking(userId);
	}
	
	//전체검색	
	public List<ReqAllDto> searchAll(String content, String tag, String keyword){
		return styleRepository.searchAll(content, tag, keyword);
	}
}
