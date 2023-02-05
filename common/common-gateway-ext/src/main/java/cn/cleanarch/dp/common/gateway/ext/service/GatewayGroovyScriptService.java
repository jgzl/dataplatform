package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.base.BaseService;
import cn.cleanarch.dp.common.gateway.ext.dao.GatewayGroovyScriptDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayGroovyScriptDO;
import cn.cleanarch.dp.common.gateway.ext.util.GroovyScriptUtils;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Description 规则引擎GroovyScript动态脚本业务处理类
 * @Author JL
 * @Date 2022/2/21
 * @Version V1.0
 */
@Slf4j
@Service
public class GatewayGroovyScriptService extends BaseService<GatewayGroovyScriptDO, Long, GatewayGroovyScriptDao> {

    @Resource
    private GatewayGroovyScriptDao gatewayGroovyScriptDao;

    private final static String ORDER_NUM = "orderNum";

    /**
     * 获取routeId下最大orderNum
     * @param routeId
     * @param event
     * @return
     */
    public int findMaxOrderNum(String routeId, String event) {
        Integer orderNum = gatewayGroovyScriptDao.findMaxOrderNum(routeId, event);
        return orderNum == null ? 1 : orderNum;
    }

    /**
     * 获取routeId下status为0（启用）的id集合，并按id顺序排序
     * @param routeId
     * @return
     */
    public List<Long> findIdList(String routeId){
        return gatewayGroovyScriptDao.findIdList(routeId);
    }

    /**
     * 删除动态脚本
     * @param gatewayGroovyScriptDO
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void delete(GatewayGroovyScriptDO gatewayGroovyScriptDO){
        super.delete(gatewayGroovyScriptDO);
        //重新排序
        GatewayGroovyScriptDO qGatewayGroovyScriptDO = new GatewayGroovyScriptDO();
        qGatewayGroovyScriptDO.setRouteId(gatewayGroovyScriptDO.getRouteId());
        List<GatewayGroovyScriptDO> gatewayGroovyScriptDOList = list(qGatewayGroovyScriptDO);
        if (CollectionUtils.isEmpty(gatewayGroovyScriptDOList)){
            return;
        }
        int i = 1;
        for (GatewayGroovyScriptDO dbGatewayGroovyScriptDO : gatewayGroovyScriptDOList){
            dbGatewayGroovyScriptDO.setOrderNum(i++);
            update(dbGatewayGroovyScriptDO);
        }
    }

    @Override
    public List<GatewayGroovyScriptDO> list(GatewayGroovyScriptDO script){
        return list(script, ORDER_NUM);
    }

    /**
     * 向上移动
     * @param gatewayGroovyScriptDO
     */
    public boolean upOrderNum(GatewayGroovyScriptDO gatewayGroovyScriptDO) {
        if (gatewayGroovyScriptDO.getOrderNum() <= 1) {
            return false;
        }
        return remvoeOrderNum(gatewayGroovyScriptDO, true);
    }

    /**
     * 向下移动
     * @param gatewayGroovyScriptDO
     */
    public boolean downOrderNum(GatewayGroovyScriptDO gatewayGroovyScriptDO) {
        int maxOrderNum = findMaxOrderNum(gatewayGroovyScriptDO.getRouteId(), gatewayGroovyScriptDO.getEvent());
        if (gatewayGroovyScriptDO.getOrderNum() >= maxOrderNum) {
            return false;
        }
        return remvoeOrderNum(gatewayGroovyScriptDO, false);
    }

    /**
     * 移动序号
     * @param gatewayGroovyScriptDO
     * @param isUp
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean remvoeOrderNum(GatewayGroovyScriptDO gatewayGroovyScriptDO, boolean isUp) {
        String routeId = gatewayGroovyScriptDO.getRouteId();
        long id = gatewayGroovyScriptDO.getId();
        int orderNum = gatewayGroovyScriptDO.getOrderNum();
        GatewayGroovyScriptDO qGatewayGroovyScriptDO = new GatewayGroovyScriptDO();
        qGatewayGroovyScriptDO.setRouteId(routeId);
        List<GatewayGroovyScriptDO> gatewayGroovyScriptDOList = list(qGatewayGroovyScriptDO, ORDER_NUM);
        if (CollectionUtils.isEmpty(gatewayGroovyScriptDOList)) {
            return false;
        }

        Optional<GatewayGroovyScriptDO> optional ;
        if (isUp){
            optional = gatewayGroovyScriptDOList.stream()
                    .filter(g->g.getId() != id && g.getEvent().equals(gatewayGroovyScriptDO.getEvent()) && g.getOrderNum() <= orderNum)
                    .sorted((g1,g2)->g2.getOrderNum() - g1.getOrderNum())
                    .findFirst();
        }else {
            optional = gatewayGroovyScriptDOList.stream()
                    .filter(g->g.getId() != id && g.getEvent().equals(gatewayGroovyScriptDO.getEvent()) && g.getOrderNum() >= orderNum)
                    .findFirst();
        }

        if (!optional.isPresent()){
            return false;
        }

        //交换序号
        GatewayGroovyScriptDO dbGatewayGroovyScriptDO = optional.get();
        int dbOrderNum = dbGatewayGroovyScriptDO.getOrderNum();
        gatewayGroovyScriptDO.setOrderNum(dbOrderNum);
        update(gatewayGroovyScriptDO);

        dbOrderNum = isUp ? dbOrderNum + 1 : dbOrderNum - 1;
        dbGatewayGroovyScriptDO.setOrderNum(dbOrderNum);
        update(dbGatewayGroovyScriptDO);

        return true;
    }

    /**
     * 初始化GroovyScript规则擎动态脚本，并实例化对象
     * @param script
     */
    public Object instance(GatewayGroovyScriptDO script) throws Exception {
        try {
            return GroovyScriptUtils.newObjectInstance(script.getContent());
        }catch(CompilationFailedException e){
            log.error("groovyScript规则引擎动态脚本编译错误！脚本ID：" + script.getId() + "，文件名" + script.getName() , e);
            throw e;
        }catch (InstantiationException | IllegalArgumentException | SecurityException | IllegalAccessException e){
            log.error("groovyScript规则引擎动态脚本实例化错误！脚本ID：" + script.getId() + "，文件名" + script.getName() , e);
            throw e;
        }catch(Exception e){
            log.error("groovyScript规则引擎动态脚本加载错误！脚本ID：" + script.getId() + "，文件名" + script.getName() , e);
            throw e;
        }
    }

}
