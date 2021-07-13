#!/bin/sh
git config --global user.email "travis@travis-ci.org"
git config --global user.name "Travis CI"

cd frontend/target

#init
git clone https://${GH_TOKEN}@github.com/verkhovin/poker.git
cd poker

#commit
rm -rf ./*
cp -R ../dist/* .
cp index.html 404.html
git add .
git commit --message "Travis build: $TRAVIS_BUILD_NUMBER"
git push
