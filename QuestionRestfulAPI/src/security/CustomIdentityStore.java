
package security;

import static java.util.Collections.emptySet;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import org.glassfish.soteria.WrappingCallerPrincipal;

import entity.SecurityRole;
import entity.SecurityUser;



/**
 * @author matineh
 *
 */
public class CustomIdentityStore implements IdentityStore {
	
	@Inject
    protected CustomIdentitiStoreJPAHelper jpaHelper;

    @Inject
    protected Pbkdf2PasswordHash pb;

	
	 @Override
	    public CredentialValidationResult validate(Credential credential) {

	        CredentialValidationResult result = INVALID_RESULT;

	        if (credential instanceof UsernamePasswordCredential) {
	            String callerName = ((UsernamePasswordCredential)credential).getCaller();
	            String credentialPassword = ((UsernamePasswordCredential)credential).getPasswordAsString();
	            SecurityUser user = jpaHelper.findUserByName(callerName);
	            if (user != null) {
	                String pwHash = user.getPwHash();
	                try {
	                    boolean verified = pb.verify(credentialPassword.toCharArray(), pwHash);
	                    if (verified) {
	                        Set<String> rolesForUser = jpaHelper.findRoleNamesForUser(callerName);
	                        result = new CredentialValidationResult(new WrappingCallerPrincipal(user), rolesForUser);
	                    }
	                }
	                catch (Exception e) {
	                    // e.printStackTrace();
	                }
	            }
	        }
	        // check if the credential was CallerOnlyCredential
	        else if (credential instanceof CallerOnlyCredential) {
	            String callerName = ((CallerOnlyCredential)credential).getCaller();
	            SecurityUser user = jpaHelper.findUserByName(callerName);
	            if (user != null) {
	                result = new CredentialValidationResult(callerName);
	            }
	        }

	        return result;
	    }

	    protected Set<String> getRolesNamesForSecurityRoles(Set<SecurityRole> roles) {
	        Set<String> roleNames = emptySet();
	        if (!roles.isEmpty()) {
	            roleNames = roles
	                .stream()
	                .map(s -> s.getRoleName())
	                .collect(Collectors.toSet());
	        }
	        return roleNames;
	    }
	

}
