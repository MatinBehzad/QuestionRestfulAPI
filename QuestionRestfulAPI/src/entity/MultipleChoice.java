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
public class MultipleChoice extends QuestionModel {

	private static final long serialVersionUID = 1L;
	
	public MultipleChoice() {
		super();
	}

}
