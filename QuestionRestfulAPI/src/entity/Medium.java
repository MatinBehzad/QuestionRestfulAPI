/**
 * 
 */
package entity;

import javax.persistence.DiscriminatorValue;

/**
 * @author matineh
 *
 */
@DiscriminatorValue( "2")
public class Medium extends Dificulty {
	
private static final long serialVersionUID = 1L;
	
	public Medium() {
		super();
	}

}
