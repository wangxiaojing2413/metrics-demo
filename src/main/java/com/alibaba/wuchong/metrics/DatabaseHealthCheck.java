package com.alibaba.wuchong.metrics;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

public class DatabaseHealthCheck extends HealthCheck {
    private final Database database;
    private final static HealthCheckRegistry healthChecks = new HealthCheckRegistry();

    public DatabaseHealthCheck(Database database) {
        this.database = database;
    }

    @Override
    public HealthCheck.Result check() throws Exception {
        if (database.isConnected()) {
            return HealthCheck.Result.healthy();
        } else {
            return HealthCheck.Result.unhealthy("Cannot connect to " + database.getUrl());
        }
    }

    public static void main(String[] args) throws Exception{
        Database db = new Database();
        DatabaseHealthCheck checkHealth = new DatabaseHealthCheck(db);
        healthChecks.register("postgres", new DatabaseHealthCheck(db));

        while(true){
            Map<String,Result> results = healthChecks.runHealthChecks();
            for(Entry<String, Result> entry : results.entrySet()) {
                if (entry.getValue().isHealthy()) {
                    System.out.println(entry.getKey() +" is healthy");
                } else {
                    System.err.println(entry.getKey() +" is UNHEALTHY: " + entry.getValue().getMessage());
                }
            }
            Thread.sleep(1000);
        }
    }
}