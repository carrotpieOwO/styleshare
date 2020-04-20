package com.carrot.styleshare.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.carrot.styleshare.Utils;
import com.carrot.styleshare.model.product.Product;
import com.carrot.styleshare.model.style.dto.RespWriteDto;
import com.carrot.styleshare.model.tag.Tag;
import com.carrot.styleshare.model.user.User;
import com.carrot.styleshare.service.StyleService;
import com.carrot.styleshare.service.TagService;

@Controller
public class StyleController {
	@Value("${file.path}")
	private String fileRealPath;
	
	@Autowired
	private StyleService styleService;
	
	@Autowired
	private TagService tagService;
	
	// 화면이동
	//메인화면
	@GetMapping({ "", "/", "/list" })
	public String posts(Model model) {
		
		return "/style/list";
	}
	
	//글쓰기
	@GetMapping("/style/write")
	public String write(Model model) {
		
		return "/style/write";
	}
	
	//글쓰기 - 아이템 검색
	@PostMapping("/style/{item}")
	public  @ResponseBody String getNaverSearch(@PathVariable String item, Model model) {
		System.out.println("오긴오니");
		System.out.println(item);
		 String url = "https://openapi.naver.com/v1/search/shop.json?query="+item+"&display=10&start=1";
	        RestTemplate restTemplate = new RestTemplate();

	        HttpHeaders header = new HttpHeaders();
	        header.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
	        header.set("X-Naver-Client-Id","Z67LEXnLFW6Gxln_n7HU");
	        header.set("X-Naver-Client-Secret", "C9KR0USkp9");
	        

	        HttpEntity entity = new HttpEntity(header);

	        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        System.out.println(response.getBody());

		return response.getBody();
	}
	
	//글쓰기
	@PostMapping("/style/write")
	public @ResponseBody String write(@RequestParam MultipartFile image1, @RequestParam MultipartFile image2, @RequestParam MultipartFile image3, @RequestParam String content,  
			@RequestParam String title[], @RequestParam String image[], @RequestParam int lprice[], @RequestParam String link[], 
			@RequestParam String tags, @RequestParam int userId, @AuthenticationPrincipal User principal) {

		
		System.out.println("이미지:"+image1);
		System.out.println("이미지:"+image2);
		System.out.println("이미지:"+image3);
		
		UUID uuid = UUID.randomUUID();
		
		String uuidFilename1 = "";
		String uuidFilename2 = "";
		String uuidFilename3 = "";
		//String uuidFilenames = null;
		
		List<MultipartFile> images = new ArrayList<>();
		if(!image1.getOriginalFilename().equals("")) {
			images.add(image1);
		}
		if(!image2.getOriginalFilename().equals("")) {
			images.add(image2);
		}
		if(!image3.getOriginalFilename().equals("")) {
			images.add(image3);	
		}
				
		List<String> uuidFilenames = new ArrayList<>();

		for(int i=0; i<images.size(); i++) {	
			System.out.println(images.get(i).getOriginalFilename());
			if(images.get(i).getOriginalFilename()!=null || !images.get(i).getOriginalFilename().equals("")) {
				uuidFilenames.add (uuid+"_"+images.get(i).getOriginalFilename());
				Path filePath = Paths.get(fileRealPath+uuidFilenames.get(i));
				
				try {
					Files.write(filePath, images.get(i).getBytes());
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				continue;
			}
			
		}
		
			System.out.println("파일이름사이즈: "+uuidFilenames.size());
			
		RespWriteDto dto = new RespWriteDto();
		
		if(uuidFilenames.size()==1) {
			System.out.println("여기옴?");
			dto.setImage1(uuidFilenames.get(0));
			dto.setImage2("");
			dto.setImage3("");
		}else if(uuidFilenames.size()==2) {
			dto.setImage1(uuidFilenames.get(0));
			dto.setImage2(uuidFilenames.get(1));
			dto.setImage3("");
		}else if(uuidFilenames.size()==3) {
			dto.setImage1(uuidFilenames.get(0));
			dto.setImage2(uuidFilenames.get(1));
			dto.setImage3(uuidFilenames.get(2));
		}
		dto.setContent(content);
		dto.setUserId(userId);
		
		int result = styleService.write(dto);
		//모델 디티오에 담기
		
		int reviewId = dto.getId();
		
		Product pd = new Product();
		List<Product> pdList;
		
		for(int i=0; i<image.length; i++) {
			pd.setImage(image[i]);
		}
		
		for(int i=0; i<title.length; i++) {
			pd.setTitle(title[i]);
		}
		
		System.out.println(pd);
		
		
		List<String> tagList = Utils.tagParser(tags);
	
		for (String tag : tagList) {
			Tag t = new Tag();
			t.setTag(tag);
			t.setReviewId(reviewId);
			tagService.write(tag, reviewId);
		}

		
				
		StringBuffer sb = new StringBuffer();
		if(result==1) {
			sb.append("<script>");
			sb.append("alert('작성완료');");
			sb.append("location.href='/user/mypage/"+principal.getUsername()+"';");
			sb.append("</script>");
			return sb.toString();
			
		}else {
			sb.append("<script>");
			sb.append("alert('작성실패');");
			sb.append("history.back();");
			sb.append("</script>");
			return sb.toString();
		}	
		
		}
	
	
	
}
