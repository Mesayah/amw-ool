# Concurrent & Distributed Programming
## Requirements
* Java Runtime Environment 8 or higher

## Build & Installation
    ./gradlew clean build installDist

## Help & Execution Parameters
The following command execute programs with default parameters. To specify your own see help for each program (`--help` or `-h`).

## Concurrent Processing
The program executes network action (downloading data) and dependant computing (n-th Fibonacci element) both synchronously and asynchronously.

    concurrency/build/install/concurrency/bin/concurrency

In the first case blocking operations such as data downloading are executed faster when processed asynchronously. In case of dependant operations such as computing Fibonacci sequence elements, the asynchronous processing is slower due to thread creation overhead which is bigger than profit from concurrent processing of these operations.

## Synchronization
The program concurrently executes counter incrementation with and without thread synchronization.

     synchronization/build/install/synchronization/bin/synchronization

When executed without synchronization the counter never reaches expected value (10000) if executed on multicore procesor.

## WebScraping
The program downloads content of a given Wikipedia page (only Polish).

    webscraping/build/install/webscraping/bin/webscraping <wiki keyword>
    
where `<wiki keyword>` is searched keyword, eg.

    webscraping/build/install/webscraping/bin/webscraping Polska
    
will search for "Polska" (eng. "Poland") wiki page and print its content.

## Messaging
The program uses RabbitMQ for asynchronous message sending.
It's composed of two separate applications: Consumer and Publisher.
It needs an instance of RabbitMQ, which you can launch in a Docker container.

Launch RabbitMQ Docker container.

    docker run -d --hostname my-rabbit --name some-rabbit -p 5672:5672 rabbitmq:3

Launch Consumer application.

    messaging/consumer/build/install/consumer/bin/consumer
    
Launch Publisher application providing message content.

    messaging/publisher/build/install/publisher/bin/publisher "hello world"
    
Verify if Consumer received the message.
To close Consumer press `Ctrl+C`.

    docker container rm some-rabbit

Remove created Docker container.
    
## Reactive Streams
The program consists of reactive pipeline composed to encrypt or decrypt provided data.

    Still in progress...

# Machine Learning
## Classification
The program loads data of 101 animals and constructs decision tree to classify new data sets.

    classification/build/install/classification/bin/classification learn -d zoo.arff

## Regresja
Aplikacja pobiera dane charakteryzujące zestaw budynków..

    regression/build/install/regression/bin/regression learn -d data.csv

## Klastrowanie
Aplikacja tworzy klastry dla danych bankowych.

    clustering/build/install/clustering/bin/clustering -d bank-data.arff

