#!/bin/bash

# Install Java if not present
if ! command -v java >/dev/null 2>&1; then
    echo "Java not found, installing OpenJDK 17..."
    apt-get update && apt-get install -y openjdk-17-jdk
fi

# Find and set JAVA_HOME dynamically
JAVA_PATH=""
for possible_path in /usr/lib/jvm/java-17-openjdk-amd64 /usr/lib/jvm/default-java /usr/lib/jvm/*; do
    if [ -d "$possible_path/bin" ] && [ -f "$possible_path/bin/java" ]; then
        export JAVA_HOME="$possible_path"
        JAVA_PATH="$possible_path/bin/java"
        break
    fi
done

# Fallback: find java in PATH and deduce JAVA_HOME
if [ -z "$JAVA_HOME" ] && command -v java >/dev/null 2>&1; then
    JAVA_PATH=$(command -v java)
    RESOLVED_PATH=$(readlink -f "$JAVA_PATH" 2>/dev/null || echo "$JAVA_PATH")
    export JAVA_HOME=$(dirname "$(dirname "$RESOLVED_PATH")")
fi

# Final check
if [ ! -f "$JAVA_HOME/bin/java" ]; then
    echo "ERROR: Could not find valid Java installation"
    echo "JAVA_HOME is set to: $JAVA_HOME"
    exit 1
fi

# Set Android SDK
export ANDROID_HOME="/usr/lib/android-sdk"

# Add Java to PATH
export PATH="$JAVA_HOME/bin:$PATH"

echo "Using JAVA_HOME: $JAVA_HOME"
echo "Using ANDROID_HOME: $ANDROID_HOME"
echo "Java version: $(java -version 2>&1 | head -1)"

# Build the app
./gradlew assembleDebug --no-daemon