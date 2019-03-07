# Programowanie równoległe i rozproszone
## Wymagania
* Java Runtime Environment 8 lub wyższa

## Budowanie & Instalacja
    ./gradlew clean build installDist

## Przetwarzanie współbieżne
    concurrency/build/install/concurrency/bin/concurrency
W pierwszym przypadku operacje blokujące wątek takie jak pobieranie danych z sieci są wykonywane szybciej podczas 
przetwarzania współbieżnego. W przypadku operacji zależnych od wyników poprzedzających jak np. w przypadku obliczania 
elementów ciągu Fibbonaciego, przetwarzanie asynchroniczne jest wolniejsze, gdyż overhead z tytułu tworzenia i 
uruchamiania wątku jest większy niż zysk z przetwarzania współbieżnego danych operacji.

## Synchronizacja
     synchronization/build/install/synchronization/bin/synchronization
Po wykonaniu bez synchronizacji licznik (`counter`) nigdy nie pownien wynosić 10000 (oczekiwana wartość) na procesorze 
z wieloma rdzeniami.

## WebScraping
Program pobiera treść ze strony Wikipedii o wskazanym haśle.

    webscraping/build/install/webscraping/bin/webscraping <wiki keyword>
gdzie `<wiki keyword>` jest szukanym hasłem, np.

    webscraping/build/install/webscraping/bin/webscraping Polska
wyszuka strone o tytule "Polska" i wyświetli jej treść.

## Messaging
Program wykorzystuje RabbitMQ do asynchronicznego przesyłania wiadomośći.
Składa się z dwóch małych aplikacji: Consumera oraz Publishera.
Do poprawnego działania wymaga instancji RabbitMQ, którą można uruchomić np. w kontenerze Dockera.

Uruchom RabbitMQ w Dockerze

    docker run -d --hostname my-rabbit --name some-rabbit -p 5672:5672 rabbitmq:3

Uruchom aplikację Consumer

    messaging/consumer/build/install/consumer/bin/consumer
    
Uruchom aplikację Publisher podając treść wiadomości do wysłania

    messaging/publisher/build/install/publisher/bin/publisher <message>
    
Zweryfikuj odebranie odpowiedzi w aplikacji Consumer.
    
## Reactive Streams
Aplikacja skłąda się z reaktywnego pipeline skomponowanego w celu szyfrowania przetworzonych danych.

    Wciąż w budowie...

# Machine Learning
## Klasyfikacja
Aplikacja pobiera dane 101 zwierząt i buduje drzewo decyzyjne w celu klasyfikcji nowych zestawów danych.

    classification/build/install/classification/bin/classification learn -d zoo.arff
