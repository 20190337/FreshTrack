#!/bin/bash

# Auto-detect JAVA_HOME
if [ -d "/usr/lib/jvm/java-17-openjdk-amd64" ]; then
    export JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64"
elif [ -d "/usr/lib/jvm/default-java" ]; then
    export JAVA_HOME="/usr/lib/jvm/default-java"
else
    # Get JAVA_HOME from java executable path
    JAVA_PATH=$(readlink -f $(which java))
    export JAVA_HOME=$(dirname $(dirname $JAVA_PATH))
fi

# Set Android SDK
export ANDROID_HOME="/usr/lib/android-sdk"

echo "Using JAVA_HOME: $JAVA_HOME"
echo "Using ANDROID_HOME: $ANDROID_HOME"

# Build the app
./gradlew assembleDebug --no-daemon