FROM osixia/openldap:1.3.0

RUN mkdir /cgos
COPY ./users.ldif /cgos/users.ldif
COPY ./groups.ldif /cgos/groups.ldif
COPY ./init.sh /cgos/init.sh
COPY ./docker-entrypoint.sh /cgos/docker-entrypoint.sh

ENTRYPOINT [ "/cgos/docker-entrypoint.sh" ]
