package com.pk.dumb_bakend.model;

import lombok.Data;

@Data
public class Ranged {
  Integer idWeapon;
  String name;
  Integer requirement;
  Integer damage;
  String location;
}
