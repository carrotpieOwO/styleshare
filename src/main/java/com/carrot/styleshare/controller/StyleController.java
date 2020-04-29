package com.carrot.styleshare.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.carrot.styleshare.Utils;
import com.carrot.styleshare.model.RespCM;
import com.carrot.styleshare.model.clipping.Clipping;
import com.carrot.styleshare.model.follow.Follow;
import com.carrot.styleshare.model.like.Likes;
import com.carrot.styleshare.model.product.dto.ReqProductDto;
import com.carrot.styleshare.model.style.dto.ReqAllDto;
import com.carrot.styleshare.model.style.dto.ReqLikeRankingDto;
import com.carrot.styleshare.model.style.dto.ReqUpdateDto;
import com.carrot.styleshare.model.style.dto.RespDetailDto;
import com.carrot.styleshare.model.style.dto.RespWriteDto;
import com.carrot.styleshare.model.tag.Tag;
import com.carrot.styleshare.model.user.User;
import com.carrot.styleshare.repository.ClippingRepository;
import com.carrot.styleshare.repository.FollowRepository;
import com.carrot.styleshare.repository.LikesRepository;
import com.carrot.styleshare.service.CommentService;
import com.carrot.styleshare.service.ProductService;
import com.carrot.styleshare.service.StyleService;
import com.carrot.styleshare.service.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class StyleController {
	@Value("${file.path}")
	private String fileRealPath;

	@Autowired
	private StyleService styleService;

	@Autowired
	private TagService tagService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private LikesRepository likesRepository;

	@Autowired
	private ClippingRepository clippingRepository;

	@Autowired
	private FollowRepository followRepository;

	// 화면이동
	// 메인화면
	@GetMapping({ "", "/", "/list" })
	public String posts(Model model) {
		List<ReqLikeRankingDto> likeRanks = styleService.allRank();
		List<ReqAllDto> feeds = styleService.findAll();
		
		//인기게시글
		for(int i=0; i<likeRanks.size(); i++) {
			likeRanks.get(i).setRank(i+1);
			 int likeCount = likesRepository.likeCount(likeRanks.get(i).getId());
			  likeRanks.get(i).setLikeCount(likeCount);
			  int clippingCount = clippingRepository.clippingCount(likeRanks.get(i).getId());
			  likeRanks.get(i).setClippingCount(clippingCount);
		}
		
		//전체 게시글
		for(int i=0; i<feeds.size(); i++) {
			 int likeCount = likesRepository.likeCount(feeds.get(i).getId());
			  feeds.get(i).setLikeCount(likeCount);
			  int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
			  feeds.get(i).setClippingCount(clippingCount);
			  System.out.println("보관:" +clippingCount);
		}
		
		model.addAttribute("likeRanks", likeRanks);
		model.addAttribute("feeds", feeds);

		return "/style/list";
	}
	
	//무한스크롤
	@GetMapping("/list/scrollDown/{bno}")
	public @ResponseBody List<ReqAllDto> scrollDown(@PathVariable int bno){
		//유저랭킹
	
		System.out.println("아이디값 "+bno);
		List<ReqAllDto> feeds = styleService.scrollDown(bno, bno);
		System.out.println(feeds);

		for(int i=0; i<feeds.size(); i++) {
		 int likeCount = likesRepository.likeCount(feeds.get(i).getId());
		  feeds.get(i).setLikeCount(likeCount);
		  int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
		  feeds.get(i).setClippingCount(clippingCount); 
		}
		
		return feeds;
	}
	
	// 메인화면-MEN
		@GetMapping({ "/men" })
		public String mens(Model model) {
			List<ReqLikeRankingDto> likeRanks = styleService.CategoryRank("남자");
			List<ReqAllDto> feeds = styleService.findCategory("남자");
			
			//인기게시글
			for(int i=0; i<likeRanks.size(); i++) {
				likeRanks.get(i).setRank(i+1);
				 int likeCount = likesRepository.likeCount(likeRanks.get(i).getId());
				  likeRanks.get(i).setLikeCount(likeCount);
				  int clippingCount = clippingRepository.clippingCount(likeRanks.get(i).getId());
				  likeRanks.get(i).setClippingCount(clippingCount);
			}
			
			//전체 게시글
			for(int i=0; i<feeds.size(); i++) {
				 int likeCount = likesRepository.likeCount(feeds.get(i).getId());
				  feeds.get(i).setLikeCount(likeCount);
				  int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
				  feeds.get(i).setClippingCount(clippingCount);
				  System.out.println("보관:" +clippingCount);
			}
			
			model.addAttribute("likeRanks", likeRanks);
			model.addAttribute("feeds", feeds);

			return "/style/men";
		}
		
		//무한스크롤
		@GetMapping("/men/scrollDown/{bno}")
		public @ResponseBody List<ReqAllDto> scrollDownMen(@PathVariable int bno){
			//유저랭킹
		
			System.out.println("아이디값 "+bno);
			List<ReqAllDto> feeds = styleService.scrollDownCategory(bno, bno, "남자");
			System.out.println(feeds);

			for(int i=0; i<feeds.size(); i++) {
			 int likeCount = likesRepository.likeCount(feeds.get(i).getId());
			  feeds.get(i).setLikeCount(likeCount);
			  int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
			  feeds.get(i).setClippingCount(clippingCount); 
			}
			
			return feeds;
		}
	
		// 메인화면-WOMEN
				@GetMapping({ "/women" })
				public String womens(Model model) {
					List<ReqLikeRankingDto> likeRanks = styleService.CategoryRank("여자");
					List<ReqAllDto> feeds = styleService.findCategory("여자");
					
					//인기게시글
					for(int i=0; i<likeRanks.size(); i++) {
						likeRanks.get(i).setRank(i+1);
						 int likeCount = likesRepository.likeCount(likeRanks.get(i).getId());
						  likeRanks.get(i).setLikeCount(likeCount);
						  int clippingCount = clippingRepository.clippingCount(likeRanks.get(i).getId());
						  likeRanks.get(i).setClippingCount(clippingCount);
					}
					
					//전체 게시글
					for(int i=0; i<feeds.size(); i++) {
						 int likeCount = likesRepository.likeCount(feeds.get(i).getId());
						  feeds.get(i).setLikeCount(likeCount);
						  int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
						  feeds.get(i).setClippingCount(clippingCount);
						  System.out.println("보관:" +clippingCount);
					}
					
					model.addAttribute("likeRanks", likeRanks);
					model.addAttribute("feeds", feeds);

					return "/style/women";
				}
				
				//무한스크롤
				@GetMapping("/women/scrollDown/{bno}")
				public @ResponseBody List<ReqAllDto> scrollDownWomen(@PathVariable int bno){
					//유저랭킹
				
					System.out.println("아이디값 "+bno);
					List<ReqAllDto> feeds = styleService.scrollDownCategory(bno, bno, "여자");
					System.out.println(feeds);

					for(int i=0; i<feeds.size(); i++) {
					 int likeCount = likesRepository.likeCount(feeds.get(i).getId());
					  feeds.get(i).setLikeCount(likeCount);
					  int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
					  feeds.get(i).setClippingCount(clippingCount); 
					}
					
					return feeds;
				}
				
				// 메인화면-KIDS
				@GetMapping({ "/kids" })
				public String kids(Model model) {
					List<ReqLikeRankingDto> likeRanks = styleService.CategoryRank("키즈");
					List<ReqAllDto> feeds = styleService.findCategory("키즈");
					
					//인기게시글
					for(int i=0; i<likeRanks.size(); i++) {
						likeRanks.get(i).setRank(i+1);
						 int likeCount = likesRepository.likeCount(likeRanks.get(i).getId());
						  likeRanks.get(i).setLikeCount(likeCount);
						  int clippingCount = clippingRepository.clippingCount(likeRanks.get(i).getId());
						  likeRanks.get(i).setClippingCount(clippingCount);
					}
					
					//전체 게시글
					for(int i=0; i<feeds.size(); i++) {
						 int likeCount = likesRepository.likeCount(feeds.get(i).getId());
						  feeds.get(i).setLikeCount(likeCount);
						  int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
						  feeds.get(i).setClippingCount(clippingCount);
						  System.out.println("보관:" +clippingCount);
					}
					
					model.addAttribute("likeRanks", likeRanks);
					model.addAttribute("feeds", feeds);

					return "/style/kids";
				}
				
				//무한스크롤
				@GetMapping("/kids/scrollDown/{bno}")
				public @ResponseBody List<ReqAllDto> scrollDownKids(@PathVariable int bno){
					//유저랭킹
				
					System.out.println("아이디값 "+bno);
					List<ReqAllDto> feeds = styleService.scrollDownCategory(bno, bno, "키즈");
					System.out.println(feeds);

					for(int i=0; i<feeds.size(); i++) {
					 int likeCount = likesRepository.likeCount(feeds.get(i).getId());
					  feeds.get(i).setLikeCount(likeCount);
					  int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
					  feeds.get(i).setClippingCount(clippingCount); 
					}
					
					return feeds;
				}
				
				
	// 글쓰기
	@GetMapping("/style/write")
	public String write(Model model) {

		return "/style/write";
	}

	// 글쓰기 - 아이템 검색
	@PostMapping("/style/{item}")
	public @ResponseBody String getNaverSearch(@PathVariable String item, Model model) {

		String url = "https://openapi.naver.com/v1/search/shop.json?query=" + item + "&display=5&start=1";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
		header.set("X-Naver-Client-Id", "Z67LEXnLFW6Gxln_n7HU");
		header.set("X-Naver-Client-Secret", "C9KR0USkp9");

		HttpEntity entity = new HttpEntity(header);

		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		return response.getBody();
	}

	// 글쓰기 - 아이템 검색(페이징)
	@PostMapping("/style/{item}/{startNum}")
	public @ResponseBody String getNaverSearchNext(@PathVariable String item, @PathVariable int startNum, Model model) {

		String url = "https://openapi.naver.com/v1/search/shop.json?query=" + item + "&display=5&start=" + startNum;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
		header.set("X-Naver-Client-Id", "Z67LEXnLFW6Gxln_n7HU");
		header.set("X-Naver-Client-Secret", "C9KR0USkp9");

		HttpEntity entity = new HttpEntity(header);

		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		return response.getBody();
	}

	// 디테일
	@GetMapping("/style/{styleId}")
	public String detail(Model model, @PathVariable int styleId, @AuthenticationPrincipal User principal) {
		RespDetailDto dto = styleService.detail(styleId);

		if (principal != null) {
			// 좋아요 여부 체크
			Likes like = likesRepository.findByUserIdAndstyleID(styleId, principal.getId());
			if (like != null) {
				dto.setLike(true);
			}

			// 북마크 추가 여부 체크
			Clipping clipping = clippingRepository.findByUserIdAndstyleID(styleId, principal.getId());
			if (clipping != null) {
				dto.setClipping(true);
			}

			// 팔로우 여부
			Follow follow = followRepository.findByFromUserAndToUser(principal.getId(), dto.getUserId());
			if (follow != null) {
				dto.setFollow(true);
			}
		}

		// 좋아요 카운트
		int likeCount = likesRepository.likeCount(styleId);
		dto.setLikeCount(likeCount);

		// 태그불러오기
		List<String> tags = tagService.tags(styleId);

		// 프로덕트 불러오기
		List<ReqProductDto> products = productService.products(styleId);

		model.addAttribute("style", dto);
		model.addAttribute("tags", tags);
		model.addAttribute("products", products);
		model.addAttribute("comments", commentService.list(styleId));

		return "/style/detail";
	}

	// 글수정
	@GetMapping("/style/modify/{id}")
	public String modify(Model model, @PathVariable int id, @AuthenticationPrincipal User principal) {
		RespDetailDto dto = styleService.detail(id);
		List<String> tags = tagService.tags(id);
		List<ReqProductDto> products = productService.products(id);
		model.addAttribute("style", dto);
		model.addAttribute("tags", tags);
		model.addAttribute("products", products);
		return "/style/modify";
	}

	// 글쓰기
	@PostMapping("/style/write")
	public @ResponseBody String write(@RequestParam MultipartFile image1, @RequestParam MultipartFile image2,
			@RequestParam MultipartFile image3, @RequestParam String content, @RequestParam int userId,
			@RequestParam String product, @RequestParam String tags, @AuthenticationPrincipal User principal)
			throws JsonMappingException, JsonProcessingException {

		System.out.println("글쓰기옴");
		System.out.println(product);

		// 이미지파일 처리
		UUID uuid = UUID.randomUUID();

		String uuidFilename1 = "";
		String uuidFilename2 = "";
		String uuidFilename3 = "";

		List<MultipartFile> images = new ArrayList<>();
		if (!image1.getOriginalFilename().equals("")) {
			images.add(image1);
		}
		if (!image2.getOriginalFilename().equals("")) {
			images.add(image2);
		}
		if (!image3.getOriginalFilename().equals("")) {
			images.add(image3);
		}

		// 이미지 파일 저장
		List<String> uuidFilenames = new ArrayList<>();

		for (int i = 0; i < images.size(); i++) {
			System.out.println(images.get(i).getOriginalFilename());
			if (images.get(i).getOriginalFilename() != null || !images.get(i).getOriginalFilename().equals("")) {
				uuidFilenames.add(uuid + "_" + images.get(i).getOriginalFilename());
				Path filePath = Paths.get(fileRealPath + uuidFilenames.get(i));

				try {
					Files.write(filePath, images.get(i).getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				continue;
			}

		}

		System.out.println("파일이름사이즈: " + uuidFilenames.size());

		// 스타일 insert
		RespWriteDto dto = new RespWriteDto();

		if (uuidFilenames.size() == 1) {
			System.out.println("여기옴?");
			dto.setImage1(uuidFilenames.get(0));
			dto.setImage2("");
			dto.setImage3("");
		} else if (uuidFilenames.size() == 2) {
			dto.setImage1(uuidFilenames.get(0));
			dto.setImage2(uuidFilenames.get(1));
			dto.setImage3("");
		} else if (uuidFilenames.size() == 3) {
			dto.setImage1(uuidFilenames.get(0));
			dto.setImage2(uuidFilenames.get(1));
			dto.setImage3(uuidFilenames.get(2));
		}
		dto.setContent(content);
		dto.setUserId(userId);

		int result = styleService.write(dto);

		// styleId 가져오기
		int styleId = dto.getId();
		System.out.println("글쓰기스타일아이디:" + styleId);

		// 태그 insert
		List<String> tagList = Utils.tagParser(tags);

		for (String tag : tagList) {
			System.out.println("여긴오니");
			Tag t = new Tag();
			t.setTag(tag);
			t.setStyleId(styleId);
			tagService.write(tag, styleId);
		}

		// 프로덕트 insert
		Gson gson = new Gson();

		Type collectionType = new TypeToken<Collection<ReqProductDto>>() {
		}.getType();
		Collection<ReqProductDto> enums = gson.fromJson(product, collectionType);
		System.out.println(enums);

		for (ReqProductDto pro : enums) {
			System.out.println(pro.getTitle());

			if (pro.getTitle().contains(",")) {
				int titleIdx = pro.getTitle().indexOf(",");
				System.out.println(titleIdx);
				String title = pro.getTitle().substring(0, titleIdx);
				pro.setTitle(title);
				System.out.println(title);
			}

			if (pro.getTitle().contains("<b>")) {
				int brandIdx = pro.getTitle().indexOf("</b>");
				String brand = pro.getTitle().substring(3, brandIdx);
				pro.setBrand(brand);
			}

			productService.write(pro.getImage(), pro.getTitle(), pro.getLink(), pro.getLprice(), styleId, userId,
					pro.getBrand(), pro.getProductId());
		}

		// 페이지 이동 처리
		StringBuffer sb = new StringBuffer();
		if (result == 1) {
			sb.append("<script>");
			sb.append("alert('작성완료');");
			sb.append("location.href='/style/" + styleId + "';");
			sb.append("</script>");
			return sb.toString();

		} else {
			sb.append("<script>");
			sb.append("alert('작성실패');");
			sb.append("history.back();");
			sb.append("</script>");
			return sb.toString();
		}

	}

	// 수정
	@PutMapping("/style/modify")
	public ResponseEntity<?> update(@RequestBody ReqUpdateDto dto, @AuthenticationPrincipal User principal) {
		System.out.println("dto:" + dto);
		//스타일ID 가져오기
		int styleId = dto.getId();

		//태그 새로 저장
		List<String> tagList = Utils.tagParser(dto.getTag());

		tagService.delete(styleId);

		for (String tag : tagList) {
			Tag t = new Tag();
			t.setTag(tag);
			t.setStyleId(styleId);
			tagService.write(tag, styleId);
		}

		//프로덕트 새로 저장
		productService.delete(styleId);
		ArrayList<ReqProductDto> products = dto.getProducts();
		
		 for(ReqProductDto product: products) { 
			 if(product.getTitle().contains(",")) {
				 int titleIdx = product.getTitle().indexOf(","); 
				 System.out.println(titleIdx);
				 String title = product.getTitle().substring(0, titleIdx);
				 product.setTitle(title); System.out.println(title); 
			 }
		 
		 	if(product.getTitle().contains("<b>")) { 
		 		int brandIdx = product.getTitle().indexOf("</b>"); 
		 		String brand = product.getTitle().substring(3, brandIdx); 
		 		product.setBrand(brand); 
		 	}
		 
		 	productService.write(product.getImage(), product.getTitle(),
		 	product.getLink(), product.getLprice(), styleId, principal.getId(),
		 	product.getBrand(), product.getProductId()); 
		 	
		 }
		 
		 //글수정
		int result = styleService.update(dto.getContent(), styleId);

		if (result == 1) {
			return new ResponseEntity<RespCM>(new RespCM(200, "ok"), HttpStatus.OK);
		} else if (result == -3) {
			return new ResponseEntity<RespCM>(new RespCM(403, "fail"), HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<RespCM>(new RespCM(400, "fail"), HttpStatus.BAD_REQUEST);

		}
	}
	
	//삭제하기
	@DeleteMapping("/style/delete/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) {
		int result = styleService.delete(id);
		if (result == 1) {
			return new ResponseEntity<RespCM>(new RespCM(200, "ok"), HttpStatus.OK);
		} else if (result == -3) {
			return new ResponseEntity<RespCM>(new RespCM(403, "fail"), HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<RespCM>(new RespCM(400, "fail"), HttpStatus.BAD_REQUEST);

		}

	}

	
}
