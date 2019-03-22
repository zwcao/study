package com.weston.study.boot.mongo.starter;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.mapping.MapperOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;

@Configuration
@EnableConfigurationProperties(MongodbProperties.class)
@ConditionalOnBean(MongodbProperties.class)
public class MongodbAutoConfiguration {

    @Bean
    public MongoClient mongoClient(MongodbProperties mongodbProperties) {
        MongoClientOptions.Builder builder = MongoClientOptions.builder().minConnectionsPerHost(mongodbProperties.getMinConnectionsPerHost())
                .connectionsPerHost(mongodbProperties.getMaxConnectionsPerHost())
                .connectTimeout(mongodbProperties.getConnectionTimeout())
                .socketTimeout(mongodbProperties.getSocketTimeout())
                .threadsAllowedToBlockForConnectionMultiplier(mongodbProperties.getThreadsAllowedToBlockForConnectionMultiplier());
        MongoClientURI mongoClientURI = new MongoClientURI(mongodbProperties.getUri(), builder);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        return mongoClient;
    }

    @Bean
    public Morphia morphia(MongodbProperties mongodbProperties) throws ClassNotFoundException {
        MapperOptions mapperOptions = new MapperOptions();
        mapperOptions.setMapSubPackages(true);
        mapperOptions.setCacheClassLookups(true);
        Morphia morphia = new Morphia(new Mapper(mapperOptions));
        if (mongodbProperties.getMapPackages() != null) {
            for (String packageName : mongodbProperties.getMapPackages().split(",")) {
                morphia.mapPackage(packageName, mongodbProperties.isIgnoreInvalidClasses());
            }
        }
        if (mongodbProperties.getMapClasses() != null) {
            for (String entityClass : mongodbProperties.getMapClasses().split(",")) {
                morphia.map(Class.forName(entityClass));
            }
        }
        return morphia;
    }

    @Bean
    public Datastore datastore(Morphia morphia, MongoClient mongoClient, MongodbProperties mongodbProperties) {
        Datastore datastore = morphia.createDatastore(mongoClient, mongodbProperties.getDbName());
        if (mongodbProperties.isEnsureIndexes()) {
            datastore.ensureIndexes();
        }
        if (mongodbProperties.isEnsureCaps()) {
            datastore.ensureCaps();
        }
        return datastore;
    }

}
