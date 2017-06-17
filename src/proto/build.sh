#!/bin/bash

pbjs -t static-module -o ChatMessage.js -w closure ChatMessage.proto && \
  cp ChatMessage.js ../../res/public/incs/js/

#.
