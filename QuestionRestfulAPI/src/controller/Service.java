/**
 * @author matineh
 *
 */
package controller;



import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import entity.Person;
import entity.QuestionModel;
import entity.SecurityRole;
import entity.SecurityUser;

import static entity.SecurityUser.USER_FOR_OWNING_PERSON_QUERY;




/**
 * Stateless Singleton ejb Bean - Service
 */
@Singleton
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    //@PersistenceContext(name = //databasename)
    protected EntityManager em;
    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;
    
  
    public List<Person> getAllPeople() {
    	TypedQuery< Person> allPeopleQuery = em.createNamedQuery( Person.ALL_PERSONS_QUERY_NAME, Person.class);
		//execute the query and return the result/s.
		return allPeopleQuery.getResultList();
    	
    }

    public Person getPersonId(int id) {
    	TypedQuery< Person> allpersonIdQuery = em.createNamedQuery( Person.SPECIFIC_PERSON_QUERY_NAME, Person.class);
    	allpersonIdQuery.setParameter("param1", id);
		return allpersonIdQuery.getSingleResult();
    }

    @Transactional
    public Person persistPerson(Person newPerson) {
		em.merge(newPerson);
		return newPerson;
    }

    @Transactional
    public void buildUserForNewPerson(Person newPerson) {
        SecurityUser userForNewPerson = new SecurityUser();
        userForNewPerson.setUsername(
        		"user" + "_" + newPerson.getFirstName() + "." + newPerson.getLastName());
        //all users and their password have to add to this map they should retrieve from database 
        Map<String, String> pbAndjProperties = new HashMap<>();
//        pbAndjProperties.put();
//        pbAndjProperties.put();
//        pbAndjProperties.put();
//        pbAndjProperties.put();
        pbAndjPasswordHash.initialize(pbAndjProperties);
        String pwHash = pbAndjPasswordHash.generate("pasword".toCharArray());
        userForNewPerson.setPwHash(pwHash);
        userForNewPerson.setPerson(newPerson);
        SecurityRole userRole = em.createNamedQuery(ROLE_BY_NAME_QUERY, SecurityRole.class)
            .setParameter("param1", "USER_ROLE").getSingleResult();
        userForNewPerson.getRoles().add(userRole);
        userRole.getUsers().add(userForNewPerson);
        em.persist(userForNewPerson);
    }
   

    @Transactional
    public Person updatePersonById(int id, Person personWithUpdates) {
        Person personToBeUpdated = getPersonId(id);
        if (personToBeUpdated != null) {
            em.refresh(personToBeUpdated);
            em.merge(personWithUpdates);
            em.flush();
        }
        return personToBeUpdated;
    }

    @Transactional
    public void deletePersonById(int id) {
        Person person = getPersonId(id);
        if (person != null) {
            em.refresh(person);
            TypedQuery<SecurityUser> findUser = em
                .createNamedQuery(USER_FOR_OWNING_PERSON_QUERY, SecurityUser.class)
                .setParameter("param1", person.getId());
            SecurityUser sUser = findUser.getSingleResult();
            em.remove(sUser);
            em.remove(person);
        }
    }
    
    public List<QuestionModel> getAllQuestion() {
    	TypedQuery< QuestionModel> allQuestionQuery = em.createNamedQuery(QuestionModel.ALL_Questions_QUERY_NAME, QuestionModel.class);
		return allQuestionQuery.getResultList();
    	
    }
    @Transactional
    public QuestionModel persistQuestionModel(QuestionModel qe) {
		em.persist(qe);
		return qe;
    }
    
    public QuestionModel getQuestionModelById(int id) {
    	
    	TypedQuery< QuestionModel> questionByIdQuery = em.createNamedQuery(QuestionModel.SPECIFIC_Question_QUERY_NAME, QuestionModel.class);
    	questionByIdQuery.setParameter("param1", id);
		return questionByIdQuery.getSingleResult();
    }
    
    @Transactional
    public QuestionModel deleteQuestionModelById(int id) {
    	
    	QuestionModel findqr = getQuestionModelById(id); 
    	
    		if(findqr != null) {
    			 em.remove(findqr);
    		}
    return findqr;
    	
    	
    }
    
}