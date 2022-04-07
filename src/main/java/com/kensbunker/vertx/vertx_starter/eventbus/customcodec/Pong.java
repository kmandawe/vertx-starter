package com.kensbunker.vertx.vertx_starter.eventbus.customcodec;

public class Pong {
  private Integer id;

  public Pong() {
    // Default constructor
  }

  public Pong(final Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Pong{" +
      "id=" + id +
      '}';
  }
}
