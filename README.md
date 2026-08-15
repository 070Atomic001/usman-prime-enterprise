name: Build Android APK

on:
  workflow_dispatch:
  push:
    branches: ["main"]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v5

      - name: Find Android project ZIP
        run: |
          ZIP_FILE=""
          for f in *.zip; do
            if [ -f "$f" ] && unzip -l "$f" | grep -q "settings.gradle"; then
              ZIP_FILE="$f"
              break
            fi
          done

          if [ -z "$ZIP_FILE" ]; then
            echo "No Android project ZIP found."
            exit 1
          fi

          echo "Using: $ZIP_FILE"
          mkdir android-project
          unzip -q "$ZIP_FILE" -d android-project

      - name: Set up Java
        uses: actions/setup-java@v5
        with:
          distribution: temurin
          java-version: "17"

      - name: Set up Gradle
        uses: gradle/actions/setup-gradle@v4
        with:
          gradle-version: "8.9"

      - name: Build APK
        working-directory: android-project
        run: gradle assembleDebug --stacktrace

      - name: Upload APK
        uses: actions/upload-artifact@v4
        with:
          name: usman-prime-enterprise-apk
          path: android-project/app/build/outputs/apk/debug/app-debug.apk
