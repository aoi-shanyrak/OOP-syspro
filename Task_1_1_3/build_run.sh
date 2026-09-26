#!/bin/bash

set -e

SRC_DIR="src/main/java"
OUT_DIR="build/classes"
DOC_DIR="build/docs/javadoc"

mkdir -p "$OUT_DIR" "$DOC_DIR"

echo "Compiling..."
find "$SRC_DIR" -name "*.java" | xargs javac -d "$OUT_DIR"

echo "Running application..."
java -cp "$OUT_DIR" expressions.Main

echo "Done."
