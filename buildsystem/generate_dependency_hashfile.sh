#!/bin/sh

#
# This script scans the directory passed as the first parameter and hashes all gradlefiles it finds/
# It then outputs the hashes into the file passed as the second parameter
#

SOURCE_DIR=$1
HASH_FILE=$2
REGEX_PATTERN="*.gradle"

find ${SOURCE_DIR} -type f -name "$REGEX_PATTERN" -exec md5sum {} \; | sort -k2 -b > ${HASH_FILE}

