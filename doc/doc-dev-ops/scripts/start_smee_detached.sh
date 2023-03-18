#!/bin/bash

screen -S smeeClient -d -m smee --url https://smee.io/VzqN7rJZTqaUKUD6 --path /github-webhook/ --port 8000
screen -ls