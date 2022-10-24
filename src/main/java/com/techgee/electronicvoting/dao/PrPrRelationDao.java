package com.techgee.electronicvoting.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.techgee.electronicvoting.exception.VotingException;
import com.techgee.electronicvoting.model.PrPrRelation;
import com.techgee.electronicvoting.shared.Parameters;

@Repository
public class PrPrRelationDao implements GenericDao<PrPrRelation, Parameters, String>{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;
	
	public static final String BY_ROLES_AND_ROLE_CD_ENDDATE_NULL = "RolesAndRoleCdAndEndDateNull";

	@Override
	public Optional<PrPrRelation> createV1(@NotNull PrPrRelation prPrRelation, Parameters parameters) {
			return getV1(new Parameters(insert(prPrRelation, parameters)));
	}

	@Override
	public Long insert(PrPrRelation prPrRelation, Parameters parameters) {
		validateRelationshipExist(prPrRelation); // To enforce only one relationship type  b/w party role and party role related
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			jdbcTemplate.update(con -> {
				PreparedStatement ps = con.prepareStatement(environment.getProperty("PrPrRelation.create"), new String[] { "pr_pr_relation_id" });
				if (prPrRelation.getPartyRoleId1() == null) {
					ps.setObject(1, null);
				} else {
					ps.setLong(1, prPrRelation.getPartyRoleId1());
				}
				if (prPrRelation.getPartyRoleId2() == null) {
					ps.setObject(2, null);
				} else {
					ps.setLong(2, prPrRelation.getPartyRoleId2());
				}
				if (prPrRelation.getPrPrRelationCd() == null) {
					ps.setObject(3, null);
				} else {
					ps.setLong(3, prPrRelation.getPrPrRelationCd());
				}
				if (prPrRelation.getStartDate() == null) {
					ps.setObject(4, null);
				} else {
					ps.setObject(4, prPrRelation.getStartDate());
				}
				if (prPrRelation.getEndDate() == null) {
					ps.setObject(5, null);
				} else {
					ps.setObject(5, prPrRelation.getEndDate());
				}
				return ps;
			}, holder);
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("ex_party_party_relationship_end_date")) {
				throw new VotingException("Party Party Relationship is already exist. "
						+ "Please use already existing one or close the previous relationship to create new one. For more details Thirumal");
			} else if (e.getMessage().contains("ck_party_party_relationship_start_date_end_date")) {
				throw new VotingException("End date must be greater than start date");
			} else {
				throw new VotingException("Not able to insert party_party, contact admin " + e);
			}
		}
		return Optional.ofNullable(holder.getKey())
				.orElseThrow(()->new VotingException("DATABASE_EXCEPTION")).longValue();

	}

	private boolean validateRelationshipExist(PrPrRelation prPrRelation) {
		if (prPrRelation.getEndDate() != null) {
			return false;
		}
		List<PrPrRelation> prPrRelations = jdbcTemplate.query(environment.getProperty("PrPrRelation.check"), prPrRelationRowMapper, 
				prPrRelation.getPartyRoleId1(),
				prPrRelation.getPartyRoleId2(),
				prPrRelation.getPrPrRelationCd());
		if (!prPrRelations.isEmpty()) {
			throw new VotingException("Party Party Relationship is already exist. "
					+ "Please use already existing one or close the previous relationship to create new one.");
		}
		return true;
		
	}

	@Override
	public Optional<PrPrRelation> getV1(Parameters parameters)  {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PrPrRelation.get"), prPrRelationRowMapper,
					parameters.getId()));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new VotingException( "PrPrRelation get method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new VotingException("DATABASE_EXCEPTION "+ e.getMessage());
		}

	}

	
	@Override
	public Optional<PrPrRelation> getV1(Parameters parameters, String whereClause)  {
		try {
			if (whereClause.equalsIgnoreCase(BY_ROLES_AND_ROLE_CD_ENDDATE_NULL)) {
				return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PrPrRelation.getBy" + whereClause), prPrRelationRowMapper, 
						parameters.getId(), //PartyRole
						parameters.getForeignKey(), //PartyRole1
						parameters.getParentParameters().getId())); //PartyRoleCd1));//Party Role Cd 
			}
			throw new VotingException("Party Role DAO where clause method is not implemented");
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new VotingException("PartyRole get where method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new VotingException(e.getMessage());
		}	}

	@Override
	public List<PrPrRelation> list(Parameters parameters)  {
		try {
			return jdbcTemplate.query(environment.getProperty("PrPrRelation.list"), prPrRelationRowMapper, 
					parameters.getId());
		} catch (Exception e) {
			throw new VotingException("DATABASE_EXCEPTION "+ e.getMessage());
		}
	}

	@Override
	public List<PrPrRelation> list(Parameters parameters, String whereClause)  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PrPrRelation> updateV1(PrPrRelation transientObject, Parameters parameters) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public int delete(PrPrRelation prPrRelation) {
		return jdbcTemplate.update(environment.getProperty("PrPrRelation.delete"), prPrRelation.getPrPrRelationId());
		
	}

	@Override
	public int delete(Parameters parameters, String whereClause)  {
		// TODO Auto-generated method stub
		return 0;
	}
	
RowMapper<PrPrRelation> prPrRelationRowMapper = (rs, rowNum) -> {
		
	PrPrRelation prPrRelation = new PrPrRelation();
			
		prPrRelation.setPrPrRelationId(rs.getObject("pr_pr_relation_id") != null ? rs.getLong("pr_pr_relation_id") : null);
		
		prPrRelation.setPartyRoleId1(rs.getObject("party_role_id_party_role") != null ? rs.getLong("party_role_id_party_role") : null);
		
		prPrRelation.setPartyRoleId2(rs.getObject("party_role_id_party_role1") != null ? rs.getLong("party_role_id_party_role1") : null);
		
		prPrRelation.setPrPrRelationCd(rs.getObject("pr_pr_relation_cd") != null ? rs.getLong("pr_pr_relation_cd") : null);
			
		prPrRelation.setStartDate(rs.getObject("start_date") != null ? rs.getDate("start_date").toLocalDate(): null);

		prPrRelation.setEndDate(rs.getObject("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);

		return prPrRelation;
	};

}
