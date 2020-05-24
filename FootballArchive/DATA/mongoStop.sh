#!/bin/bash

# KILL PROCESS USING PORT
sudo fuser -k 27017/tcp
sudo fuser -k 27018/tcp
sudo fuser -k 27019/tcp