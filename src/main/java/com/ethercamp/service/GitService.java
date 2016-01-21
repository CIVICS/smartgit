package com.ethercamp.service;

import com.ethercamp.model.GitResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class GitService {

//    GITHUB v3
//    https://api.github.com/
//    GET /repos/:owner/:repo/issues/:number
//    example:
//       https://api.github.com/repos/ethereum/go-ethereum/issues/2137


    public boolean isOpenIssueFor(String org, String user, Integer index){

        RestTemplate restTemplate = new RestTemplate();
        String rpcEnd =
            String.format("https://api.github.com/repos/%s/%s/issues/%s", org, user, index);

        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "application/json");

        HttpHeaders headers = new HttpHeaders();
        headers.setAll(headersMap);

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<GitResponse> response = null;


        try {
            response = restTemplate.exchange(rpcEnd, HttpMethod.GET, entity, GitResponse.class);
            GitResponse body = response.getBody();

            System.out.println("state: " + body.getState());

            return body.getState().equals("open");
        } catch (HttpClientErrorException ex)   {
            System.out.println(ex);
//            throw ex;
        }

        return false;
    }
}
