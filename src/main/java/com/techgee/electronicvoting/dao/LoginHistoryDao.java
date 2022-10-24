package com.techgee.electronicvoting.dao;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.techgee.electronicvoting.exception.VotingException;
import com.techgee.electronicvoting.model.LoginHistory;
import com.techgee.electronicvoting.shared.Parameters;

@Repository
public class LoginHistoryDao implements GenericDao<LoginHistory, Parameters, String> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;
	
	public static final String BY_LOGIN_ID = "LoginId"; 
	
	@Override
	public Optional<LoginHistory> createV1(@NotNull LoginHistory loginHistory, Parameters parameters) {
			return getV1(new Parameters(insert(loginHistory, parameters)));
	}

	@Override
	public Long insert(LoginHistory loginHistory, Parameters parameters) {
		KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(con -> {
				PreparedStatement ps = con.prepareStatement(environment.getProperty("LoginHistory.create"), new String[] { "login_history_id" });
				if(loginHistory.getLoginId() == null) {
					ps.setObject(1, null);
				} else { 
					ps.setLong(1, loginHistory.getLoginId());
				}
				if(loginHistory.getStartDate() == null) {
					ps.setObject(2, null);
				} else { 
					ps.setObject(2, loginHistory.getStartDate());
				}
				return ps;
			}, holder);
		return holder.getKey().longValue();	
	}

	@Override
	public Optional<LoginHistory> getV1(Parameters parameters)  {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("LoginHistory.get"), loginHistoryRowMapper,
				parameters.getId()));  //LoginId
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new VotingException("LoginHistory get method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}
	}

	@Override
	public Optional<LoginHistory> getV1(Parameters parameters, String whereClause)  {
		Object parameter [] = switch(whereClause) {
		case BY_LOGIN_ID -> new Object [] {
				parameters.getId() //login Id
		};
		default -> throw new VotingException("The requested method is not implemented");
		};
		try {
				return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("Login.getBy" + whereClause), loginHistoryRowMapper, parameter));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new VotingException("Login get "+ parameters.getId()  +" where method return more than one result."
					+ " Contact developer");
		} catch (Exception e) {
			throw new VotingException( e.getMessage());
		}
	}

	@Override
	public List<LoginHistory> list(Parameters parameters)  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoginHistory> list(Parameters parameters, String whereClause)  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<LoginHistory> updateV1(LoginHistory loginHistory, Parameters parameters) {
		jdbcTemplate.update(environment.getProperty("loginHistory.update"), 
				loginHistory.getEndDate());
		try {
			return getV1(new Parameters(loginHistory.getLoginHistoryId()));
		} catch (Exception e) {
			throw new VotingException( e.getMessage());
		}
	}

	@Override
	public int delete(LoginHistory persistentObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Parameters parameters, String whereClause)  {
		// TODO Auto-generated method stub
		return 0;
	}
	
RowMapper<LoginHistory> loginHistoryRowMapper = (rs, rowNum) -> {
		
		LoginHistory loginHistory = new LoginHistory();
			
		loginHistory.setLoginId(rs.getObject("login_id") != null ? rs.getLong("login_id") : null);
			
		loginHistory.setStartDate(rs.getObject("start_date") != null ? LocalDateTime.ofInstant(rs.getDate("start_date").toInstant(), ZoneId.systemDefault()): null);

		loginHistory.setEndDate(rs.getObject("end_date") != null ? LocalDateTime.ofInstant(rs.getDate("end_date").toInstant(), ZoneId.systemDefault()) : null);

		return loginHistory;
	};

}
