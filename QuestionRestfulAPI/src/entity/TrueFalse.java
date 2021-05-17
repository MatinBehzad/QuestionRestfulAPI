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
public class TrueFalse extends QuestionModel{

	private static final long serialVersionUID = 1L;
	
    public TrueFalse() {
		super();
	}


}
