#!/usr/bin/env bash
set -euo pipefail

# Bash bootstrap for Gradle on Unix-like systems
GRADLE_VERSION="8.6"
DIST_URL="https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip"
WRAPPER_DIR="$(cd "$(dirname "$0")" && pwd)/.gradle/gradle-${GRADLE_VERSION}"
ZIP_FILE="$(cd "$(dirname "$0")" && pwd)/gradle-${GRADLE_VERSION}-bin.zip"

if [ ! -x "${WRAPPER_DIR}/bin/gradle" ]; then
  echo "Gradle ${GRADLE_VERSION} not found. Downloading..."
  if command -v curl >/dev/null 2>&1; then
    curl -L -o "${ZIP_FILE}" "${DIST_URL}"
  elif command -v wget >/dev/null 2>&1; then
    wget -O "${ZIP_FILE}" "${DIST_URL}"
  else
    echo "curl or wget required to download Gradle"
    exit 1
  fi
  unzip -q "${ZIP_FILE}" -d "$(cd "$(dirname "$0")" && pwd)/.gradle"
  rm -f "${ZIP_FILE}"
fi

"${WRAPPER_DIR}/bin/gradle" "$@"
