/**
 * 
 */
package entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author matineh
 *
 */
@Inheritance( strategy = InheritanceType.SINGLE_TABLE)
//columnDefinition, discriminatorType
@DiscriminatorColumn( columnDefinition = "String", name = "dificulty", discriminatorType = DiscriminatorType.INTEGER)
public class Dificulty {
	
	private static final long serialVersionUID = 1L;

	public Dificulty() {
		
	}

}
