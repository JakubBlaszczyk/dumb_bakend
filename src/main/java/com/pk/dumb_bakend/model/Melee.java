package com.pk.dumb_bakend.model;

import lombok.Data;

@Data
public class Melee {
  Integer idWeapon;
  String name;
  Integer strengthReq;
  Integer damage;
  String location;
}
