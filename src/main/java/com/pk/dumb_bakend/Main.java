package com.pk.dumb_bakend;

import static spark.Spark.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.pk.dumb_bakend.model.Armor;
import com.pk.dumb_bakend.model.Err;
import com.pk.dumb_bakend.model.LoginData;
import com.pk.dumb_bakend.model.Melee;
import com.pk.dumb_bakend.model.Potion;
import com.pk.dumb_bakend.model.Ranged;
import com.pk.dumb_bakend.model.Spell;
import com.pk.dumb_bakend.model.User;
import com.pk.dumb_bakend.repository.ArmorRepository;
import com.pk.dumb_bakend.repository.MeleeRepository;
import com.pk.dumb_bakend.repository.PotionRepository;
import com.pk.dumb_bakend.repository.RangedRepository;
import com.pk.dumb_bakend.repository.SpellRepository;
import com.pk.dumb_bakend.repository.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.time.Instant;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import spark.Filter;
import spark.Request;
import spark.Response;

@Slf4j
public class Main {

  private static boolean checkPrivsInternal(String reqRole, String jwtRole) {
    log.info("reqRole: {}", reqRole);
    log.info("jwtRole: {}", jwtRole);
    if (reqRole.equalsIgnoreCase("admin") && jwtRole.equalsIgnoreCase("admin")) {
      return true;
    }
    if (reqRole.equalsIgnoreCase("mod") && (jwtRole.equalsIgnoreCase("admin") || jwtRole.equalsIgnoreCase("mod"))) {
      return true;
    }
    if (reqRole.equalsIgnoreCase("user")
        && (jwtRole.equalsIgnoreCase("admin") || jwtRole.equalsIgnoreCase("mod") || jwtRole.equalsIgnoreCase("user"))) {
      return true;
    }
    return false;
  }

  public static boolean checkPrivs(Algorithm algorithm, Request req, Response resp, String reqRole) {
    try {
      DecodedJWT jwt = JWT.require(algorithm).build().verify(req.headers("jwt"));
      if (!checkPrivsInternal(reqRole, jwt.getClaim("role").asString())) {
        resp.status(403);
        return false;
      }
    } catch (Exception e) {
      log.error("Exception msg: " + e.getMessage());
      e.printStackTrace();
      resp.status(403);
      return false;
    }
    return true;
  }

