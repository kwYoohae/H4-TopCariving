package com.backend.topcariving.domain.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.user.dto.LoginRequestDTO;
import com.backend.topcariving.domain.user.service.UserService;
import com.backend.topcariving.global.auth.annotation.Login;
import com.backend.topcariving.global.auth.dto.TokenDTO;
import com.backend.topcariving.global.auth.service.OAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name="로그인 로직 담당 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;
	private final OAuthService oAuthService;

	@Value("${oauth.hyundai.redirect-uri}")
	private String REDIRECT_URI;

	private static final String MAIN_URI = "https://www.topcariving.com";

	@PostMapping("/login")
	@ApiResponse(responseCode = "200", description = "성공하면, Authorization 헤더에 access-token 값 반환")
	@Operation(summary = "로그인", description = "로그인을 수행하고, Authorization 헤더에 access-token 값을 반환한다")
	public TokenDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
		return userService.login(loginRequestDTO);
	}

	@GetMapping("/reissue")
	@SecurityRequirement(name = "Authorization")
	public TokenDTO reissue(@Parameter(hidden = true) @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String bearerToken) {
		String accessToken = userService.reissueAccessToken(bearerToken);
		return new TokenDTO(accessToken, null);
	}

	@GetMapping("/oauth/authorize")
	@Operation(hidden = true)
	public ResponseEntity<TokenDTO> authorize(@RequestParam String code, @RequestParam String state) {
		HttpHeaders headers = new HttpHeaders();
		TokenDTO tokenDTO = oAuthService.authorize(state, code);

		headers.set("Location", REDIRECT_URI + "?accessToken=" + tokenDTO.getAccessToken() + "&refreshToken=" + tokenDTO.getRefreshToken());

		return new ResponseEntity<>(tokenDTO, headers, HttpStatus.FOUND);
	}

	@GetMapping("/logout")
	@Operation(summary = "로그아웃", description = "로그아웃을 수행하고, ")
	@SecurityRequirement(name = "Authorization")
	public ResponseEntity<Void> logout(@Parameter(hidden = true) @Login Long userId) {
		oAuthService.logout(userId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Location", MAIN_URI);

		return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}
}
