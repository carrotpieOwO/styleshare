package com.carrot.styleshare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carrot.styleshare.model.ReturnCode;
import com.carrot.styleshare.model.user.User;
import com.carrot.styleshare.model.user.dto.ReqJoinDto;
import com.carrot.styleshare.model.user.dto.ReqPwdUpdateDto;
import com.carrot.styleshare.model.user.dto.RespListDto;
import com.carrot.styleshare.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private MyUserDetailService userDetailService;

	// 회원가입 - ID유효성 검사
	public int idCheck(String username) {
		int result = userRepository.findByUsername(username);
		if (result == 1) {
			return ReturnCode.아이디중복;
		} else {
			return ReturnCode.성공;
		}
	}
	
	// 회원가입
	@Transactional
	public int join(ReqJoinDto dto) {
		try {
				String encodePassword = passwordEncoder.encode(dto.getPassword());
				dto.setPassword(encodePassword);
				return userRepository.save(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();

		}
	}
	
	//프로필 수정
	public int updateProfile(int id, String profile, String introduction, String email, int height, String gender, String age, String insta, String site, User principal) {
		int result = userRepository.updateProfile(id, profile, introduction, email, height, gender, age, insta, site);

		if (result == 1) {
			principal.setProfile(profile);
			principal.setIntroduction(introduction);
			principal.setEmail(email);
			principal.setHeight(height);
			principal.setGender(gender);
			principal.setAge(age);
			principal.setInsta(insta);
			principal.setSite(site);
			return 1;
		} else {
			return -1;
		}

	}
	
	//비밀번호 확인
	public int pwdCertify(String username, String password) {
		User user = userRepository.authentication(username);		
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ReturnCode.오류;
        } else {
            return ReturnCode.성공;
        }
	}
	
	// 비밀번호 변경
		public int updatePassword(ReqPwdUpdateDto dto, User principal) {
			String encodePassword = passwordEncoder.encode(dto.getPassword());
			try {
				int result = userRepository.updatePassword(encodePassword, dto.getUsername());

				if (result == 1) {
					principal.setPassword(encodePassword);
					return ReturnCode.성공;
				} else {
					return ReturnCode.오류;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return ReturnCode.무반응;
			
		}
		
	//마이페이지 리스트
	public List<RespListDto> mypageList(String username, int userId){
		return userRepository.mypageList(username, userId);
	}
	
	//무한스크롤
	//마이페이지 리스트
		public List<RespListDto> scrollDownMypage(int id1, int id2, String username){
			return userRepository.scrollDownMypage(id1, id2, username);
		}
}
