# The Cookie Factory... in Spring (aka the simple TCFS)

  * Author: Philippe Collet
  * Author: Nassim Bounouas
  * Reviewer: Anne-Marie Déry
  * Reviewer: Nikita Rousseau
  * some code and doc borrowed from the original Cookie Factory by Sebastiem Mosser, last fork being [https://github.com/collet/4A_ISA_TheCookieFactory](https://github.com/collet/4A_ISA_TheCookieFactory)

This case study is used to illustrate the different technologies involved in the _Introduction to Software Architecture_  course given at Polytech Nice - Sophia Antipolis at the graduate level. This demonstration code requires the following software to run properly:

  * Build & Spring environment configuration: Maven >=3.8.5 (provided maven wrapper set to 3.8.6)
  * Spring/Java implementation language: Java >=17 or above (Java language level is set to Java 17), SpringBoot 2.7.7
  * NestJS 9.1+ (node 18+, npm 8.19+)

## Product vision

_The Cookie Factory_ (TCF) is a major bakery brand in the USA. The _Cookie on Demand_ (CoD) system is an innovative service offered by TCF to its customer. They can order cookies online thanks to an application, and select when they'll pick-up their order in a given shop. The CoD system ensures to TCF's happy customers that they'll always retrieve their pre-paid warm cookies on time.

## Chapters

  1. [Architecture](chapters/Architecture.md)
  2. [Business Components](chapters/BusinessComponents.md)
  3. [Controllers](chapters/Controllers.md)
  4. [Testing](chapters/Testing.md)
  5. No Persistence (in memory repository)
  6. [AOP, logging, and monitoring](chapters/AOPLogging.md)

## How to use this repository

  * the following tags keeps track of the two main versions:
    * The [release v1.1.0](https://github.com/CookieFactoryInSpring/simpleTCFS/tree/v1.1.0) contains the code that implements the system without persistence;
    * The release v2.1.0 will contain the persistence version.

The following "build and run" documentation is divided in three versions from bare run to "everything in a container" run.

### Basic build and run

The first step is to build the backend and the cli. This can be done manually using the command:

    mvn clean package
 
from both folders (it will generate the corresponding jar into the target folder). Note that this comand will only run unit tests, you can use:

    mvn clean verify
    
to run both unit and integration tests. See the page on [Testing](chapters/Testing.md#running-different-types-of-test-with-maven) for more details.

To run the server (from the corresponding folder):

    mvn spring-boot:run
    
or

    java -jar target/simpleTCFS-0.0.1-SNAPSHOT.jar

To run the cli (from the corresponding folder):

    mvn spring-boot:run
    
or

    java -jar target/cli-0.0.1-SNAPSHOT.jar

At startup the cli must provide the following prompt :

    shell:>

Running the command `help` will guide you in the CLI usage.

### Containerized backend

In this version, we will run the cli as previously, however we will run the backend in a docker container.

  * To build the backend docker image from the corresponding folder, the script `build.sh` can be used or directly the command `docker build --build-arg JAR_FILE=target/simpleTCFS-0.0.1-SNAPSHOT.jar -t pcollet/tcf-spring-backend .`

  * To run it the script `run.sh` can be used or directly `docker run --rm -d -p 8080:8080 pcollet/tcf-spring-backend`.

Note: It's necessary to stop the "basic" version of the backend to release the 8080 port.

### Everything containerized and composed

We will now run both the backend and the CLI into docker. It requires to build the cli docker image (the backend's one is considered built during the previous step).

To build the cli docker image from the corresponding folder, the script `build.sh` can be used or directly the command `docker build --build-arg JAR_FILE=cli-0.0.1-SNAPSHOT.jar -t pcollet/tcf-spring-cli .`

The whole system can now be deployed locally from the root folder using the command:

    docker-compose up -d
    
after few seconds:

    docker attach cli

enables to use the containerized cli (see docker-compose.yml and the devops lecture for more information).

Note that you cannot run the two docker images separately and expect them to communicate with each other, each one being isolated in its own container. That's one of the main purpose of `docker-compose` to enable composition of container, with by default a shared network.

The docker-compose file contains a volume declaration (see the cli section) to mount the `demo.txt` file which can be directly used from the cli as below to iterate through a complete scenario and check that verify is running fine :

```
shell:>script demo.txt
CHOCOLALALA
DARK_TEMPTATION
SOO_CHOCOLATE
[...]
```

Some actions will display intended errors :

```
403 : [{"error":"Attempting to update the cookie quantity to a negative value","details":"from Customer kadocwith cookie SOO_CHOCOLATE leading to quantity -1"}]
403 : [{"error":"Cart is empty","details":"from Customer kadoc"}]
409 : [no body]
400 : [{"error":"Payment was rejected","details":"from Customer tatie for amount 1.3"}]
```

## Tips & Troubleshoot

### "/usr/bin/env: 'bash\r': No such file or directory"

If during the docker-compose start up you encounter this error `/usr/bin/env: 'bash\r': No such file or directory` with the back or the cli container, you need to rebuild the corresponding image with the `wait-for-it.sh` configured to use LF end of line sequence instead CRLF. This can occur if you open the file with an editor like VSCode configured to use CRLF.


### Docker-compose retro-compatibility (Docker Desktop)

If Docker Desktop is used, the `Use Docker Compose V2` feature toggle must be activated within the Docker Desktop to ensure a compatibility with the former `docker-compose` executable name : [See more](https://docs.docker.com/compose/compose-v2/)

### Windows specific terminals

Few terminals are not considered as a TTY which will lead to an error at the `docker attach cli` command run. According the terminal used it might be necessary to prepend the command with another one. For example for `MinGW64` : 

```
$ docker attach cli
the input device is not a TTY.  If you are using mintty, try prefixing the command with 'winpty'

$ winpty docker attach cli
shell:>
```

## Docker Desktop Backend on Windows

The backend used for Docker Desktop on Windows can be either WSL or Hyper-V. WSL might require an update/upgrade. The procedure can be found on the following page and requires a restart : [https://aka.ms/wsl2kernel](https://aka.ms/wsl2kernel)

### How to detach from the CLI without stopping it ?

As mentionned on the [Docker CLI documentation](https://docs.docker.com/engine/reference/commandline/attach/#description) it's possible to detach from a running container and let it run using `CTRL+P` then `CTRL+Q`.

### build_all.sh output according the environment

The `build_all.sh` script output can be slightly different on windows than on Linux or Mac OS X depending on the environment it is executed without causing any damages. See the following (mingw64 terminal installed by git installer) :

```
** Building all
Compiling the TCF Spring BACKEND within a multi-stage docker build
#1 [internal] load build definition from Dockerfile
#1 sha256:b4da1a09722684c536ca109a15d57a497155b023ef064b46bd0202cd907f2695
#1 transferring dockerfile: 841B done
#1 DONE 0.0s

#2 [internal] load .dockerignore
#2 sha256:e317355cc535cec5252170bc3f4f01faad40d922e26e7fdceb6a256560d58447
#2 transferring context: 2B done
#2 DONE 0.0s

#4 [internal] load metadata for docker.io/library/eclipse-temurin:17-jdk-jammy
#4 sha256:a51bde80cf0615ff53399b6efd508bc3dc742fee4204aebe09d433f9034dcb55
#4 DONE 10.0s

[...]