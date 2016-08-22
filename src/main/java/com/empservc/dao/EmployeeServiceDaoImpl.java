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
			LOGGER.error(e.getMessage());
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
			
			sql = this.queryManager.getQuery("getNewEmployeeId");
			int employeeId = this.namedParameterJdbcTemplate.getJdbcOperations().queryForInt(sql);
			if (employeeId > 0) {
				sql = this.queryManager.getQuery("insertEmployeeAddressInfo");
				param.addValue("employee_id", employeeId);
				param.addValue("address_no", employee.getAddressInfo().getIdentifier());
				param.addValue("streetname", employee.getAddressInfo().getStreetName());
				param.addValue("villagename", employee.getAddressInfo().getVillageName());
				param.addValue("cityname", employee.getAddressInfo().getCityName());
				param.addValue("statename", employee.getAddressInfo().getStateName());
				param.addValue("zip", employee.getAddressInfo().getZip());
				this.namedParameterJdbcTemplate.update(sql, param);
				
				sql = this.queryManager.getQuery("insertEmployeeContactInfo");
				for (Long contactNo : employee.getContactInfo().getPhoneNumbers()) {
					param.addValue("employee_id", employeeId);
					param.addValue("contact_no", contactNo);
					this.namedParameterJdbcTemplate.update(sql, param);
				}
			}
			
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("Failed to insert new employee", e);
			throw new EmployeeServiceException(e);
		}
		
	}

	@Override
	public boolean isEmployeeExists(Employee employee)  throws EmployeeServiceException {
		boolean isEmployeeExist = false;
		try {
			String sql = this.queryManager.getQuery("checkIfEmployeeExists");
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("firstname", employee.getPersonalInfo().getFirstName());
			param.addValue("lastname", employee.getPersonalInfo().getLastName());
			param.addValue("middlename", employee.getPersonalInfo().getMiddleName());
			
			int employeeExist = this.namedParameterJdbcTemplate.queryForInt(sql, param);
			if (employeeExist > 0) {
				isEmployeeExist = true;
			}
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("Failed to check if employee exists.", e);
			throw new EmployeeServiceException(e);
		}
		return isEmployeeExist;
	}

	@Override
	public void updateEmployeePersonalInfo(Employee employee) throws EmployeeServiceException {
		try {
			String sql = this.queryManager.getQuery("updateEmployeePersonalInfo");
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("firstname", employee.getPersonalInfo().getFirstName());
			param.addValue("lastname", employee.getPersonalInfo().getLastName());
			param.addValue("middlename", employee.getPersonalInfo().getMiddleName());
			param.addValue("gender", employee.getPersonalInfo().getGender());
			param.addValue("employee_id", employee.getEmployeeId());
			
			this.namedParameterJdbcTemplate.update(sql, param);
		}  catch (DataAccessException | SQLException e) {
			LOGGER.error("Failed to update employee-personal.", e);
			throw new EmployeeServiceException(e);
		}
		
	}

	@Override
	public void updateEmployeeAddressInfo(Employee employee) throws EmployeeServiceException {
		try {
			String sql = this.queryManager.getQuery("updateEmployeeAddressInfo");
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("employee_id", employee.getEmployeeId());
			param.addValue("address_no", employee.getAddressInfo().getIdentifier());
			param.addValue("streetname", employee.getAddressInfo().getStreetName());
			param.addValue("villagename", employee.getAddressInfo().getVillageName());
			param.addValue("cityname", employee.getAddressInfo().getCityName());
			param.addValue("statename", employee.getAddressInfo().getStateName());
			param.addValue("zip", employee.getAddressInfo().getZip());
			
			this.namedParameterJdbcTemplate.update(sql, param);
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("Failed to update employee-address.", e);
			throw new EmployeeServiceException(e);
		}
	}

	@Override
	public void updateEmployeeContactInfo(Employee employee) throws EmployeeServiceException {
		try {
			String sql = this.queryManager.getQuery("deleteEmployeeContactInfo");
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("employee_id", employee.getEmployeeId());
			this.namedParameterJdbcTemplate.update(sql, param);
			
			sql = this.queryManager.getQuery("insertEmployeeContactInfo");
			for (Long contactNo : employee.getContactInfo().getPhoneNumbers()) {
				param.addValue("contact_no", contactNo);
				this.namedParameterJdbcTemplate.update(sql, param);
			}

		} catch (DataAccessException | SQLException e) {
			LOGGER.error("Failed to update employee-address.", e);
			throw new EmployeeServiceException(e);
		}
		
	}

	@Override
	public void deleteEmployeeInfo(Long employeeId) throws EmployeeServiceException {
		try {
			String sql = this.queryManager.getQuery("deleteEmployeeAddressInfo");
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("employee_id", employeeId);
			this.namedParameterJdbcTemplate.update(sql, param);
			
			sql = this.queryManager.getQuery("deleteEmployeeContactInfo");
			this.namedParameterJdbcTemplate.update(sql, param);
			
			sql = this.queryManager.getQuery("deleteEmployeePersonalInfo");
			this.namedParameterJdbcTemplate.update(sql, param);

		} catch (DataAccessException | SQLException e) {
			LOGGER.error("Failed to delete employee", e);
			throw new EmployeeServiceException(e);
		}
		
	}
}
