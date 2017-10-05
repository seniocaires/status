package com.github.seniocaires.status;

public class Servico {

  private String nome;

  private String link;

  private Status status = Status.LOADING;

  public Servico() {
  }

  public Servico(String nome, String link) {
    this.nome = nome;
    this.link = link;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getLink() {
    return this.link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public Status getStatus() {
    return this.status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
