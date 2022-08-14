package com.stim.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


// 장바구니에서 결제
@Controller
public class KakaoPayController {

	@RequestMapping("/kakaopayAll")
	@ResponseBody
	public String kakaopayAll(@RequestParam("user_code") int user_code,
						   @RequestParam("total") Integer total) {
		try {
			URL payUrl = new URL("https://kapi.kakao.com/v1/payment/ready");
			try {// 카카오 디벨로퍼 단건결제페이지에 있는 양식 그대로 옮김
				
				HttpURLConnection server = (HttpURLConnection) payUrl.openConnection(); //서버연결
				server.setRequestMethod("POST");
				server.setRequestProperty("Authorization", "KakaoAK 325966b20fbf7d9bf0737903ad5a11c4" );
				server.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				server.setDoOutput(true);
				// 정보 파라미터
				String pram = "cid=TC0ONETIME&partner_order_id=partner_order_id&"
						+ "partner_user_id=partner_user_id&item_name=game&quantity=1&total_amount="+total+"&"
						+ "tax_free_amount=0&approval_url=http://152.67.216.237:8080/paysuccessAll/"+user_code
						+ "&fail_url=http://152.67.216.237:8080/cart/"+user_code+"&"
						+ "cancel_url=http://152.67.216.237:8080/cart/"+user_code;
				OutputStream out = server.getOutputStream();	// 주는애
				DataOutputStream dataOut = new DataOutputStream(out);	//데이터 주는놈
				dataOut.writeBytes(pram);	// 바이트형식으로 데이터를 주고 받는다.
				dataOut.flush();
				dataOut.close();
				
				int result = server.getResponseCode();
				
				InputStream in;				// 받는애
				if(result==200) {
					in = server.getInputStream();
					System.out.println("결제성공");
				}else {
					in = server.getErrorStream();
					System.out.println(in);
				}
				InputStreamReader inReader = new InputStreamReader(in); // 받아온 값 읽어주기
				BufferedReader bufferReader = new BufferedReader(inReader); //형변환이 가능한 친구
				
				return bufferReader.readLine(); // 형변환 해서 받아주기
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		
		return "{\"result\":\"NO\"}";
	}
	
	// 바로결제
	@RequestMapping("/kakaopayOne")
	@ResponseBody
	public String kakaopayOne(@RequestParam("user_code") int user_code,
						@RequestParam("game_code") Integer game_code,
						   @RequestParam("game_price") Integer game_price) {
		try {
			URL payUrl = new URL("https://kapi.kakao.com/v1/payment/ready");
			try {// 카카오 디벨로퍼 단건결제페이지에 있는 양식 그대로 옮김
				
				HttpURLConnection server = (HttpURLConnection) payUrl.openConnection(); //서버연결
				server.setRequestMethod("POST");
				server.setRequestProperty("Authorization", "KakaoAK 325966b20fbf7d9bf0737903ad5a11c4" );
				server.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				server.setDoOutput(true);
				// 정보 파라미터
				String pram = "cid=TC0ONETIME&partner_order_id=partner_order_id&"
						+ "partner_user_id=partner_user_id&item_name=game&quantity=1&total_amount="+game_price+"&"
						+ "tax_free_amount=0&approval_url=http:152.67.216.237:8080/paysuccessOne/"+user_code+","+game_code 
						+ "&fail_url=http:152.67.216.237:8080/gameDetailView?game_code="+game_code+"&"
						+ "cancel_url=http://152.67.216.237:8080/gameDetailView?game_code="+game_code;
				OutputStream out = server.getOutputStream();	// 주는애
				DataOutputStream dataOut = new DataOutputStream(out);	//데이터 주는놈
				dataOut.writeBytes(pram);	// 바이트형식으로 데이터를 주고 받는다.
				dataOut.flush();
				dataOut.close();
				
				int result = server.getResponseCode();
				
				InputStream in;				// 받는애
				if(result==200) {
					in = server.getInputStream();
					System.out.println("결제성공");
				}else {
					in = server.getErrorStream();
					System.out.println(in);
				}
				InputStreamReader inReader = new InputStreamReader(in); // 받아온 값 읽어주기
				BufferedReader bufferReader = new BufferedReader(inReader); //형변환이 가능한 친구
				
				return bufferReader.readLine(); // 형변환 해서 받아주기
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		
		return "{\"result\":\"NO\"}";
	}
}