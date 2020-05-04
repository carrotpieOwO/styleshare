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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
import com.carrot.styleshare.model.product.Product;
import com.carrot.styleshare.model.product.dto.ReqProductDto;
import com.carrot.styleshare.model.product.dto.ReqProductListDto;
import com.carrot.styleshare.model.product.dto.ReqSearchKeywordDto;
import com.carrot.styleshare.model.style.dto.ReqAllDto;
import com.carrot.styleshare.model.style.dto.ReqLikeRankingDto;
import com.carrot.styleshare.model.style.dto.ReqUpdateDto;
import com.carrot.styleshare.model.style.dto.RespDetailDto;
import com.carrot.styleshare.model.style.dto.RespWriteDto;
import com.carrot.styleshare.model.tag.Tag;
import com.carrot.styleshare.model.tag.dto.ReqSearchTagDto;
import com.carrot.styleshare.model.user.User;
import com.carrot.styleshare.model.user.dto.RespSearchDto;
import com.carrot.styleshare.model.user.dto.RespStyleListDto;
import com.carrot.styleshare.repository.ClippingRepository;
import com.carrot.styleshare.repository.FollowRepository;
import com.carrot.styleshare.repository.LikesRepository;
import com.carrot.styleshare.repository.UserRepository;
import com.carrot.styleshare.service.CommentService;
import com.carrot.styleshare.service.ProductService;
import com.carrot.styleshare.service.StyleService;
import com.carrot.styleshare.service.TagService;
import com.carrot.styleshare.service.UserService;
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
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
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

		// 인기게시글
		for (int i = 0; i < likeRanks.size(); i++) {
			likeRanks.get(i).setRank(i + 1);
			int likeCount = likesRepository.likeCount(likeRanks.get(i).getId());
			likeRanks.get(i).setLikeCount(likeCount);
			int clippingCount = clippingRepository.clippingCount(likeRanks.get(i).getId());
			likeRanks.get(i).setClippingCount(clippingCount);
		}
		
		// 전체 게시글
		for (int i = 0; i < feeds.size(); i++) {
			int likeCount = likesRepository.likeCount(feeds.get(i).getId());
			feeds.get(i).setLikeCount(likeCount);
			int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
			feeds.get(i).setClippingCount(clippingCount);
			System.out.println("보관:" + clippingCount);
		}
		
		//데이터랩
		String URL = "https://search.shopping.naver.com/best100v2/detail/kwd.nhn?catId=50000000&kwdType=KWD";
		Document doc = null;
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements elem = doc.select(".ranking_list li>span.txt>a");
		String value = elem.text();
		String[] values = elem.text().split(" ");

		for(String val:values) {
			System.out.println(val);
		}
		
		model.addAttribute("likeRanks", likeRanks);
		model.addAttribute("feeds", feeds);
		model.addAttribute("trends", values);

		return "/style/list";
	}

	// 무한스크롤
	@GetMapping("/list/scrollDown/{bno}")
	public @ResponseBody List<ReqAllDto> scrollDown(@PathVariable int bno) {
		System.out.println("아이디값 " + bno);
		List<ReqAllDto> feeds = styleService.scrollDown(bno, bno);
		System.out.println(feeds);

		for (int i = 0; i < feeds.size(); i++) {
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

		// 인기게시글
		for (int i = 0; i < likeRanks.size(); i++) {
			likeRanks.get(i).setRank(i + 1);
			int likeCount = likesRepository.likeCount(likeRanks.get(i).getId());
			likeRanks.get(i).setLikeCount(likeCount);
			int clippingCount = clippingRepository.clippingCount(likeRanks.get(i).getId());
			likeRanks.get(i).setClippingCount(clippingCount);
		}

		// 전체 게시글
		for (int i = 0; i < feeds.size(); i++) {
			int likeCount = likesRepository.likeCount(feeds.get(i).getId());
			feeds.get(i).setLikeCount(likeCount);
			int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
			feeds.get(i).setClippingCount(clippingCount);
			System.out.println("보관:" + clippingCount);
		}

		//데이터랩
		String URL = "https://search.shopping.naver.com/best100v2/detail/kwd.nhn?catId=50000169&kwdType=KWD";
		Document doc = null;
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements elem = doc.select(".ranking_list li>span.txt>a");
		String value = elem.text();
		String[] values = elem.text().split(" ");

		System.out.println(elem);
		for(String val:values) {
			System.out.println(val);
		}
				
		model.addAttribute("likeRanks", likeRanks);
		model.addAttribute("feeds", feeds);
		model.addAttribute("trends", values);

		return "/style/men";
	}

	  
	// 무한스크롤
	@GetMapping("/men/scrollDown/{bno}")
	public @ResponseBody List<ReqAllDto> scrollDownMen(@PathVariable int bno) {
		System.out.println("아이디값 " + bno);
		List<ReqAllDto> feeds = styleService.scrollDownCategory(bno, bno, "남자");
		System.out.println(feeds);

		for (int i = 0; i < feeds.size(); i++) {
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

		// 인기게시글
		for (int i = 0; i < likeRanks.size(); i++) {
			likeRanks.get(i).setRank(i + 1);
			int likeCount = likesRepository.likeCount(likeRanks.get(i).getId());
			likeRanks.get(i).setLikeCount(likeCount);
			int clippingCount = clippingRepository.clippingCount(likeRanks.get(i).getId());
			likeRanks.get(i).setClippingCount(clippingCount);
		}

		// 전체 게시글
		for (int i = 0; i < feeds.size(); i++) {
			int likeCount = likesRepository.likeCount(feeds.get(i).getId());
			feeds.get(i).setLikeCount(likeCount);
			int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
			feeds.get(i).setClippingCount(clippingCount);
			System.out.println("보관:" + clippingCount);
		}
		
		//데이터랩
		String URL = "https://search.shopping.naver.com/best100v2/detail/kwd.nhn?catId=50000167&kwdType=KWD";
		Document doc = null;
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements elem = doc.select(".ranking_list li>span.txt>a");
		String value = elem.text();
		String[] values = elem.text().split(" ");

		System.out.println(elem);
		for(String val:values) {
			System.out.println(val);
		}
		model.addAttribute("likeRanks", likeRanks);
		model.addAttribute("feeds", feeds);
		model.addAttribute("trends", values);

		return "/style/women";
	}

	// 무한스크롤
	@GetMapping("/women/scrollDown/{bno}")
	public @ResponseBody List<ReqAllDto> scrollDownWomen(@PathVariable int bno) {
		System.out.println("아이디값 " + bno);
		List<ReqAllDto> feeds = styleService.scrollDownCategory(bno, bno, "여자");
		System.out.println(feeds);

		for (int i = 0; i < feeds.size(); i++) {
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

		// 인기게시글
		for (int i = 0; i < likeRanks.size(); i++) {
			likeRanks.get(i).setRank(i + 1);
			int likeCount = likesRepository.likeCount(likeRanks.get(i).getId());
			likeRanks.get(i).setLikeCount(likeCount);
			int clippingCount = clippingRepository.clippingCount(likeRanks.get(i).getId());
			likeRanks.get(i).setClippingCount(clippingCount);
		}

		// 전체 게시글
		for (int i = 0; i < feeds.size(); i++) {
			int likeCount = likesRepository.likeCount(feeds.get(i).getId());
			feeds.get(i).setLikeCount(likeCount);
			int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
			feeds.get(i).setClippingCount(clippingCount);
			System.out.println("보관:" + clippingCount);
		}
		
		//데이터랩
		String URL = "https://search.shopping.naver.com/best100v2/detail/kwd.nhn?catId=50000138&kwdType=KWD";
		Document doc = null;
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements elem = doc.select(".ranking_list li>span.txt>a");
		String value = elem.text();
		String[] values = elem.text().split(" ");

		System.out.println(elem);
		for(String val:values) {
			System.out.println(val);
		}
				
		model.addAttribute("likeRanks", likeRanks);
		model.addAttribute("feeds", feeds);
		model.addAttribute("trends", values);

		return "/style/kids";
	}

	// 무한스크롤
	@GetMapping("/kids/scrollDown/{bno}")
	public @ResponseBody List<ReqAllDto> scrollDownKids(@PathVariable int bno) {
		// 유저랭킹

		System.out.println("아이디값 " + bno);
		List<ReqAllDto> feeds = styleService.scrollDownCategory(bno, bno, "키즈");
		System.out.println(feeds);

		for (int i = 0; i < feeds.size(); i++) {
			int likeCount = likesRepository.likeCount(feeds.get(i).getId());
			feeds.get(i).setLikeCount(likeCount);
			int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
			feeds.get(i).setClippingCount(clippingCount);
		}

		return feeds;
	}

	// 메인화면-타임라인
	@GetMapping("/timeline/{userId}")
	public String feeds(@PathVariable int userId, Model model) {
		List<ReqAllDto> feeds = styleService.findByFollow(userId);

		for (int i = 0; i < feeds.size(); i++) {
			int likeCount = likesRepository.likeCount(feeds.get(i).getId());
			feeds.get(i).setLikeCount(likeCount);
			int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
			feeds.get(i).setClippingCount(clippingCount);
		}

		model.addAttribute("feeds", feeds);
		return "style/timeline";
	}

	// 무한스크롤
	@GetMapping("/timeline/scrollDown/{bno}/{userId}")
	public @ResponseBody List<ReqAllDto> scrollDownTimeline(@PathVariable int bno, @PathVariable int userId) {
		System.out.println("아이디값 " + bno);
		List<ReqAllDto> feeds = styleService.scrollDownFollow(bno, bno, userId);
		System.out.println(feeds);

		for (int i = 0; i < feeds.size(); i++) {
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

	// 검색
	@GetMapping("/search")
	public String search(Model model, @RequestParam String searchMenu, @RequestParam String searchContent) {
		System.out.println(searchMenu);
		if (searchMenu.equals("태그")) {
			List<Tag> tagKeywords = tagService.selectDistinctTag(searchContent);
			List<ReqSearchTagDto> tags = tagService.searchByTag(searchContent);
			for (ReqSearchTagDto tag : tags) {
				int likeCount = likesRepository.likeCount(tag.getId());
				tag.setLikeCount(likeCount);
				int clippingCount = clippingRepository.clippingCount(tag.getId());
				tag.setClippingCount(clippingCount);
			}
			model.addAttribute("searchContent", searchContent);
			model.addAttribute("tagKeywords", tagKeywords);
			model.addAttribute("tags", tags);
			System.out.println(tags);
			return "search/tag";
		}
		
		if (searchMenu.equals("키워드")) {
			List<Product> proKeywords = productService.selectDistinctProduct(searchContent);
			List<ReqSearchKeywordDto> keywords = productService.searchByKeyword(searchContent);
			for (ReqSearchKeywordDto keyword : keywords) {
				int likeCount = likesRepository.likeCount(keyword.getId());
				keyword.setLikeCount(likeCount);
				int clippingCount = clippingRepository.clippingCount(keyword.getId());
				keyword.setClippingCount(clippingCount);
			}
			model.addAttribute("searchContent", searchContent);
			model.addAttribute("proKeywords", proKeywords);
			model.addAttribute("keywords", keywords);
			System.out.println(keywords);
			return "search/keyword";
		}
		
		if (searchMenu.equals("전체")) {
			//상품검색
			String url = "https://openapi.naver.com/v1/search/shop.json?query=" + searchContent + "&display=4&start=1";
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
			header.set("X-Naver-Client-Id", "Z67LEXnLFW6Gxln_n7HU");
			header.set("X-Naver-Client-Secret", "C9KR0USkp9");

			HttpEntity entity = new HttpEntity(header);

			HttpEntity<ReqProductListDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, ReqProductListDto.class);
			
			ReqProductListDto body = response.getBody();
			ArrayList<ReqProductDto> products = body.getItems();

			for (ReqProductDto pro : products) {
				if (pro.getTitle().contains(",")) {
					int titleIdx = pro.getTitle().indexOf(",");
					System.out.println(titleIdx);
					String title = pro.getTitle().substring(0, titleIdx);
					pro.setTitle(title);
					System.out.println(title);
				}
				
			}
	
			//유저검색
			List<RespSearchDto> users = userService.searchByUsername(searchContent, searchContent);
			for (RespSearchDto user : users) {
			
				List<RespStyleListDto> images = userService.imageByUsername(user.getId());
				System.out.println(images);
				if(images.size()>=1) {
					user.setImage1(images.get(0).getImage1());
				}
				if(images.size()>=2) {
					user.setImage2(images.get(1).getImage1());
				}
				if(images.size()>=3) {
					user.setImage3(images.get(2).getImage1());
				}
				if(images.size()==4) {
					user.setImage4(images.get(3).getImage1());
				}
				
				int follower = followRepository.followerCount(user.getId());
				user.setFollower(follower);
			}
			
			//게시글 검색
			List<ReqAllDto> feeds = styleService.searchAll(searchContent, searchContent, searchContent);
			for (int i = 0; i < feeds.size(); i++) {
				User user = userRepository.findById(feeds.get(i).getUserId());
				String username = user.getUsername();
				feeds.get(i).setUsername(username);
				int likeCount = likesRepository.likeCount(feeds.get(i).getId());
				feeds.get(i).setLikeCount(likeCount);
				int clippingCount = clippingRepository.clippingCount(feeds.get(i).getId());
				feeds.get(i).setClippingCount(clippingCount);
			}

			model.addAttribute("searchContent", searchContent);
			model.addAttribute("products", products);
			model.addAttribute("users", users);
			model.addAttribute("feeds", feeds);

			return "search/all";
		}
		return "/";

	}
	
	// 상품 더보기
	@PostMapping("/search/{searchContent}/{startNum}")
	public @ResponseBody String itemSearchMore(@PathVariable String searchContent, @PathVariable int startNum, Model model) {

		String url = "https://openapi.naver.com/v1/search/shop.json?query=" + searchContent + "&display=4&start=" + startNum;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
		header.set("X-Naver-Client-Id", "Z67LEXnLFW6Gxln_n7HU");
		header.set("X-Naver-Client-Secret", "C9KR0USkp9");

		HttpEntity entity = new HttpEntity(header);

		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		return response.getBody();
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
		List<ReqLikeRankingDto> ranks = styleService.detailRank(dto.getUserId());

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

		// 글쓴이 인기 게시글 리스트

		for (int i = 0; i < ranks.size(); i++) {
			ranks.get(i).setRank(i + 1);
			int detailLike = likesRepository.likeCount(ranks.get(i).getId());
			ranks.get(i).setLikeCount(detailLike);
			int detailClipping = clippingRepository.clippingCount(ranks.get(i).getId());
			ranks.get(i).setClippingCount(detailClipping);
		}

		model.addAttribute("style", dto);
		model.addAttribute("tags", tags);
		model.addAttribute("products", products);
		model.addAttribute("comments", commentService.list(styleId));
		model.addAttribute("ranks", ranks);

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
				int keywordIdx = pro.getTitle().indexOf("</b>");
				String keyword = pro.getTitle().substring(3, keywordIdx);
				pro.setKeyword(keyword);
			}

			productService.write(pro.getImage(), pro.getTitle(), pro.getLink(), pro.getLprice(), styleId, userId,
					pro.getKeyword(), pro.getProductId());
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
		// 스타일ID 가져오기
		int styleId = dto.getId();

		// 태그 새로 저장
		List<String> tagList = Utils.tagParser(dto.getTag());

		tagService.delete(styleId);

		for (String tag : tagList) {
			Tag t = new Tag();
			t.setTag(tag);
			t.setStyleId(styleId);
			tagService.write(tag, styleId);
		}

		// 프로덕트 새로 저장
		productService.delete(styleId);
		ArrayList<ReqProductDto> products = dto.getProducts();

		for (ReqProductDto product : products) {
			if (product.getTitle().contains(",")) {
				int titleIdx = product.getTitle().indexOf(",");
				System.out.println(titleIdx);
				String title = product.getTitle().substring(0, titleIdx);
				product.setTitle(title);
				System.out.println(title);
			}

			if (product.getTitle().contains("<b>")) {
				int keywordIdx = product.getTitle().indexOf("</b>");
				String keyword = product.getTitle().substring(3, keywordIdx);
				product.setKeyword(keyword);
			}

			productService.write(product.getImage(), product.getTitle(), product.getLink(), product.getLprice(),
					styleId, principal.getId(), product.getKeyword(), product.getProductId());

		}

		// 글수정
		int result = styleService.update(dto.getContent(), styleId);

		if (result == 1) {
			return new ResponseEntity<RespCM>(new RespCM(200, "ok"), HttpStatus.OK);
		} else if (result == -3) {
			return new ResponseEntity<RespCM>(new RespCM(403, "fail"), HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<RespCM>(new RespCM(400, "fail"), HttpStatus.BAD_REQUEST);

		}
	}

	// 삭제하기
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
