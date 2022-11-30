package com.pk.dumb_bakend;

import static spark.Spark.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.pk.dumb_bakend.model.Err;
import com.pk.dumb_bakend.model.Melee;
import com.pk.dumb_bakend.model.Potion;
import com.pk.dumb_bakend.model.Ranged;
import com.pk.dumb_bakend.model.Spell;
import com.pk.dumb_bakend.model.User;
import com.pk.dumb_bakend.repository.MeleeRepository;
import com.pk.dumb_bakend.repository.PotionRepository;
import com.pk.dumb_bakend.repository.RangedRepository;
import com.pk.dumb_bakend.repository.SpellRepository;
import com.pk.dumb_bakend.repository.UserRepository;
import java.time.Instant;
import java.util.Date;

public class Main {

  private static boolean checkPrivs(String reqRole, String jwtRole) {
    if (reqRole.equals("admin") && jwtRole.equals("admin")) {
      return true;
    }
    if (reqRole.equals("mod") && (jwtRole.equals("admin") || jwtRole.equals("mod"))) {
      return true;
    }
    if (reqRole.equals("user")
        && (jwtRole.equals("admin") || jwtRole.equals("mod") || jwtRole.equals("user"))) {
      return true;
    }
    return false;
  }

  public static void main(String[] args) {
    Gson gsonBuilder = new Gson();

    JdbcService jdbcService;
    jdbcService = new JdbcService();
    jdbcService.createConnection("jdbc:sqlite:tempDb");

    UserRepository userRepository = new UserRepository(jdbcService.getConnection());
    MeleeRepository meleeRepository = new MeleeRepository(jdbcService.getConnection());
    PotionRepository potionRepository = new PotionRepository(jdbcService.getConnection());
    RangedRepository rangedRepository = new RangedRepository(jdbcService.getConnection());
    SpellRepository spellRepository = new SpellRepository(jdbcService.getConnection());
    Algorithm algorithm = Algorithm.HMAC256("supertajemnysekret");

    exception(Exception.class, (e, request, response) -> {
      System.out.println("EXCEPTION");
      e.printStackTrace();
    });

    get(
        "/armor",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("user", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return "X";
        });
    post(
        "/armor",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return "X";
        });
    put(
        "/armor",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return "X";
        });
    delete(
        "/armor",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return "X";
        });

    // melee
    get(
        "/melee",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("user", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              meleeRepository.get(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
    get(
        "/melee/all",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("user", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(meleeRepository.getAll());
        });
    post(
        "/melee",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              meleeRepository.create(gsonBuilder.fromJson(req.body(), Melee.class)));
        });
    put(
        "/melee",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              meleeRepository.update(gsonBuilder.fromJson(req.body(), Melee.class)));
        });
    delete(
        "/melee",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              meleeRepository.delete(gsonBuilder.fromJson(req.body(), Integer.class)));
        });

    // potion
    get(
        "/potion",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("user", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              potionRepository.get(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
    get(
        "/potion/all",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("user", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(potionRepository.getAll());
        });
    post(
        "/potion",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              potionRepository.create(gsonBuilder.fromJson(req.body(), Potion.class)));
        });
    put(
        "/potion",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              potionRepository.update(gsonBuilder.fromJson(req.body(), Potion.class)));
        });
    delete(
        "/potion",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              potionRepository.delete(gsonBuilder.fromJson(req.body(), Integer.class)));
        });

    // ranged
    get(
        "/ranged",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("user", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              rangedRepository.get(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
    get(
        "/ranged/all",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("user", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(rangedRepository.getAll());
        });
    post(
        "/ranged",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              rangedRepository.create(gsonBuilder.fromJson(req.body(), Ranged.class)));
        });
    put(
        "/ranged",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              rangedRepository.update(gsonBuilder.fromJson(req.body(), Ranged.class)));
        });
    delete(
        "/ranged",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              rangedRepository.delete(gsonBuilder.fromJson(req.body(), Integer.class)));
        });

    // session
    post(
        "/login",
        (req, resp) -> {
          try {
            return JWT.create()
                .withClaim("id", "testUser")
                .withClaim("role", "mod")
                .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                .sign(algorithm);
          } catch (JWTCreationException exception) {
            throw new RuntimeException("yyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
          }
        });
    post(
        "/register",
        (req, resp) -> {
          userRepository.create(gsonBuilder.fromJson(req.body(), User.class));
          return "X";
        });

    // spell
    get(
        "/spell",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("user", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              spellRepository.get(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
    get(
        "/spell/all",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("user", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(spellRepository.getAll());
        });
    post(
        "/spell",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              spellRepository.create(gsonBuilder.fromJson(req.body(), Spell.class)));
        });
    put(
        "/spell",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              spellRepository.update(gsonBuilder.fromJson(req.body(), Spell.class)));
        });
    delete(
        "/spell",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              spellRepository.delete(gsonBuilder.fromJson(req.body(), Integer.class)));
        });

    // users
    get(
        "/user",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              userRepository.get(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
    get(
        "/user/all",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("mod", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(userRepository.getAll());
        });
    post(
        "/user",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("admin", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              userRepository.create(gsonBuilder.fromJson(req.body(), User.class)));
        });
    put(
        "/user",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("admin", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              userRepository.update(gsonBuilder.fromJson(req.body(), User.class)));
        });
    delete(
        "/user",
        (req, resp) -> {
          try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
            if (!checkPrivs("admin", jwt.getClaim("role").asString())) {
              resp.status(403);
              return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
            }
          } catch (Exception e) {
            System.out.println("Exception msg: " + e.getMessage());
            resp.status(403);
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              spellRepository.delete(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
  }
}
