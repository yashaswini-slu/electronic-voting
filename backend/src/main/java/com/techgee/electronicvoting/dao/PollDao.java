package com.techgee.electronicvoting.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.techgee.electronicvoting.model.Poll;
import com.techgee.electronicvoting.shared.Parameters;

@Repository
public class PollDao implements GenericDao<Poll, Parameters, String> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;
	
	public static final String BY_PPR_ID = "PprId"; 

	@Override
	public Optional<Poll> createV1(@NotNull Poll poll, Parameters parameters) {
			return getV1(new Parameters(insert(poll, parameters)));
	}

	@Override
	public Long insert(Poll poll, Parameters parameters) {
		KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(con -> {
				PreparedStatement ps = con.prepareStatement(environment.getProperty("Poll.create"), new String[] { "poll_id" });
				if(poll.getPprId() == null) {
					ps.setObject(1, null);
				} else { 
					ps.setLong(1, poll.getPprId());
				}
				if(poll.getTitle() == null) {
					ps.setObject(2, null);
				} else { 
					ps.setObject(2, poll.getTitle());
				}
				if(poll.getDescription() == null) {
					ps.setObject(3, null);
				} else { 
					ps.setObject(3, poll.getDescription());
				}
				if(poll.getStartDate() == null) {
					ps.setObject(4, null);
				} else { 
					ps.setObject(4, poll.getStartDate());
				}
				if(poll.getEndDate() == null) {
					ps.setObject(5, null);
				} else { 
					ps.setObject(5, poll.getEndDate());
				}
				return ps;
			}, holder);
		return holder.getKey().longValue();
	}

	@Override
	public Optional<Poll> getV1(Parameters parameters)  {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("Poll.get"), pollRowMapper,
				parameters.getId()));  //LoginId
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new VotingException("Poll get method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}
	}

	@Override
	public Optional<Poll> getV1(Parameters parameters, String whereClause)  {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Poll> list(Parameters parameters)  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Poll> list(Parameters parameters, String whereClause)  {
		Object parameter [] = switch(whereClause) {
		case BY_PPR_ID -> new Object [] {
				parameters.getId() //login Id
		};
		default -> throw new VotingException("The requested method is not implemented");
		};
		try {
				return jdbcTemplate.query(environment.getProperty("Poll.listBy" + whereClause), pollRowMapper, parameter);
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}
	}

	@Override
	public Optional<Poll> updateV1(Poll poll, Parameters parameters) {
		jdbcTemplate.update(environment.getProperty("Poll.update"), 
				poll.getTitle(),
				poll.getDescription(),
				poll.getStartDate(),
				poll.getEndDate(),
				parameters.getId());
		try {
			return getV1(new Parameters(parameters.getId()));
		} catch (Exception e) {
			throw new VotingException( e.getMessage());
		}
	}

	@Override
	public int delete(Poll poll) {
		return jdbcTemplate.update(environment.getProperty("Poll.delete"), poll.getPollId());
	}

	@Override
	public int delete(Parameters parameters, String whereClause)  {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public List<Poll> listIn(Parameters parameters, Set<Long> inValues) {
			
			try {
				String query = setInvalues(environment.getProperty("Poll.listIn"), "%IDS%", inValues);
				return jdbcTemplate.query(query, pollRowMapper);
			} catch (EmptyResultDataAccessException e) {
				throw new VotingException(e.getMessage());
			}
		}
	
RowMapper<Poll> pollRowMapper = (rs, rowNum) -> {
		
		Poll poll = new Poll();
			
		poll.setPollId(rs.getObject("poll_id") != null ? rs.getLong("poll_id") : null);

		poll.setPprId(rs.getObject("pr_pr_relation_id_pr_pr_relation") != null ? rs.getLong("pr_pr_relation_id_pr_pr_relation") : null);

		poll.setTitle(rs.getObject("title") != null ? rs.getString("title") : null);
		
		poll.setDescription(rs.getObject("description") != null ? rs.getString("description") : null);
			
		poll.setStartDate(rs.getObject("start_date") != null ? rs.getDate("start_date").toLocalDate() : null);

		poll.setEndDate(rs.getObject("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);

		return poll;
	};

}
