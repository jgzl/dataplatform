FROM amazoncorretto:17 as builder
RUN mkdir -p /app
WORKDIR /app
ADD ./target/app.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM amazoncorretto:17
MAINTAINER li7hai26@gmail.com

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mkdir -p /app

WORKDIR /app
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/application/ ./

EXPOSE 9001

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms1024m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh","-c","java $JAVA_OPTS org.springframework.boot.loader.JarLauncher"]