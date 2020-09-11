package com.malyshev2202.store.backend.grpc;

import com.malyshev2202.store.grpc.PersonRoleRequest;
import com.malyshev2202.store.grpc.PersonRoleResponse;
import com.malyshev2202.store.grpc.PersonRoleServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PersonRoleClient {
    private PersonRoleServiceGrpc.PersonRoleServiceBlockingStub stub;
    @PostConstruct
    public void unit(){
        ManagedChannel channel= ManagedChannelBuilder.forAddress("localhost",6565).usePlaintext().build();
        stub=PersonRoleServiceGrpc.newBlockingStub(channel);
    }
    public String getRole(String name){
        PersonRoleRequest request=PersonRoleRequest.newBuilder().setName(name).build();
        PersonRoleResponse response=stub.getRole(request);
        return response.getRole();

    }
}
