#!/bin/bash

cp app/src/main/java/it/subbuteo/subbutapp/globals/Globals.java ./commit_sub_files/
cp app/src/main/res/layout/su_* ./commit_sub_files/
cp app/src/main/res/layout/cu_* ./commit_sub_files/
cp app/src/main/res/layout/ch_* ./commit_sub_files/
cp app/src/main/res/layout/le_* ./commit_sub_files/

cp ./no_commit_files/Globals.java app/src/main/java/it/subbuteo/subbutapp/globals/
cp ./no_commit_files/su_* app/src/main/res/layout/
cp ./no_commit_files/cu_* app/src/main/res/layout/
cp ./no_commit_files/ch_* app/src/main/res/layout/
cp ./no_commit_files/le_* app/src/main/res/layout/
