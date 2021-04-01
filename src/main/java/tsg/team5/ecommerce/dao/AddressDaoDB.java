package tsg.team5.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoDB {
    @Autowired
    JdbcTemplate jdbc;

}
