#使用jdk8作为基础镜像
FROM java:8
VOLUME /tmp
ADD demo-0.0.1.jar /app.jar
#创建一个新的容器并在新的容器中运行命令
#RUN bash -c 'touch /app.jar'
EXPOSE 8082
#设置时区 PRC为中国
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#相当于在容器中用cmd命令执行jar包  指定外部配置文件 ENTRYPOINT命令比cmd先执行
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod"]