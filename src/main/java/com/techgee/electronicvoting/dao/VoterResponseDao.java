package com.techgee.electronicvoting.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

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
import com.techgee.electronicvoting.model.VoterResponse;
import com.techgee.electronicvoting.shared.Parameters;

@Repository
public class VoterResponseDao implements GenericDao<VoterResponse, Parameters, String> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;
	

	@Override
	public Optional<VoterResponse> createV1(@NotNull VoterResponse voterResponse, Parameters parameters) {
		return getV1(new Parameters(insert(voterResponse, parameters)));
		
	}

	@Override
	public Long insert(VoterResponse voterResponse, Parameters parameters) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> setPreparedStatement(voterResponse, con.prepareStatement(environment.getProperty("VoterResponse.create"),
				new String[] { "voter_response_uid"})), holder);
		return holder.getKey().longValue();
	}
	
	public void insertBatch(List<VoterResponse> VoterResponses) {
		jdbcTemplate.batchUpdate(environment.getProperty("VoterResponse.create"), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				VoterResponse VoterResponse = VoterResponses.get(i);
				setPreparedStatement(VoterResponse, ps);
			}
			
			@Override
			public int getBatchSize() {
				return VoterResponses.size();
			}
		});
	}

	private PreparedStatement setPreparedStatement(VoterResponse voterResponse, PreparedStatement ps) throws SQLException {
		if(voterResponse.getPrPrRelationId() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setLong(1, voterResponse.getPrPrRelationId());
		}
		if(voterResponse.getAllowedResponseOptionId() == null ) {
			ps.setObject(2,  null);
		} else {
			ps.setLong(2,  voterResponse.getAllowedResponseOptionId());
		}
		if(voterResponse.getCastTime() == null ) {
			ps.setObject(3, null);
		} else {
			ps.setObject(3, voterResponse.getCastTime());
		}
		return ps;
	}

	@Override
	public Optional<VoterResponse> getV1(Parameters parameters) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("VoterResponse.get"), voterResponseRowMapper,
				parameters.getId()));  //pollQuestionId
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new VotingException("VoterResponse get method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}
	}

	@Override
	public Optional<VoterResponse> getV1(Parameters parameters, String whereClause) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<VoterResponse> list(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VoterResponse> list(Parameters parameters, String whereClause) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<VoterResponse> updateV1(VoterResponse transientObject, Parameters parameters) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public int delete(VoterResponse persistentObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Parameters parameters, String whereClause) {
		// TODO Auto-generated method stub
		return 0;
	}
	
RowMapper<VoterResponse> voterResponseRowMapper = (rs, rowNum) -> {
		
	VoterResponse voterResponse = new VoterResponse();
	
			
		voterResponse.setVoterResponseId(rs.getObject("voter_response_uid") != null ? rs.getLong("voter_response_uid") : null);
	
		voterResponse.setPrPrRelationId(rs.getObject("pr_pr_relation_id") != null ? rs.getLong("pr_pr_relation_id") : null);
		
		voterResponse.setAllowedResponseOptionId(rs.getObject("allowed_response_option_id") != null ? rs.getLong("allowed_response_option_id") : null);
	   	
		voterResponse.setCastTime(rs.getObject("cast_time") != null ? toDateTime(rs.getObject("cast_time")): null);

		return voterResponse;
	};
	
	public static LocalDateTime toDateTime(Object dateTimeObject) {
	    if (dateTimeObject instanceof LocalDateTime) {
	        return (LocalDateTime) dateTimeObject;
	    }
	    return null;
	}

}
