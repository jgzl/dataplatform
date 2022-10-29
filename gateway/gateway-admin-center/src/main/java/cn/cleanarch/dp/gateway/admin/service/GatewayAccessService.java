package cn.cleanarch.dp.gateway.admin.service;

import cn.cleanarch.dp.gateway.domain.GatewayAccessDO;
import cn.cleanarch.dp.gateway.vo.GatewayAccessVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 路由
 *
 * @author li7hai26@gmail.com
 * @date 2018-11-06 10:17:18
 */
public interface GatewayAccessService extends IService<GatewayAccessDO> {

    /**
     * 删除路由信息
     *
     * @param id 路由id
     * @return
     */
	void deleteItem(String id);

    Boolean updateStatus(GatewayAccessVO vo);
}
