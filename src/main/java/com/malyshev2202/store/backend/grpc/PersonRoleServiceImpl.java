package com.malyshev2202.store.backend.grpc;

import com.malyshev2202.store.backend.strategy.DBStrategy;
import com.malyshev2202.store.grpc.PersonRoleRequest;
import com.malyshev2202.store.grpc.PersonRoleResponse;
import com.malyshev2202.store.grpc.PersonRoleServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class PersonRoleServiceImpl extends PersonRoleServiceGrpc.PersonRoleServiceImplBase {

    @Override
    public void getRole(PersonRoleRequest request, StreamObserver<PersonRoleResponse> responseObserver) {
        String role="admin";
        PersonRoleResponse response=PersonRoleResponse.newBuilder().setRole(role).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
