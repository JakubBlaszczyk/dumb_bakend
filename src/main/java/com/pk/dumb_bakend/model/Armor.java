package com.pk.dumb_bakend.model;

import lombok.Data;

@Data
public class Armor {
  Integer idArmor;
  String name;
  Integer meleeRes;
  Integer rangedRes;
  Integer fireRes;
  Integer magicRes;
}
