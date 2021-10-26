# Celo Java SDK Native Android development example

This repository demonstraits utilizing the Celo Java SDK within a native Android project.
To use, Clone/fork to explore usage.

- Incorporating Celo Java SDK
- Creating/restoring a wallet
  - Persisting wallet details
- Querying the Celo blockchain for wallet details

## How do I include the Celo Java SDK within my Android native project?
One issue I had, was the lack of hosting support for the Java SDK resulting in gradle dependancy issues. To overcome this issue, I built the library as a jar file and included it within this example project.

Follow the steps below to learn the steps to resolve issues for usage in your own project.

## Configure Android development environment

- Install android studio v4.2.1 OSX
- Accessed https://github.com/blaize-tech/celo-sdk-java
- Review readme.md
- Installed dependencies
  - Org.web3j:core:4.6.0-android
- Sync Gradle

## Problem: Package hosting issue preventing gradle updates

[pic here]

## Solution: Let’s build the celo-sdk-library!


- Git clone https://github.com/blaize-tech/celo-sdk-java
- Use android studio v4.2.1 command line interface to build
  - ```./gradlew clean build -xtest```

### Problem: Gradle might have build issues depending on your build environment
example pic

### Solution: Gradle Build on OSX

- Install brew to manage gradle project
  - ```brew install gradle```
- Testing gradle wrapper
  - ```./gradlew wrapper --gradle-version=6.0 distribution-type=bin```
- Installed Java development kit version 8 due to discrepancies with recent v16
- Verified Prerequisites: Java 8 & Gradle 6
- ```brew install --cask adoptopenjdk/openjdk/adoptopenjdk8```

### Build completed!
pic here

## Include library into project

### Move jar to libs folder
pic here

### Verify & Edit build.gradle
pic here

## Utilize contractKit
pic

## Utilize web3j
pic

## Example usage
pic
