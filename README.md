[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Cinematic Journey

_Track your cinematic journey through interconnected movies_

## Goal

This project aims to demonstrate how to make a
production [Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/) app
with [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).

## Requirements

* IntelliJ IDEA / Android Studio / Fleet, with Android SDK
* XCode 15.2 for the iOS app

## Setup

Create the file `local.properties` after cloning the project to be able to compile/run

```properties
sdk.dir=<Path to Android SDK>
tmdb.api.key=<Your TMDB API Key>
```

On MacOS, your Android SDK is probably located at `/Users/<user>/Library/Android/sdk`.

## Run

### Android

Open the project in IntelliJ IDEA, Android Studio or Fleet and run the generated run configuration.

### iOS

Open the XCode project using XCode 15.2 and run the target.

### JVM / Desktop

Run gradle task `desktopRun` from your IDE or terminal:

```bash
./gradlew desktopRun
```

## Attribution

This application uses TMDB and the TMDB APIs but is not endorsed, certified, or otherwise approved by TMDB.

## License

```
Copyright 2024 Wizbii

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
