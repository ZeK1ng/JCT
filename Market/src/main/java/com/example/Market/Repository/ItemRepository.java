package com.example.Market.Repository;
import com.example.Market.Model.Client;
import com.example.Market.Model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item,Integer>  {
}
