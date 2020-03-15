package tnc.at.brpl.configurations;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String user = "system";
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getUserAuthentication().getPrincipal();
            user = ((CustomUserDetails)principal).getUsername();
        }

        return user;
    }
}
