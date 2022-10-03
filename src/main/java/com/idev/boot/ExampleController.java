package com.idev.boot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExampleController {
	private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);
	
	// (1) controller에서 view로 데이터를 전달할 때 Model 객체를 사용
	
	@RequestMapping(value = "model")
	public void model(Model model) {		// 핸들러 메소드 *인자로 선언된 객체*에 애트리뷰트를 저장한다.
					// Model은 인터페이스이며, 핸들러 메소드에서 선언하는 것은 아니다.
					// * 인터페이스는 직접 객체를 생성할 수 없으며, 구현체가 필요하다.
					// view에 데이터를 전달하기 위해 Model 구현체는 Spring에서 알아서 생성한다.
		model.addAttribute("name", "이사나");
		model.addAttribute("page", 11);
	}
	
	// (2) 여기서부터는 파라미터 -> controller에서 attribute로 저장 -> view로 데이터 전달된다.
	@RequestMapping(value = "modelAttr")
	public void search(@ModelAttribute(value="name") String name, 
			@ModelAttribute(value="age") int age) {
		// name, age는 요청파라미터이면서 동시에 view에 전달되는 애트리뷰트(@ModelAttribute로 model 객체에 값을 저장)
		logger.info("[MyInfo] name = " + name + ", age = " + age);
	}

	@RequestMapping(value = "modelAttr2")
	public void search(@RequestParam Map<String, Object> param, Model model) {
		// @RequestParam : 요청파라미터 어노테이션 / Map으로 저장할 때는 필수
		logger.info("[MyInfo] param = " + param);
		logger.info("[MyInfo] param = " + param.get("city"));	// map의 key값을 지정하여 value 가져오기
		model.addAttribute("map", param);		// 요청파라미터를 애트리뷰트에 저장
	}
	
	@RequestMapping(value = "modelAttr3")
	public void search(@ModelAttribute(value="vo") TestVo vo) {
		logger.info("[MyInfo] TestVo = " + vo);
		// * vo 객체는 요청파라미터도 저장되고 view에도 전달되는 애트리뷰트이다. *
	}
	
	// 연습 : url로 order2를 요청하면 orderForm2.jsp를 view 출력!
	// orderForm2.jsp에서 입력한 값을 modelAttr4.jsp에서 출력하기
	
	@RequestMapping(value = "order2")
	public String order2() {
		return "orderForm2";
	}
	
	@RequestMapping(value = "orderAttr")
	public String orderAttr(@ModelAttribute(value="order") Order order) {
		return "modelAttr4";
	}
	
	// 리다이렉트 url로 파라미터 전달
	@RequestMapping(value = "search5")
	public String search(int age, String name) {
		// 메소드 인자로 파라미터가 저장될 변수를 선언
		// 변수명은 파라미터 이름과 일치해야 하며, 순서는 상관 없음!
		logger.info("[MyInfo] name = " + name + ", age = " + age);
		return "home";		
	}
	
	@RequestMapping(value = "redirectParam")
	public String search() throws UnsupportedEncodingException {
		String name = URLEncoder.encode("트와이스", "UTF-8");
		// 서버가 redirect 하기 때문에 인코딩을 해줘야 한다.
		// String name = "트와이스";
		return "redirect:search5?name=" + name + "&age=25";		
		// jsp에서의 response.sendRedirect("search5?name=" + name + "&age=25");와 같음!
	}
	// redirect:url은 서버가 재요청을 보낼 주소를 지정한다.
	// http://localhost:8080/app/search5?name=트와이스&age=25를 직접 주소창에 입력하면 기본 UTF-8 인코딩
	
	@RequestMapping(value = "redirectAttr")		// Spring Boot에서는 리다이렉트로 파라미터 전달 시 Model 객체를 사용할 수 없다.
	public String redirect(RedirectAttributes rdattr) {
		rdattr.addAttribute("name", "트와이스");
		rdattr.addAttribute("age", 25);
		return "redirect:search5";
		// return "redirect:search?name=트와이스&age=25";와 같은 실행 결과
	}
	// 결론적으로, model은 redirect에서 get 요청파라미터 값이 된다. => 실행했을 때의 url을 확인해보기!
	
	
	
	
}
