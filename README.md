# EXLA Android SDK Example

This repository contains a sample Android application that demonstrates how to integrate and use the EXLA Android SDK in your projects.

## Overview

This example app shows how to:
- Initialize the EXLA SDK
- Download AI models
- Generate text responses using the AI model

## Prerequisites

- Android Studio Arctic Fox or newer
- Android SDK 24+
- JDK 17

## Getting Started

1. Clone this repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device

## App Structure

- **MainActivity**: Main screen that demonstrates SDK initialization, model download, and text generation
- **LocalModelLoader**: Utility class that helps with loading model files from assets

## SDK Integration

The EXLA Android SDK is integrated via JitPack. See the `settings.gradle.kts` and `app/build.gradle.kts` files for integration details.

```kotlin
// In app/build.gradle.kts
dependencies {
	implementation("com.github.exla-ai:exla-android-sdk:407fc21173")
}
```

## Features Demonstrated

1. **SDK Initialization**: Shows how to initialize the EXLA AI SDK
2. **Model Download**: Demonstrates downloading and loading AI models
3. **Text Generation**: Shows how to generate text responses using the loaded model

