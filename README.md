steam4j
=======
Steam4j is a client library for accessing the Steam API. It uses 'Spring-esque' template classes to enable you to quickly access the steam API with common sense settings.
Designed with TDD in mind, Steam4J allows you to test your application using dummy data easily.

Quickstart
=======

```xml
<dependency>
    <groupId>uk.co.solong</groupId>
    <artifactId>steam4j</artifactId>
    <version>2.0.1</version>
</dependency>
```

```java
TF2Template dao = new TF2Template("API_KEY_HERE");
TF2Backpack backpack = dao.getPlayerItems(76561197971384027L)
if (Status.SUCCESS.equals(backpack.getStatus()){
    for (Item item: backpack.getItems()) {
        System.out.println(item.getLevel());
        //...
    }
}
```
