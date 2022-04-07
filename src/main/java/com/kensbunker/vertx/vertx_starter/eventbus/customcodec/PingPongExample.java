package com.kensbunker.vertx.vertx_starter.eventbus.customcodec;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPongExample {

  private static final Logger LOG = LoggerFactory.getLogger(PingPongExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new PingVerticle(), logOnError());
    vertx.deployVerticle(new PongVerticle(), logOnError());
  }

  private static Handler<AsyncResult<String>> logOnError() {
    return ar -> {
      if (ar.failed()) {
        LOG.error("err", ar.cause());
      }
    };
  }

  static class PingVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(PingVerticle.class);
    public static final String ADDRESS = "my.request.address";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      final Ping message = new Ping("Hello", true);
      LOG.debug("Sending: {}", message);
      eventBus.<Pong>request(
          ADDRESS,
          message,
          reply -> {
            if (reply.failed()) {
              LOG.error("Failed: ", reply.cause());
              return;
            }
            LOG.debug("Response: {}", reply.result().body());
          });
    }
  }

  static class PongVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(PongVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx
          .eventBus()
          .<Ping>consumer(
              PingVerticle.ADDRESS,
              message -> {
                LOG.debug("Received message: {}", message.body());
                message.reply(new Pong(0));
              })
          .exceptionHandler(
              error -> {
                LOG.error("Error: ", error);
              });
    }
  }
}
