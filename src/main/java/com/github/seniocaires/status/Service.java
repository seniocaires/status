package com.github.seniocaires.status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.HttpMethod;

@ApplicationScoped
public class Service {

  @PersistenceContext(unitName = "status-unit")
  private EntityManager entityManager;

  public List<Servico> buscarTodos() {
    return entityManager.createNamedQuery("Servico.buscarTodos", Servico.class).getResultList();
  }

  public void salvar(Servico servico) {
    entityManager.merge(servico);
  }

  public void checar() {
    for (Servico servico : buscarTodos()) {
      try {
        URL url = new URL(servico.getLink());
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

        conexao.setRequestMethod(HttpMethod.GET);

        int responseCode;

        responseCode = conexao.getResponseCode();

        Logger.getLogger(Service.class.getName()).log(Level.FINEST, "Request para " + servico.getNome() + " - Response Code: " + responseCode);

        if (responseCode != 200) {
          BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
          String inputLine;
          StringBuilder response = new StringBuilder();

          while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
          in.close();
          Logger.getLogger(Service.class.getName()).log(Level.SEVERE, response.toString());
          servico.setStatus(Status.OFFLINE);
        }

        servico.setStatus(Status.ONLINE);
      } catch (ConnectException e) {
        servico.setStatus(Status.OFFLINE);
      } catch (IOException e) {
        servico.setStatus(Status.OFFLINE);
      } finally {
        salvar(servico);
      }
    }
  }
}
