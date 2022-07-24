package com.stim.vo.oauth2;

import java.util.Map;

public class GoogleUserVO implements OAuth2UserVO {

	private Map<String, Object> attributes;
	
	public GoogleUserVO(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
	
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getProviderId() {
		return attributes.get("sub").toString();
	}

	@Override
	public String getProvider() {
		return "google";
	}

	@Override
	public String getEmail() {
		return attributes.get("email").toString();
	}

	@Override
	public String getName() {
		return attributes.get("name").toString();
	}

}
