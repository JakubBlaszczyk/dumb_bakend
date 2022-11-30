package com.pk.dumb_bakend;

import static spark.Spark.*;

import com.google.gson.Gson;
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

public class Main {

  private static JdbcService jdbcService;

  public static void main(String[] args) {

    jdbcService.createConnection("jdbc:sqlite:path");
    Gson gsonBuilder = new Gson();
    // String id = req.params(":id");
    // armor
    // ArmorRepository armorRepository = new ArmorRepository(jdbcService.getConnection());
    get(
        "/armor",
        (req, resp) -> {
          return "X";
        });
    post(
        "/armor",
        (req, resp) -> {
          return "X";
        });
    put(
        "/armor",
        (req, resp) -> {
          return "X";
        });
    delete(
        "/armor",
        (req, resp) -> {
          return "X";
        });

    // melee
    MeleeRepository meleeRepository = new MeleeRepository(jdbcService.getConnection());
    get(
        "/melee",
        (req, resp) -> {
          return gsonBuilder.toJson(
              meleeRepository.get(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
    get(
        "/melee/all",
        (req, resp) -> {
          return gsonBuilder.toJson(meleeRepository.getAll());
        });
    post(
        "/melee",
        (req, resp) -> {
          return gsonBuilder.toJson(
              meleeRepository.create(gsonBuilder.fromJson(req.body(), Melee.class)));
        });
    put(
        "/melee",
        (req, resp) -> {
          return gsonBuilder.toJson(
              meleeRepository.update(gsonBuilder.fromJson(req.body(), Melee.class)));
        });
    delete(
        "/melee",
        (req, resp) -> {
          return gsonBuilder.toJson(
              meleeRepository.delete(gsonBuilder.fromJson(req.body(), Integer.class)));
        });

    // potion
    PotionRepository potionRepository = new PotionRepository(jdbcService.getConnection());
    get(
        "/potion",
        (req, resp) -> {
          return gsonBuilder.toJson(
              potionRepository.get(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
    get(
        "/potion/all",
        (req, resp) -> {
          return gsonBuilder.toJson(potionRepository.getAll());
        });
    post(
        "/potion",
        (req, resp) -> {
          return gsonBuilder.toJson(
              potionRepository.create(gsonBuilder.fromJson(req.body(), Potion.class)));
        });
    put(
        "/potion",
        (req, resp) -> {
          return gsonBuilder.toJson(
              potionRepository.update(gsonBuilder.fromJson(req.body(), Potion.class)));
        });
    delete(
        "/potion",
        (req, resp) -> {
          return gsonBuilder.toJson(
              potionRepository.delete(gsonBuilder.fromJson(req.body(), Integer.class)));
        });

    // ranged
    RangedRepository rangedRepository = new RangedRepository(jdbcService.getConnection());
    get(
        "/ranged",
        (req, resp) -> {
          return gsonBuilder.toJson(
              rangedRepository.get(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
    get(
        "/ranged/all",
        (req, resp) -> {
          return gsonBuilder.toJson(rangedRepository.getAll());
        });
    post(
        "/ranged",
        (req, resp) -> {
          return gsonBuilder.toJson(
              rangedRepository.create(gsonBuilder.fromJson(req.body(), Ranged.class)));
        });
    put(
        "/ranged",
        (req, resp) -> {
          return gsonBuilder.toJson(
              rangedRepository.update(gsonBuilder.fromJson(req.body(), Ranged.class)));
        });
    delete(
        "/ranged",
        (req, resp) -> {
          return gsonBuilder.toJson(
              rangedRepository.delete(gsonBuilder.fromJson(req.body(), Integer.class)));
        });

    // session
    post(
        "/login",
        (req, resp) -> {
          return "X";
        });
    post(
        "/register",
        (req, resp) -> {
          return "X";
        });

    // spell
    SpellRepository spellRepository = new SpellRepository(jdbcService.getConnection());
    get(
        "/spell",
        (req, resp) -> {
          return gsonBuilder.toJson(
              spellRepository.get(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
    get(
        "/spell/all",
        (req, resp) -> {
          return gsonBuilder.toJson(spellRepository.getAll());
        });
    post(
        "/spell",
        (req, resp) -> {
          return gsonBuilder.toJson(
              spellRepository.create(gsonBuilder.fromJson(req.body(), Spell.class)));
        });
    put(
        "/spell",
        (req, resp) -> {
          return gsonBuilder.toJson(
              spellRepository.update(gsonBuilder.fromJson(req.body(), Spell.class)));
        });
    delete(
        "/spell",
        (req, resp) -> {
          return gsonBuilder.toJson(
              spellRepository.delete(gsonBuilder.fromJson(req.body(), Integer.class)));
        });

    // users
    UserRepository userRepository = new UserRepository(jdbcService.getConnection());
    get(
        "/user",
        (req, resp) -> {
          return gsonBuilder.toJson(
              userRepository.get(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
    get(
        "/user/all",
        (req, resp) -> {
          return gsonBuilder.toJson(userRepository.getAll());
        });
    post(
        "/user",
        (req, resp) -> {
          return gsonBuilder.toJson(
              userRepository.create(gsonBuilder.fromJson(req.body(), User.class)));
        });
    put(
        "/user",
        (req, resp) -> {
          return gsonBuilder.toJson(
              userRepository.update(gsonBuilder.fromJson(req.body(), User.class)));
        });
    delete(
        "/user",
        (req, resp) -> {
          return gsonBuilder.toJson(
              spellRepository.delete(gsonBuilder.fromJson(req.body(), Integer.class)));
        });
  }
}
