package cn.cleanarch.dp.tool.controller;

import cn.cleanarch.dp.tool.grpc.GrpcClientMemberService;
import cn.cleanarch.dp.tool.grpc.MemberResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MemberController {

    @Autowired
    private GrpcClientMemberService grpcClientMemberService;

    @GetMapping("/getList")
    public String list() {
        MemberResponse result = grpcClientMemberService.getList();
        List<JSONObject> list = result.getMemberList().stream().map(m -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", m.getUsername());
            jsonObject.set("password", m.getPassword());
            jsonObject.set("info", m.getInfo());
            return jsonObject;
        }).collect(Collectors.toList());
        return JSONUtil.toJsonStr(list);
    }
}
