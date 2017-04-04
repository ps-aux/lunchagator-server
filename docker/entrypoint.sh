#!/usr/bin/env bash
set -e

check_var() {
   if [[ -z "${!1}" ]]; then
        echo "Missing env variable ${1}" >&2
        exit 1
   fi
}

check_var DB_JDBC_URL
check_var DB_USER_NAME
check_var DB_USER_PASS

export SPRING_DATASOURCE_URL="$DB_JDBC_URL"
export SPRING_DATASOURCE_USERNAME="$DB_USER_NAME"
export SPRING_DATASOURCE_PASSWORD="$DB_USER_PASS"

java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
