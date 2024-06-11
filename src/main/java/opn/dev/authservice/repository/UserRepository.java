package opn.dev.authservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import opn.dev.authservice.dao.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    
}