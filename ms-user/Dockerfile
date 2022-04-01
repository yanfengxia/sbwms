FROM openjdk:11-slim
MAINTAINER xiayanfeng
ENV JAR_FILE=build/libs/ms-user-1.0.jar
ADD ${JAR_FILE} app.jar
ADD https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.30.0/elastic-apm-agent-1.30.0.jar /agent.jar
ENTRYPOINT [ "java", "-javaagent:/agent.jar", "-Delastic.apm.service_name=ms-user -Delastic.apm.application_packages=com.beta.ms.user -Delastic.apm.server_urls=http://linux.local:8200", "-jar", "/app.jar", "${JAVA_OPTS}" ]