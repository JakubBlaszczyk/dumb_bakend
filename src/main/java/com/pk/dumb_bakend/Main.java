package com.pk.dumb_bakend;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
      // String id = req.params(":id");
      // armor
      get("/armor", (req, resp) -> {
        return "X";
      });
      post("/armor", (req, resp) -> {
        return "X";
      });
      put("/armor", (req, resp) -> {
        return "X";
      });
      delete("/armor", (req, resp) -> {
        return "X";
      });

      // melee
      get("/melee", (req, resp) -> {
        return "X";
      });
      post("/melee", (req, resp) -> {
        return "X";
      });
      put("/melee", (req, resp) -> {
        return "X";
      });
      delete("/melee", (req, resp) -> {
        return "X";
      });

      // potion blaszczyk
      get("/potion", (req, resp) -> {
        return "X";
      });
      post("/potion", (req, resp) -> {
        return "X";
      });
      put("/potion", (req, resp) -> {
        return "X";
      });
      delete("/potion", (req, resp) -> {
        return "X";
      });

      // TODO jew
      // ranged
      
      
      // roles
      get("/roles", (req, resp) -> {
        return "X";
      });
      post("/roles", (req, resp) -> {
        return "X";
      });
      put("/roles", (req, resp) -> {
        return "X";
      });
      delete("/roles", (req, resp) -> {
        return "X";
      });
      
      // session
      post("/login", (req, resp) -> {
        return "X";
      });
      post("/register", (req, resp) -> {
        return "X";
      });
      
      // TODO jew
      // spell


      // users
      get("/users", (req, resp) -> {
        return "X";
      });
      post("/users", (req, resp) -> {
        return "X";
      });
      put("/users", (req, resp) -> {
        return "X";
      });
      delete("/users", (req, resp) -> {
        return "X";
      });

      // post("/users", (request, response) -> {
      //   return "chuj";
      // });
      // get("/users", (request, response) -> {
      //   return "chuj";
      // });
      // get("/users/:id", (request, response) -> {
      //   return "";
      // });
      // put("/users/:id", (request, response) -> {
      //   return "";
      // });
      // delete("/users/:id", (request, response) -> {
      //   return "";
      // });
      // options("/users/:id", (request, response) -> {
      //   return "";
      // });
    }
}
