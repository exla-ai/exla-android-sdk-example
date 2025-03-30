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
    implementation("com.github.exla-ai:exla-android-sdk:v1.0.0-alpha01")
}
```

## Features Demonstrated

1. **SDK Initialization**: Shows how to initialize the EXLA AI SDK
2. **Model Download**: Demonstrates downloading and loading AI models
3. **Text Generation**: Shows how to generate text responses using the loaded model

## Preparing for Public Release

Before making this repository public, ensure you:

1. Remove any large files such as:
   - Java heap dump files (*.hprof)
   - The dummy model file in app/src/main/assets/dummy_model.gguf (or replace it with a small placeholder)
   
2. Remove any sensitive information:
   - API keys
   - Credentials
   - Private configuration details

3. Verify the `.gitignore` file properly excludes build artifacts and large binary files

## License

[Add your license information here]

## Support

For questions or issues regarding this example app, please create an issue in this repository.
For SDK-specific questions, refer to the [EXLA AI documentation](https://docs.exla.ai). 