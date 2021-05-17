/**
 * 
 */
package entity;

import javax.persistence.DiscriminatorValue;

/**
 * @author matineh
 *
 */
@DiscriminatorValue( "1")
public class Hard extends Dificulty{
	
    public Hard() {
		super();
	}

}
