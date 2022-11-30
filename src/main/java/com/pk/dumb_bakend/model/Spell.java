package com.pk.dumb_bakend.model;

import lombok.Data;

@Data
public class Spell {
  Integer idSpell;
  String name;
  String effect;
  String manaCost;
  Integer requiredLevel;
  String location;
}
