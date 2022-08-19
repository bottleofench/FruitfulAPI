# FruitfulAPI

An additional API that simplifies and extends the Spigot / Paper API.

**Warning! The API works on Paper, but it isn't work on Spigot!**

## Features

### Simple ItemStackBuilder

Soon...

### Simple BlockEditor

Soon...

### Simple InventoryBuilder

Soon...

### Simple EntityBuilder

Soon...

### Custom Events

Soon...

## Working with API

1. Add our API as a library to your project. There are three ways to do this: using Maven / Gradle or manually adding a jar file to your project.

### Maven

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.bottleofench</groupId>
    <artifactId>FruitfulAPI</artifactId>
    <version>-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

### Gradle
```
allprojects {
    repositories {
    	...
    	maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.bottleofench:FruitfulAPI:-SNAPSHOT'
}
```

2. Add the API as a plugin to your server, and then in your plugin specify our API in the depend section (or softdepend):

```yml
name: MegaTestPlugin
version: 0.1
main: api.bottleofench.megatestplugin.MegaTestPlugin
api-version: 1.19
depend:
  - FruitfulAPI
softdepend:
  - FruitfulAPI
```

## API Examples

Soon...

## TODOs

- Сomplete the README.md;
