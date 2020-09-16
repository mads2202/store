package com.malyshev2202.store.backend.client;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import com.malyshev2202.HelloRequest;
import com.malyshev2202.HelloResponse;
import com.malyshev2202.HelloWorldServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HelloClient {
    HelloWorldServiceGrpc.HelloWorldServiceBlockingStub stub;
    @PostConstruct
    public void unit(){
        ManagedChannel channel=ManagedChannelBuilder.forAddress("localhost",6565)
                .usePlaintext().build();
        stub=HelloWorldServiceGrpc.newBlockingStub(channel);
    }
    public String sayHello(String fname, String lname){
        HelloResponse response= stub.sayHello(HelloRequest.newBuilder().
                setFirstName(fname).setLastName(lname).build());
        return response.getMessage();
    }

}







