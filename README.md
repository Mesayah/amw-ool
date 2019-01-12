# Programowanie równoległe i rozproszone
## Wymagania
* Java Runtime Environment 8 lub wyższa

## Jak zbudować?
    ./gradlew clean build

## Przetwarzanie współbieżne
    java -jar concurrency/build/libs/concurrency-0.0.1.jar
W pierwszym przypadku operacje blokujące wątek takie jak pobieranie danych z sieci są wykonywane szybciej podczas 
przetwarzania współbieżnego. W przypadku operacji zależnych od wyników poprzedzających jak np. w przypadku obliczania 
elementów ciągu Fibbonaciego, przetwarzanie asynchroniczne jest wolniejsze, gdyż overhead z tytułu tworzenia i 
uruchamiania wątku jest większy niż zysk z przetwarzania współbieżnego danych operacji.

## Synchronizacja
    java -jar synchronization/build/libs/synchronization-0.0.1.jar
Po wykonaniu bez synchronizacji licznik (`counter`) nigdy nie pownien wynosić 10000 (oczekiwana wartość) na procesorze 
z wieloma rdzeniami.

## WebScraping
Program pobiera treść ze strony Wikipedii o wskazanym haśle.

    java -jar webscraping/build/libs/webscraping-0.0.1.jar <wiki keyword>
gdzie `<wiki keyword>` jest szukanym hasłem, np.

    java -jar webscraping/build/libs/webscraping-0.0.1.jar Polska
wyszuka strone o tytule "Polska" i wyświetli jej treść.