package br.com.projeto.devpet.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.projeto.devpet.enums.Perfil;
import br.com.projeto.devpet.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/adminlte/**", "/img/**", "/js/**", "/plugins/**").permitAll()
            .antMatchers("/**/cadastrar").hasAuthority(Perfil.ADMIN.toString())
            .antMatchers("/**/editar").hasAuthority(Perfil.ADMIN.toString())
            .antMatchers("/**/excluir").hasAuthority(Perfil.ADMIN.toString())
            .anyRequest().authenticated()
            .and();
        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/clientes")
                .permitAll()
            .and();
        http.logout()
                .logoutRequestMatcher(
                    new AntPathRequestMatcher("/logout", "GET")
                )
                .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
    }
}
