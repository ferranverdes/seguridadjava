#!/bin/sh

PID_FILE=$GLASSFISH_HOME/glassfish/domains/domain1/config/pid

asadmin start-domain 
#&& \
#asadmin --user $ADMIN_USER --passwordfile=/opt/tmpfile change-admin-password && \
#asadmin --user $ADMIN_USER --passwordfile=/opt/pwdfile enable-secure-admin && \
#asadmin restart-domain


inotifywait -qq -e delete_self $PID_FILE