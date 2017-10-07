package org.enoy.awesomeradio.view;

import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@SpringComponent
public class CoinHiveTokenChecker {

	private static final String URL = "https://api.coinhive.com/token/verify";

	@Value("${coinhive.key.private}")
	private String coinHivePrivateKey;

	private RestTemplate restTemplate;

	@PostConstruct
	private void init() {
		restTemplate = new RestTemplate();
	}

	public boolean isTokenValid(String token, long hashes) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("secret", coinHivePrivateKey);
		map.add("token", token);
		map.add("hashes", Long.toString(hashes));

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, null);

		VerifyResponse result = restTemplate.postForObject(URL, request, VerifyResponse.class);

		return result.success;

	}

	static class VerifyResponse {

		private boolean success;

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}
	}

}
