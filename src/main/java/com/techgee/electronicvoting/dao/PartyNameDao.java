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
import com.techgee.electronicvoting.model.PartyName;
import com.techgee.electronicvoting.shared.Parameters;

@Repository
public class PartyNameDao implements GenericDao<PartyName, Parameters, String> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;

	private static final String PARTY_NAME_ID = "party_name_id";
	public static final String BY_PARTYID = "PartyId";
	
	@Override
	public Optional<PartyName> createV1(@NotNull PartyName partyname, Parameters parameters) {
			return getV1(new Parameters(insert(partyname, parameters)));
	}

	@Override
	public Long insert(PartyName partyname, Parameters parameters) {
		KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(con -> {
				PreparedStatement ps = con.prepareStatement(environment.getProperty("PartyName.create"), new String[] { PARTY_NAME_ID });
				if(partyname.getPartyId() == null) {
					ps.setObject(1, null);
				} else { 
					ps.setLong(1, partyname.getPartyId());
				}
				if(partyname.getFirstName() == null) {
					ps.setObject(2, null);
				} else { 
					ps.setString(2, partyname.getFirstName().strip());
				}
				if(partyname.getLastName() == null) {
					ps.setObject(3, null);
				} else { 
					ps.setString(3, partyname.getLastName().strip());
				}
				if(partyname.getMiddleName() == null) {
					ps.setObject(4, null);
				} else { 
					ps.setString(4, partyname.getMiddleName().strip());
				}
				if(partyname.getRestOfName() == null) {
					ps.setObject(5, null);
				} else { 
					ps.setString(5, partyname.getRestOfName().strip());
				}
				if(partyname.getIsPreferred() == null) {
					ps.setObject(6, null);
				} else { 
					ps.setBoolean(6, partyname.getIsPreferred());
				}
				if(partyname.getStartDate() == null) {
					ps.setObject(7, null);
				} else { 
					ps.setObject(7, partyname.getStartDate());
				}
				return ps;
			}, holder);
			return holder.getKey().longValue();
	}

	@Override
	public Optional<PartyName> getV1(Parameters parameters) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyName.get"), partynameRowMapper,
				parameters.getId()));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new VotingException("Patry Name get method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}
	}

	@Override
	public Optional<PartyName> getV1(Parameters parameters, String whereClause) {
		Object parameter [] = switch(whereClause) {
		case BY_PARTYID -> new Object [] {
				parameters.getId() //Party ID
		};
		default -> throw new VotingException("The requested method is not implemented");
		};
		try {
				return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyName.getBy" + whereClause), partynameRowMapper, parameter));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new VotingException("Patry Name get "+ parameters.getId()  +" where method return more than one result."
					+ " Contact developer");
		} catch (Exception e) {
			throw new VotingException( e.getMessage());
		}
	}

	@Override
	public List<PartyName> list(Parameters parameters) {
		try {
			return jdbcTemplate.query(environment.getProperty("PartyName.list"), partynameRowMapper, 
					parameters.getId());
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}
	}

	@Override
	public List<PartyName> list(Parameters parameters, String whereClause) {
		try {
			if (whereClause.equalsIgnoreCase(BY_PARTYID)) {
				return jdbcTemplate.query(environment.getProperty("PartyName.listBy" + whereClause), partynameRowMapper, 
						parameters.getId());
			}
			throw new VotingException( "whereClause method not implemented for partyName");
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}
	}

	@Override
	public Optional<PartyName> updateV1(PartyName partyname, Parameters parameters) {
		jdbcTemplate.update(environment.getProperty("PartyName.update"), 
				partyname.getFirstName() == null ? null : partyname.getFirstName().strip(),
				partyname.getLastName() == null ? null : partyname.getLastName().strip(),
				partyname.getMiddleName() == null ? null : partyname.getMiddleName().strip(),				
				partyname.getRestOfName() == null ? null : partyname.getRestOfName().strip(),
				partyname.getIsPreferred(),
				partyname.getStartDate(),
				partyname.getEndDate());
		try {
			return getV1(new Parameters(partyname.getPartyNameId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public int delete(PartyName partyname) {
		return jdbcTemplate.update(environment.getProperty("PartyName.delete"), partyname.getPartyNameId());
	}

	@Override
	public int delete(Parameters parameters, String whereClause) {
		try {
			if(whereClause.equalsIgnoreCase(BY_PARTYID)) {
				return jdbcTemplate.update(environment.getProperty("PartyName.deleteBy" + whereClause), parameters.getId());
			}
		} catch(Exception e) { 
			throw new VotingException("The requested delete method is not implemented");
		}
		return 0;
	}
	
	RowMapper<PartyName> partynameRowMapper = (rs, rowNum) -> {

		PartyName partyname = new PartyName();

		partyname.setPartyId(rs.getObject("party_id_party") != null ? rs.getLong("party_id_party") : null);

		partyname.setPartyNameId(rs.getObject(PARTY_NAME_ID) != null ? rs.getLong(PARTY_NAME_ID) : null);

		partyname.setFirstName(rs.getObject("first_name") != null ? rs.getString("first_name") : null);

		partyname.setLastName(rs.getObject("last_name") != null ? rs.getString("last_name") : null);
		
		partyname.setMiddleName(rs.getObject("middle_name") != null ? rs.getString("middle_name") : null);

		partyname.setRestOfName(rs.getObject("rest_of_name") != null ? rs.getString("rest_of_name") : null);

		partyname.setIsPreferred(rs.getObject("preferred") != null ? rs.getBoolean("is_preferred") : null);

		partyname.setStartDate(rs.getObject("start_date") != null ? (rs.getDate("start_date")).toLocalDate() : null);

		partyname.setEndDate(rs.getObject("end_date") != null ? (rs.getDate("end_date")).toLocalDate() : null);

		return partyname;
	};

	

}
