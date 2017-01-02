package ozden.apps.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import ozden.apps.entities.User;

// make DB operations transactional like insert record, delete record etc.
@Transactional

//repository to do some queries from DB
//queries can be created like findBy<ColumnnName>
public interface UsersRepository extends JpaRepository<User, String>{
	// find all records
	List<User> findAll();
	// find by user name
	List<User> findByUserName(String userName);
	// find by user name and status flag
	List<User> findByUserNameAndStatusFlag(String userName, Integer statusFlag);
}
