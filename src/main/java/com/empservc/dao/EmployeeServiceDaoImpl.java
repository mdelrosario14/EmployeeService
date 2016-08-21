package com.empservc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.empservc.exception.EmployeeServiceException;
import com.empservc.model.Employee;
import com.empservc.model.PersonalInfo;
import com.empservc.utils.QueryManager;

@Repository
public class EmployeeServiceDaoImpl implements EmployeeServiceDao {
	
	@Autowired
	QueryManager queryManager;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private static final Logger LOGGER = Logger.getLogger(EmployeeServiceDaoImpl.class);
	private static class EmployeeMapper implements RowMapper<Employee> {

		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Employee employee = new Employee();
			employee.setEmployeeId(rs.getLong("employee_id"));
			PersonalInfo personalInfo = new PersonalInfo();
			personalInfo.setFirstName(rs.getString("firstname"));
			personalInfo.setLastName(rs.getString("lastname"));
			personalInfo.setMiddleName(rs.getString("middlename"));
			personalInfo.setGender(rs.getString("gender"));
			employee.setPersonalInfo(personalInfo);
			
			return employee;
		}
	
	}

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> allEmployees = null;
		try {
			String sql = this.queryManager.getQuery("getAllEmployeeInformation");
		    allEmployees = this.namedParameterJdbcTemplate.getJdbcOperations().query(
		    		sql, new EmployeeMapper());
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return allEmployees;
	}

	@Override
	public void registerEmployeePersonalInfo(Employee employee) throws EmployeeServiceException {
		try {
			String sql = this.queryManager.getQuery("insertEmployeePersonalInfo");
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("firstname", employee.getPersonalInfo().getFirstName());
			param.addValue("lastname", employee.getPersonalInfo().getLastName());
			param.addValue("middlename", employee.getPersonalInfo().getMiddleName());
			param.addValue("gender", employee.getPersonalInfo().getGender());
			this.namedParameterJdbcTemplate.update(sql, param);
			
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("Failed to insert new employee", e);
			throw new EmployeeServiceException(e);
		}
		
	}
	
	
}
