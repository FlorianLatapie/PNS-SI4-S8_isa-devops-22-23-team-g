#!/bin/bash

WEBHOOK_SMEE_URL=https://smee.io/VzqN7rJZTqaUKUD6
WEBHOOK_CUSTOM_SMEE_URL=http://5.39.84.27:3000/pJvJOoFEWOTn8B9W

screen -S smeeClient -d -m smee --url $WEBHOOK_CUSTOM_SMEE_URL --path /github-webhook/ --port 8000
screen -ls