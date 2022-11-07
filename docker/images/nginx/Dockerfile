FROM nginx:1.22-alpine

COPY nginx.template /etc/nginx/nginx.template
COPY proxy_params /etc/nginx/proxy_params
COPY proxy_params_spring /etc/nginx/proxy_params_spring

CMD ["/bin/sh", "-c", "envsubst '$RESOLVER $DOCKER_IP' < /etc/nginx/nginx.template > /etc/nginx/nginx.conf && exec nginx -g 'daemon off;'"]
