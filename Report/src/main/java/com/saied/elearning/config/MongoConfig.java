package com.saied.elearning.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.saied.elearning.report")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${mongodb.hostname}")
    public String mongoDBhostName;

    @Value("${mongodb.port}")
    public int mongoDBport;

    @Override
    protected String getDatabaseName() {
        return "report_db";
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(
            "mongodb://%s:%d/%s".formatted(mongoDBhostName, mongoDBport, getDatabaseName())
        );
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.saied.elearning.report");
    }
}
