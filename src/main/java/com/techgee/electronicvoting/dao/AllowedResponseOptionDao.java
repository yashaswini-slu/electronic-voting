package com.techgee.electronicvoting.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.techgee.electronicvoting.exception.VotingException;
import com.techgee.electronicvoting.model.AllowedResponseOption;
import com.techgee.electronicvoting.shared.Parameters;

@Repository
public class AllowedResponseOptionDao implements GenericDao<AllowedResponseOption, Parameters, String> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;
	
	public static final String BY_POLL_QUESTION_ID = "PollQuestionId";
	

	@Override
	public Optional<AllowedResponseOption> createV1(AllowedResponseOption allowedResponseOption,
			Parameters parameters) {
		return getV1(new Parameters(insert(allowedResponseOption, parameters)));
	}

	@Override
	public Long insert(AllowedResponseOption allowedResponseOption, Parameters parameters) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> setPreparedStatement(allowedResponseOption, con.prepareStatement(environment.getProperty("AllowedResponseOption.create"),
				new String[] { "allowed_response_option_id"})), holder);
		return holder.getKey().longValue();
	}
	
	public void insertBatch(List<AllowedResponseOption> allowedResponseOptions) {
		jdbcTemplate.batchUpdate(environment.getProperty("AllowedResponseOption.create"), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				AllowedResponseOption allowedResponseOption = allowedResponseOptions.get(i);
				setPreparedStatement(allowedResponseOption, ps);
			}
			
			@Override
			public int getBatchSize() {
				return allowedResponseOptions.size();
			}
		});
	}

	protected PreparedStatement setPreparedStatement(AllowedResponseOption allowedResponseOption, PreparedStatement ps) throws SQLException {
		if(allowedResponseOption.getPollQuestionId() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setLong(1, allowedResponseOption.getPollQuestionId());
		}
		if(allowedResponseOption.getOption() == null) {
			ps.setObject(2, null);
		} else { 
			ps.setObject(2, allowedResponseOption.getOption());
		}
		if(allowedResponseOption.isCorrect() == null) {
			ps.setObject(3, null);
		} else { 
			ps.setObject(3, allowedResponseOption.isCorrect());
		}
		return ps;
		
	}


	@Override
	public Optional<AllowedResponseOption> getV1(Parameters parameters) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("AllowedResponseOption.get"), allowedResponseOptionRowMapper,
				parameters.getId()));  //option id
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new VotingException("PollQuesAllowedResponseOptiontion get method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}
	}

	@Override
	public Optional<AllowedResponseOption> getV1(Parameters parameters, String whereClause) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<AllowedResponseOption> list(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AllowedResponseOption> list(Parameters parameters, String whereClause) {
		Object parameter [] = switch(whereClause) {
		case BY_POLL_QUESTION_ID -> new Object [] {
				parameters.getId() //question Id
		};
		default -> throw new VotingException("The requested method is not implemented");
		};
		try {
				return jdbcTemplate.query(environment.getProperty("AllowedResponseOption.listBy" + whereClause), allowedResponseOptionRowMapper, parameter);
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}
	}

	@Override
	public Optional<AllowedResponseOption> updateV1(AllowedResponseOption transientObject, Parameters parameters) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public int delete(AllowedResponseOption persistentObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Parameters parameters, String whereClause) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	RowMapper<AllowedResponseOption> allowedResponseOptionRowMapper = (rs, rowNum) -> {
		AllowedResponseOption allowedResponseOption = new AllowedResponseOption();
		
		allowedResponseOption.setAllowedResponseOptionId(rs.getObject("allowed_response_option_id") != null ? rs.getLong("allowed_response_option_id") : null);
		
		allowedResponseOption.setPollQuestionId(rs.getObject("poll_question_id") != null ? rs.getLong("poll_question_id") : null);
		
		allowedResponseOption.setOption(rs.getObject("option") != null ? rs.getString("option") : null);
		
		allowedResponseOption.setCorrect(rs.getObject("is_correct") != null ? rs.getBoolean("is_correct") : null);
		
		return allowedResponseOption;
		
	};
	

}
