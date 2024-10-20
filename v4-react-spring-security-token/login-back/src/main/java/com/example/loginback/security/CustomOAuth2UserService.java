package com.example.loginback.security;

import com.example.loginback.security.model.ProviderUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// Don't delete this component because the spring security uses this custom user service to save the user information
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuth2UserConverter oAuth2UserConverter;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        // --- Delete this code block if you don't need the saving the user into Database ---
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        ProviderUser providerUser = oAuth2UserConverter.constructProviderUserFromOAuth2User(registrationId, oAuth2User);
        oAuth2UserConverter.register(providerUser);
        // -----------------------------------------------------------------------------------

        return oAuth2User;
    }
}
