package cn.cleanarch.dp.gateway.feign;

import cn.cleanarch.dp.common.core.constant.ServiceNameConstants;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayLogDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = ServiceNameConstants.GATEWAY_ADMIN_SERVICE)
public interface GatewayAdminFeign {
    @GetMapping("/heartbeat")
    R<String> heartbeat();
    @PostMapping(value = "/gateway/log")
    R<List<GatewayLogDO>> saveAll(@RequestBody List<GatewayLogDO> list);
}
