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

import com.techgee.electronicvoting.model.Party;
import com.techgee.electronicvoting.shared.Parameters;

@Repository
public class PartyDao implements GenericDao<Party, Parameters, String> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;

	String pk = "party_id";
	String listBy = "Party.listBy";


	@Override
	public Optional<Party> createV1(@NotNull Party party, Parameters parameters) {
		try {
			return getV1(new Parameters(insert(party, parameters)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long insert(Party party, Parameters parameters) {
		 KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(environment.getProperty("Party.create"), new String[] { pk });
			if (party.getPartyCd() == null) {
				ps.setObject(1, null);
			} else {
				ps.setLong(1, party.getPartyCd());
			}			
			return ps;
		}, key);
		try {
			return Optional.ofNullable(key.getKey())
					.orElseThrow(()->new Exception("Not able to generate PK")).longValue();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Optional<Party> getV1(Parameters parameters) throws Exception {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("Party.get"), partyRowMapper,
					parameters.getId())); //Party UID
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new Exception("Patry get method return more than one result. Contact developer");
		}  catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Optional<Party> getV1(Parameters parameters, String whereClause) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Party> list(Parameters parameters) throws Exception {
		try {
			return jdbcTemplate.query(environment.getProperty("Party.list"), partyRowMapper, 
					parameters.getId());
		}  catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Party> list(Parameters parameters, String whereClause) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Party> updateV1(Party party, Parameters parameters) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}


	@Override
	public int delete(Party party) {
		return jdbcTemplate.update(environment.getProperty("Party.delete"), party.getPartyId());
	}

	@Override
	public int delete(Parameters parameters, String whereClause) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	RowMapper<Party> partyRowMapper = (rs, rowNum) -> {
		Party party = new Party();
		party.setPartyId(rs.getObject(pk) != null ? rs.getLong(pk) : null);
		party.setPartyCd(rs.getObject("party_cd_party_cd") != null ? rs.getLong("party_cd_party_cd") : null);
//		party.setPartyCdLocale(rs.getObject("party_cd_locale") != null ? rs.getString("party_cd_locale") : null);

		return party;
	};


	
}
