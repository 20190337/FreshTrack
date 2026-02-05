#!/bin/bash

# Auto-detect JAVA_HOME
if [ -d "/usr/lib/jvm/java-17-openjdk-amd64" ]; then
    export JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64"
elif [ -d "/usr/lib/jvm/default-java" ]; then
    export JAVA_HOME="/usr/lib/jvm/default-java"
else
    # Get JAVA_HOME from java executable path - handle cases where readlink behaves differently
    if command -v java >/dev/null 2>&1; then
        JAVA_PATH=$(command -v java)
        if [ -L "$JAVA_PATH" ]; then
            # It's a symbolic link
            RESOLVED_PATH=$(readlink -f "$JAVA_PATH" 2>/dev/null || readlink "$JAVA_PATH" 2>/dev/null || echo "$JAVA_PATH")
        else
            # It's not a symbolic link
            RESOLVED_PATH="$JAVA_PATH"
        fi
        if [ -n "$RESOLVED_PATH" ]; then
            export JAVA_HOME=$(dirname "$(dirname "$RESOLVED_PATH")")
        fi
    fi
fi

# Set Android SDK
export ANDROID_HOME="/usr/lib/android-sdk"

echo "Using JAVA_HOME: $JAVA_HOME"
echo "Using ANDROID_HOME: $ANDROID_HOME"

# Build the app
./gradlew assembleDebug --no-daemon