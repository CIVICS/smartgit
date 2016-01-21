package com.ethercamp.model;

import java.util.ArrayList;
import java.util.List;

public class EthRequest {


    // { "jsonrpc": "2.0",
    //   "method": "eth_getTransactionCount",
    //   "params": ["0x407d73d8a49eeb85d32cf465507dd71d507100c1","latest"],
    //   "id":1}

    String jsonrpc;
    String method;
    List<String> params = new ArrayList<>();
    int id;

    public EthRequest() {
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addParam(String param){
        params.add(param);
    }
}
