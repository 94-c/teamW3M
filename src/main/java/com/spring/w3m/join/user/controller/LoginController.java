package com.spring.w3m.join.user.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.spring.w3m.cart.user.service.CartService;
import com.spring.w3m.cart.user.vo.CartVO;
import com.spring.w3m.join.user.service.KakaoAccessToken;
import com.spring.w3m.join.user.service.KakaoUserInfo;
import com.spring.w3m.join.user.service.NaverLoginBO;
import com.spring.w3m.join.user.service.UserService;
import com.spring.w3m.join.user.vo.UserVO;

@Controller
public class LoginController {
	/* NaverLoginBO */
	private NaverLoginBO naverLoginBO;
	private String apiResult = null;

	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartSevice;

	@Autowired
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}

	// 로그인 첫 화면 요청 메소드
	@RequestMapping(value = "/loginForm.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(Model model, HttpSession session, @ModelAttribute UserVO vo) {
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		// https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
		// redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
		// 네이버
		if (vo.getReturnURL() != null) {
			if (vo.getReturnURL().equals("cart")) { // 비 로그인으로 장바구니 진입 시 로그인페이지 진입 후 성공하면 장바구니로 바로이동
				session.setAttribute("returnURL", "cart");
			}
		}
		model.addAttribute("url", naverAuthUrl);
		return "login/login";
	}

	@RequestMapping(value = "/selectJoin.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String login_insert(Model model, HttpSession session) {
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		// https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
		// redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
		// 네이버
		model.addAttribute("url", naverAuthUrl);
		return "join/selectJoin";
	}

	// 네이버 로그인 성공시 callback호출 메소드
	@RequestMapping(value = "/callback.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(Model model, @RequestParam String code, @RequestParam String state, CartVO cartVO,
			HttpSession session) throws IOException, ParseException {
		OAuth2AccessToken oauthToken;
		oauthToken = naverLoginBO.getAccessToken(session, code, state);
		// 1. 로그인 사용자 정보를 읽어온다.
		apiResult = naverLoginBO.getUserProfile(oauthToken); // String형식의 json데이터
		/**
		 * apiResult json 구조 {"resultcode":"00", "message":"success",
		 * "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}}
		 **/
		// 2. String형식인 apiResult를 json형태로 바꿈
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;
		// 3. 데이터 파싱
		// Top레벨 단계 _response 파싱
		JSONObject response_obj = (JSONObject) jsonObj.get("response");

		UserVO vo = new UserVO();
		vo.setUser_id((String) response_obj.get("id"));
		model.addAttribute("result", apiResult);
		// 가입되어있는지 확인
		int check = userService.SNSidCheck(vo.getUser_id());
		if (check > 0) {
			UserVO user = userService.getUser(vo);
			if (user.getUser_state().equals("정지")) {
				session.setAttribute("msg", "suspended");
				return "login/login";
			}
			if (user.getUser_state().equals("탈퇴")) {
				session.setAttribute("msg", "delete");
				return "login/login";
			}

			session.setAttribute("cart", cartSevice.cart_Cnt(cartVO));
			session.setAttribute("userVO", user);
			session.setAttribute("login_state", "login");

			return "login/loginSuccess";
		} else {

			vo.setUser_name((String) response_obj.get("name"));
			vo.setUser_email((String) response_obj.get("email"));
			vo.setUser_sns_naver("true");
			String gender = (String) response_obj.get("gender");
			if (gender.equals("M")) {
				gender = "남자";
			} else {
				gender = "여자";
			}
			String fullBirthday = (String) response_obj.get("birthyear") + "-" + (String) response_obj.get("birthday");
			vo.setUser_gender(gender);
			vo.setUser_birthday(fullBirthday);
			session.setAttribute("userVO", vo); // 세션 생성
			return "join/insertSNS";
		}
	}

	@RequestMapping(value = "/kakaoCallback.do", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String kakaoLogin(@RequestParam("code") String code, Model model, HttpSession session, CartVO cartVO) {
		JsonNode accessToken;

		// JsonNode트리형태로 토큰받아온다
		JsonNode jsonToken = KakaoAccessToken.getKakaoAccessToken(code);
		// 여러 json객체 중 access_token을 가져온다
		accessToken = jsonToken.get("access_token");
		session.setAttribute("token", jsonToken);

		// access_token을 통해 사용자 정보 요청
		JsonNode userInfo = KakaoUserInfo.getKakaoUserInfo(accessToken);
		UserVO vo = new UserVO();
		// Get id
		String id = userInfo.path("id").asText();
		String name = null;
		String email = null;
		String gender = null;
		vo.setUser_id(id);
		// 유저정보 카카오에서 가져오기 Get properties
		JsonNode properties = userInfo.path("properties");
		JsonNode kakao_account = userInfo.path("kakao_account");

		name = properties.path("nickname").asText();
		email = kakao_account.path("email").asText();
		gender = kakao_account.path("gender").asText();
		if (gender.equals("male")) {
			gender = "남자";
		} else
			gender = "여자";

		int check = userService.SNSidCheck(vo.getUser_id());
		if (check > 0) {
			UserVO user = userService.getUser(vo);
			if (user.getUser_state().equals("정지")) {
				session.setAttribute("msg", "suspended");
				return "login/login";
			}
			if (user.getUser_state().equals("탈퇴")) {
				session.setAttribute("msg", "delete");
				return "login/login";
			}
			session.setAttribute("userVO", user);
			session.setAttribute("login_state", "login");
			session.setAttribute("cart", cartSevice.cart_Cnt(cartVO));
			return "login/loginSuccess";
		} else {
			vo.setUser_name(name);

			vo.setUser_email(email);
			vo.setUser_gender(gender);
			vo.setUser_sns_kakao("true");
			session.setAttribute("userVO", vo); // 세션 생성
			return "join/insertSNSkakao";
		}

	}

}
