package vinh;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.viet.entity.coursesGroupEntity;
import com.viet.entity.userEntity;
import com.viet.repository.userRepository;
import com.viet.services.impl.userService;
@RunWith(MockitoJUnitRunner.class)
public class userServiceTest {
	@Mock
	private userRepository uR;
	@InjectMocks
	private userService us;
	/*
	 * Hàm test getAllUser Success
	 * pqvinh
	 * */
	@Test
	public void testGetAllUserSuccess() {
		// Given
		userEntity ue = new userEntity();
		ue.setUsername("Vinh");
		userEntity ue1 = new userEntity();
		when(uR.findByusername(ue.getUsername())).thenReturn(ue1);
		ue1.setPassword("123");
		ue.setPassword("123");
		// when
		String res = us.getAll(ue);
		// then
		assertEquals("Success", res);
		
		verify(uR, times(1)).findByusername(ue.getUsername());
	}
	
	/*
	 * Hàm test Password is incorrect
	 * pqvinh
	 * */
	@Test
	public void testGetAllUserByPasswordIsIncorrect() {
		// Given
		userEntity ue = new userEntity();
		ue.setUsername("Vinh");
		userEntity ue1 = new userEntity();
		when(uR.findByusername(ue.getUsername())).thenReturn(ue1);
		ue1.setPassword("123");
		ue.setPassword("321");
		// when
		String res = us.getAll(ue);
		// then
		assertEquals("Password is incorrect", res);
		
		verify(uR, times(1)).findByusername(ue.getUsername());
	}
	/*
	 * Hàm test Username is incorrect
	 * pqvinh
	 * */
	@Test
	public void testGetAllUserByUserNameIsIncorrect() {
		// Given
		userEntity ue = new userEntity();
		ue.setUsername("Vinh");
		userEntity ue1 = new userEntity();
		when(uR.findByusername(ue.getUsername())).thenReturn(null);
		// when
		String res = us.getAll(ue);
		// then
		assertEquals("Username is incorrect ", res);
		
		verify(uR, times(1)).findByusername(ue.getUsername());
	}
}
