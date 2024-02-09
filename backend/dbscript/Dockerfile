FROM postgres:14.6-alpine
MAINTAINER Jahnavi <jahnavi.manne.1@slu.edu>
ENV POSTGRES_HOST_AUTH_METHOD=trust
COPY e_voting.sql /docker-entrypoint-initdb.d/
COPY queries.sql /docker-entrypoint-initdb.d/
EXPOSE 5432
