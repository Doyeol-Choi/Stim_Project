package com.stim.vo.oauth2;

import java.util.Map;

public interface OAuth2UserVO {

	Map<String, Object> getAttributes();
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
