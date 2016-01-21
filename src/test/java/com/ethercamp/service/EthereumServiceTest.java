package com.ethercamp.service;

import com.ethercamp.BasicApplication;
import com.ethercamp.model.GitIssue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BasicApplication.class)
public class EthereumServiceTest {

    @Autowired
    EthereumService ethereumService;


    @Test
    public void callNonce(){
        int nonce = ethereumService.getNonce();
        System.out.println(nonce);
    }


    @Test
    public void reportIssue(){

        GitIssue gitIssue = new GitIssue("ether-camp", "test", 12);
        String txHash = ethereumService.reportIssue(gitIssue);

        System.out.println(txHash);

    }

}

