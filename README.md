# myRetail
myRetail Restful service

# Technologies

JDK 1.8.0

H2 database

Maven 4.0

SpringBoot 2.2.2.RELEASE

EhCache 

# Build, Test and Run application 


cd MyRetailRestfulService

Then run 

mvn clean package

Then run the jar

java -jar target/MyRetailRestfulService-0.0.1-SNAPSHOT.jar

Application will start running on port 8080


# Calling api services

Performing GET request on http://localhost:8080/product/13860428 will return json object with product information and pricing information.

GET http://localhost:8080/product/13860428

Response:-

{
  "id": 13860428,
  "name": "The Big Lebowski (Blu-ray)",
  "productPrice": null
}


To perform PUT operation, send JSON object with updated price in request body, it will return JSON object with updated pricing information.

PUT http://localhost:8080/product/13860428

Request Body:-

{"id":13860428,"name":"Test","productPrice":{"id":13860428, "price":999.00, "currencyCode":"USD"}}


Response :-
{
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray)",
    "productPrice": {
        "id": 13860428,
        "price": 999.00,
        "currencyCode": "USD"
    }
}

perform get again - GET http://localhost:8080/product/13860428
It will return - 
{"id":13860428,"name":"The Big Lebowski (Blu-ray)","productPrice":{"id":13860428,"price":999.00,"currencyCode":"USD"}}



