package com.techgee.electronicvoting.dao;

import java.sql.PreparedStatement;
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

import com.techgee.electronicvoting.model.Login;
import com.techgee.electronicvoting.shared.Parameters;

@Repository
public class LoginDao implements GenericDao<Login, Parameters, String> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;

	@Override
	public Optional<Login> createV1(@NotNull Login login, Parameters parameters) {
		try {
			return getV1(new Parameters(insert(login, parameters)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Long insert(Login login, Parameters parameters) {
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			jdbcTemplate.update(con -> {
				PreparedStatement ps = con.prepareStatement(environment.getProperty("Login.create"), new String[] { "login_id" });
				if(login.getPartyId() == null) {
					ps.setObject(1, null);
				} else { 
					ps.setLong(1, login.getPartyId());
				}
				if(login.getUserId() == null) {
					ps.setObject(2, null);
				} else { 
					ps.setObject(2, login.getUserId());
				}
				if(login.getStartDate() == null) {
					ps.setObject(3, null);
				} else { 
					ps.setObject(3, login.getStartDate());
				}
				if(login.getEndDate() == null) {
					ps.setObject(4, null);
				} else { 
					ps.setObject(4, login.getEndDate());
				}
				return ps;
			}, holder);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return holder.getKey().longValue();	
	}

	@Override
	public Optional<Login> getV1(Parameters parameters) throws Exception {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("Login.get"), loginRowMapper,
				parameters.getId()));  //LoginId
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new Exception("PartyRole get method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Optional<Login> getV1(Parameters parameters, String whereClause) throws Exception {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Login> list(Parameters parameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Login> list(Parameters parameters, String whereClause) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Login> updateV1(Login transientObject, Parameters parameters) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public int delete(Login persistentObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Parameters parameters, String whereClause) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	RowMapper<Login> loginRowMapper = (rs, rowNum) -> {
		
		Login login = new Login();
			
		login.setLoginId(rs.getObject("login_id") != null ? rs.getLong("login_id") : null);

		login.setPartyId(rs.getObject("party_id_party") != null ? rs.getLong("party_id_party") : null);

		login.setUserId(rs.getObject("user_id") != null ? rs.getString("user_id") : null);
			
		login.setStartDate(rs.getObject("start_date") != null ? rs.getDate("start_date").toLocalDate() : null);

		login.setEndDate(rs.getObject("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);

		return login;
	};


}
