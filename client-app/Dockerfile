FROM node:18-alpine as build

RUN mkdir -p /app
WORKDIR /app

RUN npm install -g @angular/cli

COPY package*.json /app
RUN npm ci
COPY . .
RUN npm run build --prod

FROM nginx:1.25-alpine as runtime

COPY nginx.proxy.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/client-app /usr/share/nginx/html
