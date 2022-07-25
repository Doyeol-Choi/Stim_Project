package com.stim.vo.oauth2;

import java.util.Map;

public class NaverUserVO implements OAuth2UserVO {

	private Map<String, Object> attributes;
	private Map<String, Object> attributesResponse;
	
	public NaverUserVO(Map<String, Object> attributes) {
		this.attributes = (Map<String, Object>) attributes.get("response");
		this.attributesResponse = (Map<String, Object>) attributes.get("response");
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getProviderId() {
		return attributesResponse.get("id").toString();
	}

	@Override
	public String getProvider() {
		return "naver";
	}

	@Override
	public String getEmail() {
		return attributesResponse.get("email").toString();
	}

	@Override
	public String getName() {
		return attributesResponse.get("name").toString();
	}

}
