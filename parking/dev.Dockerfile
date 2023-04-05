######################
# build for local dev

FROM node:18.13.0-alpine3.17 AS dev
WORKDIR /usr/src/app
# Copy application dependency manifests to the container image.
# A wildcard is used to ensure copying both package.json AND package-lock.json (when available).
# Copying this first prevents re-running npm install on every code change.
COPY --chown=node:node package*.json ./
# Install app dependencies using the `npm ci` command instead of `npm install`
RUN npm ci
# Bundle app source
COPY --chown=node:node . .
# Use the node user from the image (instead of the root user)
USER node

###################
# build for prod

FROM node:18.13.0-alpine3.17 AS build
WORKDIR /usr/src/app
COPY --chown=node:node package*.json ./
# In order to run `npm run build` we need access to the Nest CLI which is a dev dependency. In the previous development stage we ran `npm ci` which installed all dependencies, so we can copy over the node_modules directory from the development image
COPY --chown=node:node --from=dev /usr/src/app/node_modules ./node_modules
COPY --chown=node:node . .
# Run the build command which creates the production bundle
RUN npm run build
# Set NODE_ENV environment variable
ENV NODE_ENV production
# Running `npm ci` removes the existing node_modules directory and passing in --only=production ensures that only the production dependencies are installed. This ensures that the node_modules directory is as optimized as possible
RUN npm ci --only=production && npm cache clean --force
USER node

###################
# prod
FROM gcr.io/distroless/nodejs18-debian11 as prod

WORKDIR /app

COPY --from=build /usr/src/app/node_modules ./node_modules
COPY --from=build /usr/src/app/dist ./dist

EXPOSE 9090
# Start the server using the production build
CMD ["dist/main.js" ]
