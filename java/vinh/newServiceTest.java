package vinh;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.viet.converter.newConverter;
import com.viet.dto.NewDTO;
import com.viet.entity.courseEntity;
import com.viet.entity.coursesGroupEntity;
import com.viet.entity.scheduleEntity;
import com.viet.repository.courseRepository;
import com.viet.repository.coursesGroupRepository;
import com.viet.repository.schedulesRepository;
import com.viet.services.IschedulesService;
import com.viet.services.impl.newService;
@RunWith(MockitoJUnitRunner.class)
public class newServiceTest {
	@Mock
	private coursesGroupRepository cGR;
	@Mock
	private courseRepository cR;
	@Mock
	private newConverter nC;
	@Mock
	private schedulesRepository sR;
	@Mock
	private IschedulesService schedulesService;
	@InjectMocks
	private newService ns;
	
	/*
	 * Hàm test saveDuplidate
	 * pqvinh
	 * */
	@Test
	public void testSaveDupticate() {
		//Given
		String courseGroupCode = "MH001";
		coursesGroupEntity c = new coursesGroupEntity();
		NewDTO ndto = new NewDTO();
		c.setCourseCode("MH001");
		ndto.setGroupCode(1);
		ndto.setCourse_code(courseGroupCode);
		when(cGR.findByGroupCodeAndCourseCode(ndto.getGroupCode(), ndto.getCourse_code())).thenReturn(c);
		
		// when
		String result = ns.save(ndto);
		
		// then
		assertEquals("Duplicated", result);
		
		verify(cGR, times(1)).findByGroupCodeAndCourseCode(ndto.getGroupCode(), ndto.getCourse_code());
		verify(cGR, never()).save(any(coursesGroupEntity.class));
		
	}
	/*
	 * Hàm test save success
	 * pqvinh
	 * */
	@Test
	public void testSaveSuccess() {
		//Given
		String courseGroupCode = "MH001";
		coursesGroupEntity c = new coursesGroupEntity();
		NewDTO ndto = new NewDTO();
		c.setCourseCode("MH001");
		ndto.setGroupCode(1);
		ndto.setCourse_code(courseGroupCode);
		
		when(cGR.findByGroupCodeAndCourseCode(ndto.getGroupCode(), ndto.getCourse_code())).thenReturn(null);
		coursesGroupEntity c1 = new coursesGroupEntity();
		when(nC.toEntity(ndto)).thenReturn(c1);
		// when
		String result = ns.save(ndto);
		
		// then
		assertEquals("Success", result);
		
		verify(cGR, times(1)).findByGroupCodeAndCourseCode(ndto.getGroupCode(), ndto.getCourse_code());
		verify(cGR, times(1)).save(c1);
		
	}
	/*
	 * Hàm test update duplidate
	 * pqvinh
	 * */
	@Test
	public void testUpdateDuplicate() {
		//Given
		coursesGroupEntity c = new coursesGroupEntity();
		NewDTO ndto = new NewDTO();
		ndto.setGroupCode(1);
		ndto.setCourse_code("MH001");
		ndto.setId(1);
		when(cGR.findByGroupCodeAndCourseCodeAndIdNot(ndto.getGroupCode(), ndto.getCourse_code(), ndto.getId())).thenReturn(c);
		// when
		String res = ns.update(ndto);
		// then
		assertEquals("Duplicated", res);
		
		verify(cGR, times(1)).findByGroupCodeAndCourseCodeAndIdNot(ndto.getGroupCode(), ndto.getCourse_code(), ndto.getId());
		verify(cGR, never()).save(any(coursesGroupEntity.class));
	}
	/*
	 * Hàm test update success
	 * pqvinh
	 * */
	@Test
	public void testUpdateSuccess() {
		//Given
		coursesGroupEntity c = new coursesGroupEntity();
		NewDTO ndto = new NewDTO();
		ndto.setGroupCode(1);
		ndto.setCourse_code("MH001");
		ndto.setId(1);
		when(cGR.findByGroupCodeAndCourseCodeAndIdNot(ndto.getGroupCode(), ndto.getCourse_code(), ndto.getId())).thenReturn(null);
		coursesGroupEntity c1 = new coursesGroupEntity();
		coursesGroupEntity c2 = new coursesGroupEntity();
		when(cGR.findOne(ndto.getId())).thenReturn(c1);
		when(nC.toEntity(ndto, c1)).thenReturn(c2);
		// when
		String res = ns.update(ndto);
		// then
		assertEquals("Success", res);
		
		verify(cGR, times(1)).findByGroupCodeAndCourseCodeAndIdNot(ndto.getGroupCode(), ndto.getCourse_code(), ndto.getId());
		verify(cGR, times(1)).save(c2);
	}
}
