package com.example.loginback.service;

import com.example.loginback.model.ProviderUser;
import com.example.loginback.repository.UserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends AbstractOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    public CustomOidcUserService(UserRepository userRepository, UserService userService) {
        super(userRepository, userService);
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();
        OidcUser oidcUser = oidcUserService.loadUser(userRequest);
        ProviderUser providerUser = super.getProviderUserByProvider(clientRegistration, oidcUser);

        // sign up
        super.register(providerUser, userRequest);

        return oidcUser;
    }
}
