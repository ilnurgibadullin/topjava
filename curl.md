## **Методы API**

Параметры запроса:

id - номер еды

startDate - от даты (формат: 2020-01-30)

endDate - до даты (формат: 2020-01-30)

startTime - от времени (формат: 15:48 = 15%3A48)

endTime - от времени (формат: 01:30 = 01%3A51)

**Получение всей еды** 

curl --header "Accept: application/json" "http://localhost:8080/topjava/rest/meals/"

**Получение одной еды**

curl --header "Accept: application/json" "http://localhost:8080/topjava/rest/meals/id"

**Удаление одной еды** 

curl -X DELETE --header "Accept: application/json" "http://localhost:8080/topjava/rest/meals/id"

**Обновление одной еды**

curl -X PUT 
--header "Content-Type: application/json" 
--header "Accept: application/json" 
-d '{"dateTime": "2020-01-31T20:00:00", "description": "Завтрак", "calories": 666}'
"http://localhost:8080/topjava/rest/meals/id"

**Создание еды**

curl -X POST --header "Content-Type: application/json" 
--header "Accept: application/json" 
-d '{"dateTime": "2020-01-31T20:00:00", "description": "Завтрак", "calories": 510}'
"http://localhost:8080/topjava/rest/meals"

**Получение еды с фильтрацией по времени:**

curl --header "Accept: application/json" 
"http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=&startTime=15%3A48&endTime="
