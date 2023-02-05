package cn.cleanarch.dp.gateway.fish.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 动态添加路由
 * @Author JL
 * @Date 2020/05/11
 * @Version V1.0
 */
@Slf4j
@Service
public class DynamicGatewayRouteService {

    /**
     * inMemoryRouteDefinitionRepository 类中实现RouteDefinitionLocator接口
     * gateway应用在启动过程中，会调用所有实现类的getRouteDefinitions()方法，从routeDefinitions集合中加载网关路由信息
     */
    @Resource
    private RouteDefinitionRepository inMemoryRouteDefinitionRepository;

    /**
     * 批量增加路由
     * @param routeDefinitions
     * @return
     */
    public void addAll(List<RouteDefinition> routeDefinitions) {
        routeDefinitions.forEach(r->add(r));
    }

    /**
     * 增加路由
     * @param routeDefinition
     * @return
     */
    public void add(RouteDefinition routeDefinition) {
        try {
            inMemoryRouteDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
        } catch (Exception e) {
            throw new IllegalArgumentException("添加网关路由失败:" + e.getMessage(), e);
        }
    }

    /**
     * 更新路由,删除》添加
     * @param routeDefinition
     * @return
     */
    public void update(RouteDefinition routeDefinition) {
        delete(routeDefinition.getId());
        add(routeDefinition);
    }

    /**
     * 删除路由
     * @param id
     * @return
     */
    public void delete(String id) {
        try {
            inMemoryRouteDefinitionRepository.delete(Mono.just(id)).
                    then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build()))).
                    onErrorResume((t) -> t instanceof NotFoundException, (t) -> Mono.just(ResponseEntity.notFound().build())).
                    subscribe();
        }catch(Exception e){
            throw new IllegalArgumentException("删除网关路由"+ id +"失败:" + e.getMessage(), e);
        }
    }

    /**
     * 删除路由
     * @param balancedId
     * @return
     */
    public void deleteBalanced(String balancedId) {
        this.getRouteDefinitions().collectList().doOnNext(routeDefinitions -> {
            routeDefinitions.stream()
                    .filter(r -> r.getId().contains(balancedId))
                    .forEach(r->{
                        try {
                            delete(r.getId());
                        } catch (Exception e) {
                            log.error("删除网关路由" + balancedId + "失败:" + e.getMessage(),  e);
                        }
                    });
        }).subscribe();
    }

    /**
     * 获取所有网关路由信息
     * @return
     */
    public Flux<RouteDefinition> getRouteDefinitions() {
        return inMemoryRouteDefinitionRepository.getRouteDefinitions();
    }
}
