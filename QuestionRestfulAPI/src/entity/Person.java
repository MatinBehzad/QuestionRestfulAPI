/**
 * 
 */
package entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author matineh
 *
 */

	
	@Entity
	@Table( name = "person")
	@NamedQuery( name = Person.ALL_PERSONS_QUERY_NAME, query = "SELECT p FROM Person p")
	@NamedQuery( name = Person.SPECIFIC_PERSON_QUERY_NAME, query = "SELECT b FROM Person b WHERE b.id=:param1")
	public class Person implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String ALL_PERSONS_QUERY_NAME = "Person.findAll";
		public static final String SPECIFIC_PERSON_QUERY_NAME = "Person.findByAll";
        
		@Basic( optional = false)
		@Id
		@GeneratedValue( strategy = GenerationType.IDENTITY)
		@Column( nullable = false, name = "id")
		protected int id;
		
		@Basic( optional = false)
		@Column( name = "first_name", nullable = false, length = 50)
		private String firstName;

		@Basic( optional = false)
		@Column( name = "last_name", nullable = false, length = 50)
		private String lastName;


		public String getFirstName() {
			return firstName;
		}

		public void setFirstName( String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName( String lastName) {
			this.lastName = lastName;
		}

		
		public int getId() {
			return id;
		}
		
		public int setId() {
			return id;
		}
        


	}

