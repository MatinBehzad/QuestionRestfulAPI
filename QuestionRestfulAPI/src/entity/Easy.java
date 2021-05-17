/**
 * 
 */
package entity;

import javax.persistence.DiscriminatorValue;



/**
 * @author matineh
 *
 */
@DiscriminatorValue( "0")
public class Easy extends Dificulty {
	
	private static final long serialVersionUID = 1L;

	public Easy() {
		super();
	}

}
