package cn.cleanarch.dp.gateway.admin.service;

import cn.cleanarch.dp.gateway.admin.dataobject.GatewayAccessConfDO;
import cn.cleanarch.dp.gateway.admin.vo.GatewayAccessConfVO;
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
