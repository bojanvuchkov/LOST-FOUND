package mk.ukim.finki.wp.lostfound.config;

import mk.ukim.finki.wp.lostfound.model.enums.AppRole;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import javax.security.auth.login.LoginException;

public class AuthConfig {

    public HttpSecurity authorize(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**").disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers("/admin/subject-requests/my",
                                "/engagement/my",
                                "/engagement/add",
                                "/engagement/edit/**",
                                "/engagement/save/**",
                                "/engagement/delete/**",
                                "/admin/joined-subjects",
                                "/admin/joined-subjects/add",
                                "/admin/joined-subjects/edit/**",
                                "/admin/joined-subjects/save",
                                "/admin/course-preferences/add",
                                "/admin/course-preferences/edit/**",
                                "/admin/course-preferences/save",
                                "/admin/course-preferences",
                                "/items"
//                                "/items/",
//                                "/items/add",
//                                "/items/edit/",
//                                "items/delete/",
//                                "items/search"
                                ).hasAnyRole(
                                AppRole.PROFESSOR.name(),
                                AppRole.ADMIN.name()
                        )
                        .requestMatchers("/admin/subject-requests/{professorId}/**").access(
                                new WebExpressionAuthorizationManager("#professorId == authentication.name or hasRole('ROLE_PROFESSOR')")
                        )
                        .requestMatchers("/engagement/{professorId}/**").access(
                                new WebExpressionAuthorizationManager("#professorId == authentication.name or hasRole('ROLE_PROFESSOR')")
                        )
                        .requestMatchers("/admin/**", "/api/**", "/build/**", "/engagement/init").hasAnyRole(
                                AppRole.ADMIN.name()
                        )
                        .requestMatchers("/active-subjects", "io.png", "/allocation/*").permitAll()
                                .anyRequest().permitAll()
                )
//                .logout(LogoutConfigurer::permitAll);
        .logout((logout) -> logout.logoutSuccessUrl("/items"));
    }

}
