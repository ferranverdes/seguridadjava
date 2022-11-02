
# Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
# Click nbfs://nbhost/SystemFileSystem/Templates/Other/Dockerfile to edit this template

FROM ubuntu

RUN apt-get update -y && apt install -y openjdk-8-jdk curl unzip inotify-tools

RUN  curl -L -o /tmp/glassfish-5.0.1.zip http://download.oracle.com/glassfish/5.0.1/release/glassfish-5.0.1.zip && \
     unzip /tmp/glassfish-5.0.1.zip -d / && \
     rm -f /tmp/glassfish-5.0.1.zip

ENV  JAVA_HOME         /usr/lib/jvm/java-1.8.0-openjdk-amd64/
ENV  GLASSFISH_HOME    /glassfish5
ENV  PATH              $PATH:$JAVA_HOME/bin:$GLASSFISH_HOME/bin


# patch joda-time jar and grizzly jar to solve ssl issue with glassfish 5.0.
#RUN  curl https://repo1.maven.org/maven2/joda-time/joda-time/2.9.9/joda-time-2.9.9.jar --output "$GLASSFISH_HOME/glassfish/domains/domain1/lib/ext/joda-time-2.9.9.jar"
#RUN  curl https://repo1.maven.org/maven2/org/glassfish/grizzly/grizzly-npn-osgi/1.8/grizzly-npn-osgi-1.8.jar --output "$GLASSFISH_HOME/glassfish/modules/grizzly-npn-osgi.jar"
#RUN  curl https://repo1.maven.org/maven2/org/glassfish/grizzly/grizzly-npn-bootstrap/1.8/grizzly-npn-bootstrap-1.8.jar --output "$GLASSFISH_HOME/glassfish/modules/endorsed/grizzly-npn-bootstrap.jar"

#Configuración JDBC aplicación seguridad-java
COPY domain.xml "$GLASSFISH_HOME/glassfish/domains/domain1/config/domain.xml"
COPY target/seguridadjava-1.0-SNAPSHOT.war "$GLASSFISH_HOME/glassfish/domains/domain1/autodeploy/seguridadjava.war"
#COPY hello.war "$GLASSFISH_HOME/glassfish/domains/domain1/autodeploy/hello.war"
#Conector jdbc Mysql
COPY mysql-connector-j-8.0.31.jar "$GLASSFISH_HOME/glassfish/lib/mysql-connector-j-8.0.31.jar"


ENV ADMIN_USER admin
ENV ADMIN_PASSWORD admin

RUN echo 'AS_ADMIN_PASSWORD=\n\
AS_ADMIN_NEWPASSWORD='$ADMIN_PASSWORD'\n\
EOF\n'\
>> /opt/tmpfile

RUN echo 'AS_ADMIN_PASSWORD='$ADMIN_PASSWORD'\n\
EOF\n'\
>> /opt/pwdfile


#RUN \
# $GLASSFISH_HOME/bin/asadmin start-domain && \
# $GLASSFISH_HOME/bin/asadmin --user $ADMIN_USER --passwordfile=/opt/tmpfile change-admin-password && \
# $GLASSFISH_HOME/bin/asadmin --user $ADMIN_USER --passwordfile=/opt/pwdfile enable-secure-admin && \
# $GLASSFISH_HOME/bin/asadmin stop-domain

# cleanup
#RUN rm /opt/tmpfile


#RUN ln -sf /dev/stdout $GLASSFISH_HOME/glassfish/domains/domain1/logs/server.log


ADD         glassfish-start.sh /
CMD         []
ENTRYPOINT  ["/glassfish-start.sh"]