
package security;


import static java.util.Collections.emptySet;
import static entity.SecurityUser.SECURITY_USER_BY_NAME_QUERY;

import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import entity.SecurityRole;
import entity.SecurityUser;

/**
 * @author matineh
 *
 */
@Singleton
public class CustomIdentitiStoreJPAHelper {
	

	//@PersistenceContext( name = //) name should be the the name which is assigned to persistence-unit
	//that is related to the database 
	protected EntityManager em;

	public SecurityUser findUserByName( String username) {
		
		SecurityUser user = null;
		TypedQuery< SecurityUser> q = em.createNamedQuery( SECURITY_USER_BY_NAME_QUERY, SecurityUser.class);
		q.setParameter("param1", username);
		try {
			user = q.getSingleResult();
		} catch ( NoResultException e) {
			
			user = null;
		}
		return user;
	}

	public Set< String> findRoleNamesForUser( String username) {
		Set< String> roleNames = emptySet();
		SecurityUser securityUser = findUserByName( username);
		if ( securityUser != null) {
			roleNames = securityUser.getRoles().stream().map( s -> s.getRoleName()).collect( Collectors.toSet());
		}
		return roleNames;
	}

	@Transactional
	public void saveSecurityUser( SecurityUser user) {
		//adding new user
		em.persist( user);
	}

	@Transactional
	public void saveSecurityRole( SecurityRole role) {
		
		em.persist( role);
	}

}