  public static void main(String[] args) {
    Gson gsonBuilder = new Gson();

    JdbcService jdbcService;
    jdbcService =
        new JdbcService(
            "jdbc:sqlite:tempDb",
            "src/resources/zbroje.csv",
            "src/resources/bron_b.csv",
            "src/resources/eliksiry.csv",
            "src/resources/bron_d.csv",
            "src/resources/czary.csv",
            "src/resources/uzytkownicy.csv");

    UserRepository userRepository = new UserRepository(jdbcService.getConnection());
    ArmorRepository armorRepository = new ArmorRepository(jdbcService.getConnection());
    MeleeRepository meleeRepository = new MeleeRepository(jdbcService.getConnection());
    PotionRepository potionRepository = new PotionRepository(jdbcService.getConnection());
    RangedRepository rangedRepository = new RangedRepository(jdbcService.getConnection());
    SpellRepository spellRepository = new SpellRepository(jdbcService.getConnection());
    Algorithm algorithm = Algorithm.HMAC256("supertajemnysekret");

    after((Filter) (request, response) -> {
      response.header("Access-Control-Allow-Origin", "*");
      response.header("Access-Control-Allow-Methods", "*");
    });

    exception(
        Exception.class,
        (e, request, response) -> {
          log.error("Exception msg: " + e.getMessage());
          e.printStackTrace();
        });

    get(
        "/armor/:id",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "user")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(armorRepository.get(Integer.parseInt(req.params(":id"))));
        });
    get(
        "/armor",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "user")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(armorRepository.getAll());
        });
    post(
        "/armor",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              armorRepository.create(gsonBuilder.fromJson(req.body(), Armor.class)));
        });
    put(
        "/armor",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              armorRepository.update(gsonBuilder.fromJson(req.body(), Armor.class)));
        });
    delete(
        "/armor/:id",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(armorRepository.delete(Integer.parseInt(req.params(":id"))));
        });

    // melee
    get(
        "/melee/:id",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "user")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(meleeRepository.get(Integer.parseInt(req.params(":id"))));
        });
    get(
        "/melee",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "user")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(meleeRepository.getAll());
        });
    post(
        "/melee",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              meleeRepository.create(gsonBuilder.fromJson(req.body(), Melee.class)));
        });
    put(
        "/melee",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              meleeRepository.update(gsonBuilder.fromJson(req.body(), Melee.class)));
        });
    delete(
        "/melee/:id",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(meleeRepository.delete(Integer.parseInt(req.params(":id"))));
        });

    // potion
    get(
        "/potion/:id",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "user")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(potionRepository.get(Integer.parseInt(req.params(":id"))));
        });
    get(
        "/potion",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "user")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(potionRepository.getAll());
        });
    post(
        "/potion",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              potionRepository.create(gsonBuilder.fromJson(req.body(), Potion.class)));
        });
    put(
        "/potion",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              potionRepository.update(gsonBuilder.fromJson(req.body(), Potion.class)));
        });
    delete(
        "/potion/:id",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(potionRepository.delete(Integer.parseInt(req.params(":id"))));
        });

    // ranged
    get(
        "/ranged/:id",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "user")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(rangedRepository.get(Integer.parseInt(req.params(":id"))));
        });
    get(
        "/ranged",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "user")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(rangedRepository.getAll());
        });
    post(
        "/ranged",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              rangedRepository.create(gsonBuilder.fromJson(req.body(), Ranged.class)));
        });
    put(
        "/ranged",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              rangedRepository.update(gsonBuilder.fromJson(req.body(), Ranged.class)));
        });
    delete(
        "/ranged/:id",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(rangedRepository.delete(Integer.parseInt(req.params(":id"))));
        });

    // session
    post(
        "/login",
        (req, resp) -> {
          try {
            LoginData loginData = gsonBuilder.fromJson(req.body(), LoginData.class);
            User user = userRepository.getByEmail(loginData.getEmail());

            log.error("UU_Email:" + user.getEmail());
            log.error("UU_Pass:" + user.getPassword());

            log.error("LD_Email:" + loginData.getEmail());
            log.error("LD_Pass:" + loginData.getPassword());

            BCrypt.Result res = BCrypt.verifyer().verify(loginData.getPassword().toCharArray(), user.getPassword());

            if (!res.verified) {
              return gsonBuilder.toJson(new Err("Invalid credentials"), Err.class);
            }

            return JWT.create()
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                .sign(algorithm);
          } catch (JWTCreationException exception) {
            throw new RuntimeException("yyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
          }
        });
    post(
        "/register",
        (req, resp) -> {
          User user = gsonBuilder.fromJson(req.body(), User.class);
          user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
          user.setRole("User");
          userRepository.create(user);
          return gsonBuilder.toJson(user, User.class);
        });

    // spell
    get(
        "/spell/:id",
        (req, resp) -> {
          
          if (!checkPrivs(algorithm, req, resp, "user")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(spellRepository.get(Integer.parseInt(req.params(":id"))));
        });
    get(
        "/spell",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "user")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(spellRepository.getAll());
        });
    post(
        "/spell",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              spellRepository.create(gsonBuilder.fromJson(req.body(), Spell.class)));
        });
    put(
        "/spell",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              spellRepository.update(gsonBuilder.fromJson(req.body(), Spell.class)));
        });
    delete(
        "/spell/:id",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(spellRepository.delete(Integer.parseInt(req.params(":id"))));
        });

    // users
    get(
        "/user/:id",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(userRepository.get(Integer.parseInt(req.params(":id"))));
        });
    get(
        "/user",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "mod")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(userRepository.getAll());
        });
    post(
        "/user",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "admin")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              userRepository.create(gsonBuilder.fromJson(req.body(), User.class)));
        });
    put(
        "/user",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "admin")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(
              userRepository.update(gsonBuilder.fromJson(req.body(), User.class)));
        });
    delete(
        "/user/:id",
        (req, resp) -> {
          if (!checkPrivs(algorithm, req, resp, "admin")) {
            return gsonBuilder.toJson(new Err("Invalid priv"), Err.class);
          }
          return gsonBuilder.toJson(spellRepository.delete(Integer.parseInt(req.params(":id"))));
        });
  }
}
