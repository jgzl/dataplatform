//package cn.cleanarch.dp.gateway.filter.webflux;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
//import org.springframework.cloud.gateway.support.BodyInserterContext;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ReactiveHttpOutputMessage;
//import org.springframework.http.codec.HttpMessageReader;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.lang.NonNull;
//import org.springframework.web.reactive.function.BodyInserter;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.server.HandlerStrategies;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
///**
// * 文件大小过滤器
// *
// * @author li7hai26@gmail.com
// * @date 2021/12/21
// */
//@Slf4j
//public class FileSizeFilter implements WebFilter {
//
//    public static final String FILTER_NAME = "fileSizeFilter";
//
//    private static final int BYTES_PER_MB = 1024 * 1024;
//
//    private final int fileMaxSize;
//
//    private final List<HttpMessageReader<?>> messageReaders;
//
//    public FileSizeFilter(final int fileMaxSize) {
//        HandlerStrategies handlerStrategies = HandlerStrategies.builder().codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(fileMaxSize * BYTES_PER_MB)).build();
//        this.messageReaders = handlerStrategies.messageReaders();
//        this.fileMaxSize = fileMaxSize;
//    }
//
//    @Override
//    public Mono<Void> filter(@NonNull final ServerWebExchange exchange, @NonNull final WebFilterChain chain) {
//        MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
//        if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(mediaType)) {
//            ServerRequest serverRequest = ServerRequest.create(exchange,
//                    messageReaders);
//            return serverRequest.bodyToMono(DataBuffer.class)
//                    .flatMap(size -> {
//                        if (size.capacity() > BYTES_PER_MB * fileMaxSize) {
//                            return Mono.error(new RuntimeException(String.format("文件超过最大[%s]mb限制", fileMaxSize)));
//                        }
//                        BodyInserter<Mono<DataBuffer>, ReactiveHttpOutputMessage> bodyInsert = BodyInserters.fromPublisher(Mono.just(size), DataBuffer.class);
//                        HttpHeaders headers = new HttpHeaders();
//                        headers.putAll(exchange.getRequest().getHeaders());
//                        headers.remove(HttpHeaders.CONTENT_LENGTH);
//                        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(
//                                exchange, headers);
//                        return bodyInsert.insert(outputMessage, new BodyInserterContext())
//                                .then(Mono.defer(() -> {
//                                    ServerHttpRequest decorator = decorate(exchange, outputMessage);
//                                    return chain.filter(exchange.mutate().request(decorator).build());
//                                }));
//                    });
//        }
//        return chain.filter(exchange);
//
//    }
//
//    private ServerHttpRequestDecorator decorate(final ServerWebExchange exchange, final CachedBodyOutputMessage outputMessage) {
//        return new ServerHttpRequestDecorator(exchange.getRequest()) {
//            @Override
//            public Flux<DataBuffer> getBody() {
//                return outputMessage.getBody();
//            }
//        };
//    }
//}
//
