package com.pk.dumb_bakend.model;

import lombok.Data;

@Data
public class User {
  Integer idUser;
  String email;
  String nick;
  String password;
}
