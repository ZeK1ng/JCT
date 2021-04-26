package com.example.Market.repository;
import com.example.Market.Entity.ReportEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<ReportEntity,Integer>  {
}
