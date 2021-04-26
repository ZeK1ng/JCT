package com.example.Market.service;

import com.example.Market.constants.StringConstants;
import com.example.Market.Entity.DBSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;


@Service
public class DBSequenceGenerator {
    private final MongoOperations mongoOperations;

    @Autowired
    public DBSequenceGenerator(MongoOperations mongoOperations){
        this.mongoOperations = mongoOperations;
    }
    public int getSequenceNumber(){
        Query query = new Query(Criteria.where("seqname").is(StringConstants.SEQUENCE_NAME));
        Update update = new Update().inc("seq",1);
        DBSequence dbc = mongoOperations.findAndModify(query,update, options().returnNew(true).upsert(true),DBSequence.class);
        return !Objects.isNull(dbc) ? dbc.getSeq() : 1;
    }
}
