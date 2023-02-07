#!/bin/bash

# Running the image as
#  - removing the container after exit (ease housekeeping because it's a POC)
#  - detached (-d),
#  - binding localhost:8080 to container:8080
docker run --rm -d -p 8080:8080 pcollet/tcf-spring-backend

# to stop: docker stop ID
# to start a new shell in the container: docker exec -it ID bash
# to attach to the container: docker attach ID (^P ^Q to detach)
