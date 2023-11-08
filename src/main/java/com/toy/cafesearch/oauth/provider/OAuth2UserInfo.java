package com.toy.cafesearch.oauth.provider;

public interface OAuth2UserInfo {
    String getProvider();
    String getProviderId();
    String getIdEmail();
    String getName();
}
