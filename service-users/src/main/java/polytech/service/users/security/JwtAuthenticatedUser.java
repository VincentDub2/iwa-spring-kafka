package polytech.service.users.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import polytech.service.users.model.User;

import java.util.Collection;

public class JwtAuthenticatedUser implements Authentication {

    private final User user;
    private boolean authenticated = true;

    public JwtAuthenticatedUser(User user) {
        this.user = user;
    }

    // Implémentez les méthodes de l'interface Authentication
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Pas de rôles spécifiques
    }

    @Override
    public Object getCredentials() {
        return null; // Pas nécessaire
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }
}
