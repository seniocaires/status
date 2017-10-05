package com.github.seniocaires.status;

import java.util.ArrayList;
import java.util.List;

public class Util {

  static {
    Checker checker = new Checker(30);
    checker.start();
  }

  private static List<Servico> servicosMock;

  private Util() {
  }

  public static List<Servico> servicosMock() {

    if (servicosMock == null) {
      servicosMock = new ArrayList<Servico>();
      servicosMock.add(new Servico("Google", "https://www.google.com"));
      servicosMock.add(new Servico("Gitlab", "https://git.seniocaires.com.br"));
      servicosMock.add(new Servico("Senio Caires", "https://seniocaires.com.br"));
    }
    return servicosMock;
  }
}
