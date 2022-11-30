package com.pk.dumb_bakend.model;

import lombok.Data;

@Data
public class Melee {
  Integer idWeapon;
  String name;
  String type;
  Integer strengthReq;
  Integer damage;
  String location;
}
