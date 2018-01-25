#!/bin/bash
docker rm -f $(docker ps -a -q)
docker rmi -f $(docker images -q)

echo "Images"
docker images
echo ""
echo ""
echo "Container"
docker ps
echo ""
