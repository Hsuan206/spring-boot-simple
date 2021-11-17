package com.example.employeemanager;

import com.example.employeemanager.Service.EmployeeService;
import com.example.employeemanager.controller.EmployeeController;
import com.example.employeemanager.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(EmployeeController.class)
class EmployeemanagerApplicationTests {
	@MockBean
	private EmployeeService service;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {
		Employee alex = new Employee("alex", "123@gmail.com","A","123","url","123");
		List<Employee> allEmployees = Arrays.asList(alex);
		when(service.findAllEmployees()).thenReturn(allEmployees);
		MvcResult mvcResult = this.mockMvc.perform(get("/api/employees")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
//				.andExpect(jsonPath("$", hasSize(1)))
//				.andExpect(jsonPath("$[0].name", is(alex.getName())))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		//断言，判断返回代码是否正确
		Assert.assertEquals(200,status);

	}

}
