FROM node:20-alpine AS build

WORKDIR /app

COPY package.json .
COPY vite.config.js .
COPY index.html .
COPY src ./src

RUN npm install
RUN npm run build

FROM nginx:1.27-alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
