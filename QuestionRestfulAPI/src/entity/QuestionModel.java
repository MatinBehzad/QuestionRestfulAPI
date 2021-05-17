/**
 * 
 */
package entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



/**
 * @author matineh
 *
 */
@Entity
@Table( name = "question")
@NamedQuery( name = QuestionModel.ALL_Questions_QUERY_NAME, query = "SELECT p FROM Question p")
@NamedQuery( name = QuestionModel.SPECIFIC_Question_QUERY_NAME, query = "SELECT b FROM Question b WHERE b.id=:param1")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE)
//columnDefinition, discriminatorType
@DiscriminatorColumn( columnDefinition = "boolean", name = "type", discriminatorType = DiscriminatorType.INTEGER)
public class QuestionModel implements Serializable{

	
	
		private static final long serialVersionUID = 1L;
		
		public static final String ALL_Questions_QUERY_NAME = "Question.findAll";
		public static final String SPECIFIC_Question_QUERY_NAME = "Question.findByAll";
        
		@Basic( optional = false)
		@Id
		@GeneratedValue( strategy = GenerationType.IDENTITY)
		@Column( nullable = false, name = "id")
		protected int id;
		
		@Basic( optional = false)
		@Column( name = "category", nullable = false, length = 50)
		private String category;

		@Embedded
		private Dificulty dificulty;
		
		@Basic( optional = false)
		@Column( name = "correctAnswer", nullable = false)
		private String correctAnswer;
		
		@Basic( optional = false)
		@Column( name = "incorrectAnswer", nullable = false)
		private Set<String> incorrectAnswer;
		
		@ManyToOne( optional = false, cascade = { CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
		@JoinColumn( name = "id", referencedColumnName = "id")
		private Person person; 
		
		
		public void setCategory(String category) {
			this.category=category;
		}
		public String getCategory() {
			return category;
		}
		
		public void setCorrectAnswer(String correctAnswer) {
			this.correctAnswer=correctAnswer;
		}
		public String getCorrectAnswer() {
			return correctAnswer;
		}
		
		public Set<String> getincorrectAnswer() {
			
			return incorrectAnswer;
		}
		
        public Set<String> setincorrectAnswer(Set<String> incorrectAnswer) {
			
			this.incorrectAnswer=incorrectAnswer;
		}
        
        public void setId(int id) {
        	
        	this.id=id;
        }
        public int getId() {
        	
        	return id;
        }
		
		
		
	
}


