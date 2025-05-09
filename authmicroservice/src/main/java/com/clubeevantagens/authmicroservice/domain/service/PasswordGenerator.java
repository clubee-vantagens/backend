package com.clubeevantagens.authmicroservice.domain.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
  private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
  private static final String NUMBERS = "0123456789";
  private static final String SPECIAL_CHARACTERS = "@$!%*?&";

  private static final String ALL_CHARACTERS = UPPERCASE_LETTERS + LOWERCASE_LETTERS + NUMBERS + SPECIAL_CHARACTERS;

  private static final SecureRandom random = new SecureRandom();

  public static String generatePassword() {
    int length = random.nextInt(13) + 8;

    List<Character> password = new ArrayList<>();

    password.add(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));
    password.add(LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length())));
    password.add(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

    for (int i = password.size(); i < length; i++) {
      password.add(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
    }

    Collections.shuffle(password, random);

    StringBuilder sb = new StringBuilder();
    for (char c : password) {
      sb.append(c);
    }

    return sb.toString();
  }
}
