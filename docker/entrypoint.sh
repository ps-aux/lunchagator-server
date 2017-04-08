#!/usr/bin/env bash
set -e

check_var() {
   if [[ -z "${!1}" ]]; then
        echo "Missing env variable ${1}" >&2
        exit 1
   fi
}

check_var LUNCHGATOR_DB_JDBC_URL
check_var LUNCHGATOR_DB_USER
check_var LUNCHGATOR_DB_PASS

export SPRING_DATASOURCE_URL="$LUNCHGATOR_DB_JDBC_URL"
export SPRING_DATASOURCE_USERNAME="$LUNCHGATOR_DB_USER"
export SPRING_DATASOURCE_PASSWORD="$LUNCHGATOR_DB_PASS"

java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
