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

import com.techgee.electronicvoting.exception.VotingException;
import com.techgee.electronicvoting.model.PollQuestion;
import com.techgee.electronicvoting.shared.Parameters;

@Repository
public class PollQuestionDao implements GenericDao<PollQuestion, Parameters, String>{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;
	
	public static final String BY_POLL_ID = "PollId";

	@Override
	public Optional<PollQuestion> createV1(@NotNull PollQuestion pollQuestion, Parameters parameters) {
		return getV1(new Parameters(insert(pollQuestion, parameters)));
	}

	@Override
	public Long insert(PollQuestion pollQuestion, Parameters parameters) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(environment.getProperty("PollQuestion.create"), new String[] { "poll_question_id" });
			if(pollQuestion.getPollId() == null) {
				ps.setObject(1, null);
			} else { 
				ps.setLong(1, pollQuestion.getPollId());
			}
			if(pollQuestion.getPollQuestion() == null) {
				ps.setObject(2, null);
			} else { 
				ps.setObject(2, pollQuestion.getPollQuestion());
			}
			if(pollQuestion.getPollQuestionUuid() == null) {
				ps.setObject(3, null);
			} else { 
				ps.setObject(3, pollQuestion.getPollQuestionUuid());
			}
			return ps;
		}, holder);
	return holder.getKey().longValue();
	}

	@Override
	public Optional<PollQuestion> getV1(Parameters parameters) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PollQuestion.get"), pollQuestionRowMapper,
				parameters.getId()));  //pollQuestionId
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new VotingException("PollQuestion get method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}
	}

	@Override
	public Optional<PollQuestion> getV1(Parameters parameters, String whereClause) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<PollQuestion> list(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PollQuestion> list(Parameters parameters, String whereClause) {
		Object parameter [] = switch(whereClause) {
		case BY_POLL_ID -> new Object [] {
				parameters.getId() //login Id
		};
		default -> throw new VotingException("The requested method is not implemented");
		};
		try {
				return jdbcTemplate.query(environment.getProperty("PollQuestion.listBy" + whereClause), pollQuestionRowMapper, parameter);
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}
	}

	@Override
	public Optional<PollQuestion> updateV1(PollQuestion transientObject, Parameters parameters) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public int delete(PollQuestion persistentObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Parameters parameters, String whereClause) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	RowMapper<PollQuestion>  pollQuestionRowMapper = (rs, rowNum) -> {
		
		PollQuestion pollQuestion = new PollQuestion();
		
		pollQuestion.setPollQuestionId(rs.getObject("poll_question_id") != null ? rs.getLong("poll_question_id") : null);
		
		pollQuestion.setPollId(rs.getObject("poll_id_poll") != null ? rs.getLong("poll_id_poll") : null);
		
		pollQuestion.setPollQuestion(rs.getObject("poll_question") != null ? rs.getString("poll_question") : null);
		
		pollQuestion.setPollQuestionUuid(rs.getObject("poll_question_uuid") != null ? (java.util.UUID) rs.getObject("poll_question_uuid") : null);
		
		return pollQuestion;
	};



}
