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

import com.techgee.electronicvoting.model.Password;
import com.techgee.electronicvoting.shared.Parameters;

@Repository
public class PasswordDao implements GenericDao<Password, Parameters, String> {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;

	@Override
	public Optional<Password> createV1(@NotNull Password password, Parameters parameters) {
		try {
			return getV1(new Parameters(insert(password, parameters)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long insert(Password password, Parameters parameters) {
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			jdbcTemplate.update(con -> {
				PreparedStatement ps = con.prepareStatement(environment.getProperty("Password.create"), new String[] { "password_id" });
				if(password.getLoginId() == null) {
					ps.setObject(1, null);
				} else { 
					ps.setLong(1, password.getLoginId());
				}
				if(password.getScereteKey() == null) {
					ps.setObject(2, null);
				} else { 
					ps.setObject(2, password.getScereteKey());
				}
				return ps;
			}, holder);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return holder.getKey().longValue();	

	}

	@Override
	public Optional<Password> getV1(Parameters parameters) throws Exception {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("Password.get"), passwordRowMapper,
				parameters.getId()));  //Password Id
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new Exception("PartyRole get method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Optional<Password> getV1(Parameters parameters, String whereClause) throws Exception {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Password> list(Parameters parameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Password> list(Parameters parameters, String whereClause) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Password> updateV1(Password transientObject, Parameters parameters) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public int delete(Password persistentObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Parameters parameters, String whereClause) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
RowMapper<Password> passwordRowMapper = (rs, rowNum) -> {
		
		Password password = new Password();
			
		password.setPasswordId(rs.getObject("password_id") != null ? rs.getLong("password_id") : null);

		password.setLoginId(rs.getObject("login_id_login") != null ? rs.getLong("login_id_login") : null);

		password.setScereteKey(rs.getObject("scerete_key") != null ? rs.getString("scerete_key") : null);
			
		return password;
	};


}
