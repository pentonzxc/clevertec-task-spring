# Стек
Приложение было создано на JDK 17.0.5 , Gradle 7.5 , Spring Boot v.3.0.0.
СУБД - PostgreSQL, ORM - Hibernate / JPA specification ( Spring Data JPA ). Web - Spring MVC WEB.
Для тестирования - Mockito, JUnit5. Для конфигурирования БД - запустить main.sql. Есть готовые продукты и скидочные карты - использовать их.

# Инструкция по запуску

build/libs/clevertec-task-spring-boot-0.0.1-SNAPSHOT.jar [args] - jar для запуска проекта, args - могут указываться, это может быть строка в соотвествии с условием ( 3-1 2-5 5-1 card-1234 ), может быть файл ( src/main/resources/in.txt ).
Без указания аргументов - exception without shutdown. Можно использовать эндпоинт для формирования чека ( указан в эндпоинтах ). По умолчанию все выводится в консоль и в файл ( src/main/resources/out.txt ).

# Endpoints

GET http://localhost:8080/check - with query like:
http://localhost:8080/check?itemId=1&quantity=5&itemId=1&card=2222
http://localhost:8080/check?itemId=1&itemId=1&card=2222
http://localhost:8080/check?itemId=1&itemId=1
http://localhost:8080/check?itemId=1&quantity=10


# GitHub
https://github.com/pentonzxc/clevertec-task-spring

