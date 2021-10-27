# Celo Java SDK Native Android development example


#### This repository demonstraits utilizing the Celo Java SDK within a native Android project.
To use, follow the instructions below to build your own or clone/fork to explore usage.

Codebase includes:
- Incorporating Celo Java SDK
- Creating/restoring a wallet
  - Persisting wallet details
- Querying the Celo blockchain for wallet details
</br>


<img src="https://user-images.githubusercontent.com/2653576/138954935-9c474905-b2eb-48bf-a1f5-f83070588e41.png" width="50%">
</br>

### Build the celo-sdk-library


- Git clone https://github.com/blaize-tech/celo-sdk-java
- Use android studio v4.2.1 command line interface to build
  - ```./gradlew clean build -xtest```
</br>

![image](https://user-images.githubusercontent.com/2653576/138954588-9bc4b4d8-bcdf-4f8d-8f3f-ab3b633a0eb3.png)


## Include library into project

### Move jar into your project libs folder

![image](https://user-images.githubusercontent.com/2653576/138954633-a0323e6d-3161-410e-a1b3-ac0327f64e4f.png)
![image](https://user-images.githubusercontent.com/2653576/138954656-db4e9797-eeab-4cd7-8204-4992f9425cc8.png)


### Verify & Edit build.gradle dependancies

![image](https://user-images.githubusercontent.com/2653576/138954722-4e889e87-0525-43b0-8a57-e5fad9a7952f.png)


## Example: contractKit

![image](https://user-images.githubusercontent.com/2653576/138954789-0e3825ff-1d9a-4168-9caf-562073f58c0e.png)


## Example: web3j object wrapped with contractKit

![image](https://user-images.githubusercontent.com/2653576/138954860-98b88a30-8a22-4177-a6e1-6fe6cde4b647.png)
