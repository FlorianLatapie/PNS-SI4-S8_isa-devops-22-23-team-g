FROM gcr.io/distroless/nodejs18-debian11

WORKDIR /app

COPY ./dist ./dist

EXPOSE 9080
# Start the server using the production build
CMD ["dist/main.js" ]
