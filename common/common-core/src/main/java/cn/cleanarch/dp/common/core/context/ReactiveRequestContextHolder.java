package cn.cleanarch.dp.common.core.context;

import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/21
 */
public class ReactiveRequestContextHolder {

    public static final Class<ServerHttpRequest> CONTEXT_KEY = ServerHttpRequest.class;

    /**
     * Gets the {@code Mono<ServerHttpRequest>} from Reactor {@link reactor.util.context.Context}
     *
     * @return the {@code Mono<ServerHttpRequest>}
     */
    public static Mono<ServerHttpRequest> getRequest() {
        return Mono.deferContextual(Mono::just)
                .map(ctx -> ctx.get(CONTEXT_KEY));
    }

}