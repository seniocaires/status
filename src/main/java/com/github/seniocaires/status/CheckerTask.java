package com.github.seniocaires.status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.HttpMethod;

/**
 * @author Senio Caires
 */
public class CheckerTask extends TimerTask {

  /**
   * @author Senio Caires
   */
  public void run() {
    for (Servico servico : Util.servicosMock()) {
      try {
        URL url = new URL(servico.getLink());
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

        conexao.setRequestMethod(HttpMethod.GET);

        int responseCode;

        responseCode = conexao.getResponseCode();

        Logger.getLogger(CheckerTask.class.getName()).log(Level.FINEST, "Request para " + servico.getNome() + " - Response Code: " + responseCode);

        if (responseCode != 200) {
          BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
          String inputLine;
          StringBuilder response = new StringBuilder();

          while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
          in.close();
          Logger.getLogger(CheckerTask.class.getName()).log(Level.SEVERE, response.toString());
          servico.setStatus(Status.OFFLINE);
        }

        servico.setStatus(Status.ONLINE);
      } catch (ConnectException e) {
        servico.setStatus(Status.OFFLINE);
        Logger.getLogger(CheckerTask.class.getName()).log(Level.SEVERE, e.getMessage(), e);
      } catch (IOException e) {
        servico.setStatus(Status.OFFLINE);
        Logger.getLogger(CheckerTask.class.getName()).log(Level.SEVERE, e.getMessage(), e);
      }
    }
  }
}
