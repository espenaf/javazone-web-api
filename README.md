# javazone-web-api
Felles enterpri$e integrasjonsplattform.

## Deploy til nexus

Legg til følgende i settings.xml

```xml
<settings>
  <servers>
    <server>
      <id>javabin.nexus</id>
      <username>deployment</username>
      <password>spør-noen-i-javabin</password>
    </server>
  </servers>
</settings>
```

Og kjør mvn deploy

## Release til dev/test/prod

Deploy skjer via provisjoneringen i https://github.com/javaBin/infrastructure

Bruk javazone-web-api.yml-playbooken for å deploye appen.
