package com.github.seniocaires.status;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Singleton;
import javax.inject.Inject;

import de.mirkosertic.cdicron.api.Cron;

@Singleton
public class CronJob {

  @Inject
  private Service service;

  @Cron(cronExpression = "0 0/1 * * * ?")
  public void scheduledMethod() {
    Logger.getLogger(CronJob.class.getName()).log(Level.SEVERE, "Checando...");
    
    service.checar();
  }
}
