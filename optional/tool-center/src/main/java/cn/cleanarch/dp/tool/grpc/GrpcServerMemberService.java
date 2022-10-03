package cn.cleanarch.dp.tool.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GrpcServerMemberService extends MemberServiceGrpc.MemberServiceImplBase{
    @Override
    public void getList(MemberRequest request, StreamObserver<MemberResponse> responseObserver) {
        Member.Builder member = Member.newBuilder().setUsername("lihaifeng").setPassword("xxxxxx").setInfo("测试用户");
        MemberResponse memberResponse = MemberResponse.newBuilder().addMember(member).build();
        responseObserver.onNext(memberResponse);
        responseObserver.onCompleted();
    }
}
