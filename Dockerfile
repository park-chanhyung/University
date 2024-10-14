FROM ubuntu:latest
LABEL authors="chanh"

ENTRYPOINT ["top", "-b"]