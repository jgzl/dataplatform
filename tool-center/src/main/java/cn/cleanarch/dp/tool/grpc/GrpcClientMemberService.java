package cn.cleanarch.dp.tool.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientMemberService {
    @GrpcClient(value = "tool-center")
    private MemberServiceGrpc.MemberServiceBlockingStub blockingStub;

    public MemberResponse getList() {
        MemberRequest request = MemberRequest.newBuilder().setPage(1).setPageSize(20).build();
        MemberResponse response = this.blockingStub.getList(request);
        return response;
    }
}
