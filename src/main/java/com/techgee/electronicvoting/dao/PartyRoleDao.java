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

import com.techgee.electronicvoting.model.PartyRole;
import com.techgee.electronicvoting.shared.Parameters;

@Repository
public class PartyRoleDao implements GenericDao<PartyRole, Parameters, String>{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment environment;
	
	String pk = "party_role_id";
	
	public static final String BY_PARTYID_AND_PARTYROLECD = "PartyIdAndPartyRoleCd";
	public static final String BY_PARTYROLECD = "PartyRoleCd";
	public static final String BY_PARTYROLEID_AND_PARTYROLECD = "PartyRoleIdAndPartyRoleCd";
	public static final String BY_PARTYROLEID_AND_PARTYROLECD_END_DATE = "PartyRoleIdAndPartyRoleCd_EndDate";
	public static final String BY_PARTYID = "PartyId";
	
	@Override
	public Optional<PartyRole> createV1(@NotNull PartyRole partyrole, Parameters parameters) {	
		try {
			return getV1(new Parameters(insert(partyrole, parameters)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long insert(PartyRole partyrole, Parameters parameters) {
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			jdbcTemplate.update(con -> {
				PreparedStatement ps = con.prepareStatement(environment.getProperty("PartyRole.create"), new String[] { pk });
				if(partyrole.getPartyId() == null) {
					ps.setObject(1, null);
				} else { 
					ps.setLong(1, partyrole.getPartyId());
				}
				if(partyrole.getPartyRoleCd() == null) {
					ps.setObject(2, null);
				} else { 
					ps.setLong(2, partyrole.getPartyRoleCd());
				}
				if(partyrole.getStartDate() == null) {
					ps.setObject(3, null);
				} else { 
					ps.setObject(3, partyrole.getStartDate());
				}
				if(partyrole.getEndDate() == null) {
					ps.setObject(4, null);
				} else { 
					ps.setObject(4, partyrole.getEndDate());
				}
				return ps;
			}, holder);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return holder.getKey().longValue();
	}
	@Override
	public Optional<PartyRole> getV1(Parameters parameters) throws Exception {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyRole.get"), partyRoleRowMapper,
				parameters.getId()));  //Party RoleUID
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new Exception("PartyRole get method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Optional<PartyRole> getV1(Parameters parameters, String whereClause) throws Exception {
		try {
			if (whereClause.equalsIgnoreCase(BY_PARTYID_AND_PARTYROLECD)) {
				return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyRole.getBy" + whereClause), partyRoleRowMapper, 
					parameters.getId(), //Party_UID
					parameters.getParentParameters().getId()));//Party Role Cd 
			}
			if (whereClause.equalsIgnoreCase(BY_PARTYROLEID_AND_PARTYROLECD)) {
				return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyRole.getBy" + whereClause), partyRoleRowMapper,
						parameters.getId(), //PartyRoleUID
						parameters.getParentParameters().getId())); //Party Role Cd 
			}
			if (whereClause.equalsIgnoreCase(BY_PARTYROLEID_AND_PARTYROLECD_END_DATE)) {
				return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyRole.getBy" + whereClause), partyRoleRowMapper, 
						parameters.getId(), //PartyRoleUID
						parameters.getParentParameters().getId())); //Party Role Cd 
			}
			if (whereClause.equalsIgnoreCase(BY_PARTYROLECD)) { // Applicable to only ROLE_SERVICE PROVIDER & IBBI 
				return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyRole.getBy" + whereClause), partyRoleRowMapper, 
						parameters.getId())); //Party Role Cd 
			}
			if(whereClause.equalsIgnoreCase(BY_PARTYID)) {
				return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyRole.getBy" + whereClause), partyRoleRowMapper, 
						parameters.getId())); //Party Uid
			}
			throw new Exception("Party Role DAO where clause method is not implemented");
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new Exception("PartyRole get where method return more than one result. Contact developer");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<PartyRole> list(Parameters parameters) throws Exception {
		try {
			return jdbcTemplate.query(environment.getProperty("PartyRole.list"), partyRoleRowMapper, 
				parameters.getId());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<PartyRole> list(Parameters parameters, String whereClause) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PartyRole> updateV1(PartyRole transientObject, Parameters parameters) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public int delete(PartyRole partyrole) {
		return jdbcTemplate.update(environment.getProperty("PartyRole.delete"), partyrole.getPartyRoleId());
	}

	@Override
	public int delete(Parameters parameters, String whereClause) throws Exception {
		if(whereClause.equalsIgnoreCase(BY_PARTYID)) {
			return jdbcTemplate.update(environment.getProperty("PartyRole.deleteBy" + whereClause), parameters.getId());
		}
		throw new Exception("The requested delete method is not implemented");
	
	}
	
	RowMapper<PartyRole> partyRoleRowMapper = (rs, rowNum) -> {
		
		PartyRole partyrole = new PartyRole();
			
		partyrole.setPartyRoleId(rs.getObject(pk) != null ? rs.getLong(pk) : null);

		partyrole.setPartyId(rs.getObject("party_id_party") != null ? rs.getLong("party_id_party") : null);

		partyrole.setPartyRoleCd(rs.getObject("party_role_cd_party_role_cd") != null ? rs.getLong("party_role_cd_party_role_cd") : null);
			
		partyrole.setStartDate(rs.getObject("start_date") != null ? rs.getDate("start_date").toLocalDate() : null);

		partyrole.setEndDate(rs.getObject("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);

		return partyrole;
	};


}
