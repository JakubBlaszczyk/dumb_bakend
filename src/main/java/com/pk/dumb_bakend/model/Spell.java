package com.pk.dumb_bakend.model;

import lombok.Data;

@Data
public class Spell {
  Integer idSpell;
  String name;
  String effect;
  Integer manaCost;
  Integer requiredLevel;
  String location;
}
