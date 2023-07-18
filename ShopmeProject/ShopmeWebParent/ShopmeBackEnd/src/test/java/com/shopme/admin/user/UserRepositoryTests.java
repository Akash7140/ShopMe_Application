package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userAkash = new User("akashkrsingh7140@gmail.com","akash123","Akash","Kumar Singh");
		userAkash.addRole(roleAdmin);
		
		User savedUser = repo.save(userAkash);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRoles() {
		
		User userRahul = new User("rahul123@gmail.com","rahul123","Rahul","Mishra");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userRahul.addRole(roleAssistant);
		userRahul.addRole(roleEditor);
		
		User savedUser = repo.save(userRahul);
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userAkash = repo.findById(1).get();
		assertThat(userAkash).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userAkash = repo.findById(1).get();
		userAkash.setEnabled(true);
		repo.save(userAkash);
	}
	
	@Test
	public void testUpdateRoles() {
		User userRahul = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSales = new Role(2);
		
		userRahul.getRoles().remove(roleEditor);
		userRahul.addRole(roleSales);
		
		repo.save(userRahul);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "akashkrsingh7140@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnableStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 1;
		repo.updateEnableStatus(id, false);
	}

}
