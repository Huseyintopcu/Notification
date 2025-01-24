package tr.edu.ogu.ceng.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tr.edu.ogu.ceng.notification.entity.User;

@Repository
@Component
public interface UserRepo extends JpaRepository<User, Long> {

    User getByName(String name);

    User findByPhoneNumber( String phoneNumber);

    @Query("select  u from  User  u where  u.email = ?1")
    User findByEmail(String email);

    @Query("select  u from  User u where u.name = ?1 and u.phoneNumber = ?2")
    User findByNameAndPhoneNumber(String name, String phoneNumber);

}
