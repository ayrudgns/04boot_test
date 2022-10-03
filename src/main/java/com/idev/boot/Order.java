package com.idev.boot;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
	// OrderVo 클래스는 orderForm.jsp의 입력 데이터를 Controller의 order() 메소드에 전달하는 데이터 저장 클래스
	// form 양식의 input 요소 name 속성과 동일하게 변수명을 정한다.
	private String id;
	private int amount;
	private Date devDate;
}	
