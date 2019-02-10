package com.codingdojo.admindashboard.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.codingdojo.admindashboard.models.User;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

	List<User> findAllByOrderByIdAsc();
	
	@Query("SELECT u FROM User u WHERE role <> ?1")
	List<User> findAllByNotAdmin(String x);
}
