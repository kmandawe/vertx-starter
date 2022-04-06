package com.kensbunker.vertx.vertx_starter.json;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonObjectExample {

  @Test
  void jsonObjectCanBeMapped() {
    final JsonObject myJsonObject = new JsonObject();
    myJsonObject.put("id", 1);
    myJsonObject.put("name", "Alice");
    myJsonObject.put("loves_vertex", true);

    final String encoded = myJsonObject.encode();
    assertEquals("{\"id\":1,\"name\":\"Alice\",\"loves_vertex\":true}", myJsonObject.encode());

    final JsonObject decodedJsonObject = new JsonObject(encoded);
    assertEquals(myJsonObject, decodedJsonObject);
  }

  @Test
  void jsonObjectCanBeCreatedFromMap() {
    final Map<String, Object> myMap = new HashMap<>();
    myMap.put("id", 1);
    myMap.put("name", "Alice");
    myMap.put("loves_vertex", true);
    final JsonObject asJsonObject = new JsonObject(myMap);
    assertEquals(myMap, asJsonObject.getMap());
    assertEquals(1, asJsonObject.getInteger("id"));
    assertEquals("Alice", asJsonObject.getString("name"));
    assertEquals(true, asJsonObject.getBoolean("loves_vertex"));
  }

  @Test
  void jsonArrayCanBeMapped() {
    final JsonArray myJsonArray = new JsonArray();
    myJsonArray
        .add(new JsonObject().put("id", 1))
        .add(new JsonObject().put("id", 2))
        .add(new JsonObject().put("id", 3))
        .add("randomValue");
    assertEquals("[{\"id\":1},{\"id\":2},{\"id\":3},\"randomValue\"]", myJsonArray.encode());
  }

  @Test
  void canMapJavaObjects() {
    Person person = new Person(1, "Alice", true);
    final JsonObject alice = JsonObject.mapFrom(person);
    assertEquals(person.getId(), alice.getInteger("id"));
    assertEquals(person.getName(), alice.getString("name"));
    assertEquals(person.isLovesVertex(), alice.getBoolean("lovesVertex"));

    Person person2 = alice.mapTo(Person.class);
    assertEquals(person.getId(), person2.getId());
    assertEquals(person.getName(), person2.getName());
    assertEquals(person.isLovesVertex(), person2.isLovesVertex());
  }
}
