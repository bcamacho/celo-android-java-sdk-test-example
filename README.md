# Celo Java SDK Native Android development example


#### This repository demonstraits utilizing the Celo Java SDK within a native Android project.
To use, Clone/fork to explore usage.

- Incorporating Celo Java SDK
- Creating/restoring a wallet
  - Persisting wallet details
- Querying the Celo blockchain for wallet details

![image](https://user-images.githubusercontent.com/2653576/138954935-9c474905-b2eb-48bf-a1f5-f83070588e41.png)

## How do I include the Celo Java SDK within my Android native project?
One issue I had, was the lack of hosting support for the Java SDK resulting in gradle dependancy issues. To overcome this issue, I built the library as a jar file and included it within this example project.

__Follow the steps below to learn the steps to resolve issues for usage in your own project.__

## Configure Android development environment

- Install android studio v4.2.1 OSX
- Goto: https://github.com/blaize-tech/celo-sdk-java
- Review readme.md
- Installed dependencies
  - Org.web3j:core:4.6.0-android
- Sync Gradle

## Problem: Package hosting issue preventing gradle updates

![image](https://user-images.githubusercontent.com/2653576/138954479-5fc37578-f795-40f9-9ac8-9fe55c9a0235.png)

## Solution: Let’s build the celo-sdk-library!


- Git clone https://github.com/blaize-tech/celo-sdk-java
- Use android studio v4.2.1 command line interface to build
  - ```./gradlew clean build -xtest```

### Problem: Gradle might have build issues depending on your build environment

![image](https://user-images.githubusercontent.com/2653576/138954536-8d3b2026-7ea4-4d26-a73a-11dad7e20a8f.png)


### Solution: Gradle Build on OSX

- Install brew to manage gradle project
  - ```brew install gradle```
- Testing gradle wrapper
  - ```./gradlew wrapper --gradle-version=6.0 distribution-type=bin```
- Installed Java development kit version 8 due to discrepancies with recent v16
- Verified Prerequisites: Java 8 & Gradle 6
- ```brew install --cask adoptopenjdk/openjdk/adoptopenjdk8```

### Build completed!

![image](https://user-images.githubusercontent.com/2653576/138954588-9bc4b4d8-bcdf-4f8d-8f3f-ab3b633a0eb3.png)


## Include library into project

### Move jar to libs folder

![image](https://user-images.githubusercontent.com/2653576/138954633-a0323e6d-3161-410e-a1b3-ac0327f64e4f.png)
![image](https://user-images.githubusercontent.com/2653576/138954656-db4e9797-eeab-4cd7-8204-4992f9425cc8.png)


### Verify & Edit build.gradle dependancies

![image](https://user-images.githubusercontent.com/2653576/138954722-4e889e87-0525-43b0-8a57-e5fad9a7952f.png)


## contractKit

![image](https://user-images.githubusercontent.com/2653576/138954789-0e3825ff-1d9a-4168-9caf-562073f58c0e.png)


## web3j

![image](https://user-images.githubusercontent.com/2653576/138954860-98b88a30-8a22-4177-a6e1-6fe6cde4b647.png)
