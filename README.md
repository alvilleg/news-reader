### News Reader

Make sure you are using Java 11

To compile
```shell
./gradlew clean build
```

To execute
```shell
java -jar app/build/libs/app.jar <news url>
```

Example:
```shell
java -jar app/build/libs/app.jar https://syndicator.univision.com/web-api/content\?url\=https://www.univision.com/noticias\&lazyload\=false
```

