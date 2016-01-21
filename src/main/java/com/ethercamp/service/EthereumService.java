package com.ethercamp.service;

import com.ethercamp.model.EthRequest;
import com.ethercamp.model.EthResponse;
import com.ethercamp.model.GitIssue;
import org.ethereum.core.CallTransaction;
import org.ethereum.core.Transaction;
import org.ethereum.crypto.ECKey;
import org.ethereum.crypto.HashUtil;
import org.ethereum.crypto.SHA3Helper;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.ethereum.crypto.SHA3Helper.sha3;

@Service
public class EthereumService {

//    https://github.com/ether-camp/feed.reporter.ether.camp/blob/master/src/main/java/com/ethercamp/feedreporter/scheduler/Scheduler.java

    @Value("${ethereum.endpoint}")
    private String endpoint;

    @Value("${sender.address}")
    private String sender;

    @Value("${pkey.seed}")
    private String seed;

    @Value("${contract}")
    private String contract;


    public String reportIssue(GitIssue gitIssue){

        CallTransaction.Function function = CallTransaction.Function.fromSignature("reportIssue",
                "string", "string", "uint");

        Transaction tx = CallTransaction.createCallTransaction(
                getNonce(),
                70_000_000_000L, // => gas price
                1_000_000,       // => gas limit
                contract,     // => the contract address we actually updating
                1,               // => value,  can be zero
                function,        // => abi definition of the call
                gitIssue.getOrganisation(), gitIssue.getUser(), gitIssue.getIndex()
        );

        tx.sign(sha3(seed.getBytes()));


        String txRaw = Hex.toHexString(tx.getEncoded());


//        curl -X POST --data '{"jsonrpc":"2.0","method":"eth_sendRawTransaction","params":["0xf91f1..."],"id":1}'


        RestTemplate restTemplate = new RestTemplate();
        String rpcEnd = endpoint;


        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "application/json");

        HttpHeaders headers = new HttpHeaders();
        headers.setAll(headersMap);


        EthRequest request = new EthRequest();
        request.setJsonrpc("2.0");
        request.setMethod("eth_sendRawTransaction");

        request.addParam("0x" + txRaw);
        request.setId(1);

        HttpEntity<EthRequest> entity = new HttpEntity<EthRequest>(request, headers);

        // { "jsonrpc": "2.0",
        //   "method": "eth_getTransactionCount",
        //   "params": ["0x407d73d8a49eeb85d32cf465507dd71d507100c1","latest"],
        //   "id":1}

        ResponseEntity<EthResponse> response = null;


        try {
            response = restTemplate.exchange(rpcEnd, HttpMethod.POST, entity, EthResponse.class);
            EthResponse body = response.getBody();

            return body.getResult();

        } catch (HttpClientErrorException ex)   {
            System.out.println(ex);

        }

        return "Nan";
    }


    public int getNonce(){

        RestTemplate restTemplate = new RestTemplate();
        String rpcEnd = endpoint;


        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "application/json");

        HttpHeaders headers = new HttpHeaders();
        headers.setAll(headersMap);


        EthRequest request = new EthRequest();
        request.setJsonrpc("2.0");
        request.setMethod("eth_getTransactionCount");

        request.addParam(sender);
        request.addParam("latest");
        request.setId(1);

        HttpEntity<EthRequest> entity = new HttpEntity<EthRequest>(request, headers);

        // { "jsonrpc": "2.0",
        //   "method": "eth_getTransactionCount",
        //   "params": ["0x407d73d8a49eeb85d32cf465507dd71d507100c1","latest"],
        //   "id":1}

        ResponseEntity<EthResponse> response = null;


        try {
            response = restTemplate.exchange(rpcEnd, HttpMethod.POST, entity, EthResponse.class);
            EthResponse body = response.getBody();

            int nonce = Integer.parseInt(
                body.getResult().substring(2)
                , 16);

            return nonce;

        } catch (HttpClientErrorException ex)   {
            System.out.println(ex);

        }

        return 0;
    }

}
