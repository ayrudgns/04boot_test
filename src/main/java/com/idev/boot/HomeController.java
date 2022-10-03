package com.idev.boot;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name. 요청 처리 메소드 :
	 * request handler method (어떤 요청이 들어오는지에 따라 url과 메소드가 매핑되는 형식)
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home"; // 의미 : view 파일이 home.jsp이다.
	}

	// 요청 메소드에 따라 구분하면?
	// @GetMapping : Get 요청 처리
	// @PostMapping : Post 요청 처리
	// @RequestMapping : 요청 url을 지정하는 어노테이션, Get / Post 둘 다 가능하다.

	// (1) ViewResolver 테스트 : view 파일 설정
	// url과 핸들러 메소드를 매핑할 때, url을 사용한다. 
	// 이때 url이 같으면 다른 속성으로 매핑할 수 있다. (예 : method = RequestMethod.~)
	
	
	@RequestMapping(value = "test")
	public void test() {
		// 리턴 문자열이 없으면 view 파일은 url 요청 이름과 같은 파일, 즉 test.jsp가 리턴 url 요청이 된다.
		logger.info("[MyInfo] test() 메소드 실행");
		// System.out.println 대신에 로그를 관리하고 출력한다.
	}

	@RequestMapping(value = "hello")
	public void hello() {
		// view 파일이 hello.jsp
	}

	@RequestMapping(value = "spring")
	public String spring() {
		return "home"; // view 파일이 home.jsp가 된다.
	}

	// (2) 여기서부터는 파라미터 테스트 : url 쿼리스트링으로 전달되는 파라미터를 controller 핸들러 메소드가 처리한다.

	@RequestMapping(value = "search")
	public String search(String name, int age) {
		// 메소드 인자로 파라미터가 저장될 변수를 선언해야 한다.
		// 변수명은 파라미터 이름과 일치시켜야 한다. (순서는 상관 X)
		logger.info("[MyInfo] name = " + name + ", age = " + age);
		return "redirect:/"; // contextPath로 간다는 의미
		// redirect:(url) : 서버가 재요청을 보낼 주소를 지정하는 것이다.
	}

	@RequestMapping(value = "search2")
//	public String search(String name) {
	// 파라미터는 2개, 변수는 1개 => 문제 없이 변수 선언된 파라미터만 가져온다.
//	public String search(String name, int age, int city) {
	// 파라미터는 2개, 변수는 3개 => 오류 발생 : 없는거 어떻게 가져옴?
	public String search(String name, int age, @RequestParam(defaultValue = "1") int city) {
		// @RequestParam : 없는 파라미터(전달되지 않은 파라미터)에 default 값을 지정해준다.
		logger.info("[MyInfo] name = " + name + ", age = " + age + ", city = " + city);
		return "redirect:spring";
	}

	@RequestMapping(value = "search3")
	public String search(@RequestParam Map<String, Object> param) {
		// 요청 파라미터 어노테이션, map으로 저장할 때는 필수!
		// 없으면 에러가 나는건 아니고 빈 문자열로 나온다.
		logger.info("[MyInfo] param = " + param);
		logger.info("[MyInfo] param = " + param.get("city"));
		// map의 key값을 지정하여 value를 가져온다.

		return "redirect:spring";
	}

	@RequestMapping(value = "search4")
	public String search(TestVo vo) {
		logger.info("[MyInfo] TestVo = " + vo);

		return "redirect:spring";
	}

	// (3) 연습문제 : url로 order를 요청하면 orderForm.jsp를 view로 출력한다.
	// 그리고 3가지 항목에서 입력한 값을 controller 핸들러 메소드가 전달받아 logger.info로 출력한다.

	// 1. orderForm.jsp를 화면에 출력
	@RequestMapping(value = "order", method = RequestMethod.GET)
	public String form() {
		return "orderForm";
	}
	
	// 2. 폼 양식에 입력된 값 가져오기
	// 2-1) vo 사용 -> 입력 요소값이 null일 때 오류 발생
	@RequestMapping(value = "order", method = RequestMethod.POST) 
	public String order(Order vo) {
		// 메소드의 매개변수 파라미터(폼 양식 입력값)가 여러개일 때, model 객체를 사용한다.
		logger.info("[MyInfo] 주문 정보 = " + vo);
		
		return "home";		// url은 order, 화면은 home.jsp
	}
/*	
	// 2-2) Map 사용 -> 입력 요소값이 null이어도 오류 발생 X
	@RequestMapping(value = "order", method = RequestMethod.POST)
	public String order(@RequestParam Map<String, Object> param) {
		logger.info("[MyInfo] param = " + param);

		return "home";
	}
*/	
}
