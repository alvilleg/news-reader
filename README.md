### News Reader

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

