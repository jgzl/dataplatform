package cn.cleanarch.gw.gateway.admin.gateway.service;

import cn.cleanarch.gw.gateway.admin.gateway.domain.GatewayAccessConfDO;
import cn.cleanarch.gw.gateway.admin.gateway.vo.GatewayAccessConfVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 路由
 *
 * @author li7hai26@gmail.com
 * @date 2018-11-06 10:17:18
 */
public interface GatewayAccessConfService extends IService<GatewayAccessConfDO> {

    /**
     * 删除路由信息
     *
     * @param id 路由id
     * @return
     */
	void deleteItem(String id);

    Boolean updateStatus(GatewayAccessConfVO vo);
}
