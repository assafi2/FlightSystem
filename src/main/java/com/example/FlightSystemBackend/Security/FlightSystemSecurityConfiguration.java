package com.example.FlightSystemBackend.Security;

import com.example.FlightSystemBackend.DAO.UserDAO;
import com.example.FlightSystemBackend.DAO.UserDAOJDBCImpl;
import com.example.FlightSystemBackend.PersistantDomainObjects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity ;

import javax.sql.DataSource;


@EnableWebSecurity
public class FlightSystemSecurityConfiguration extends WebSecurityConfigurerAdapter {


    UserDAO udao = new UserDAOJDBCImpl();

    // NOT WORKED WELL THEREFORE IMPLEMENTED WITH USER POCO DAO FUNCTIONALITIES

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery
                        ("select users.username, users.password, true  from users where username = ? ;") ;
                //.authoritiesByUsernameQuery("SELECT  \"username\", \"role\" from \"users\" where \"username\"=?");
//        "SELECT \"username\", \"password\",\"enabled\" from \"users\" where \"username\"=?"
    }

*/

    // inmemory authentication with jdbc data access

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        var imudmc = auth.inMemoryAuthentication();
        for (User user : udao.getAll()) {
            imudmc.withUser(user.username)
                    .password(user.password)
                    .roles(udao.retRoleName(user.user_role));
        }
    }

    @Bean
    public PasswordEncoder getpassEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/admin/**").hasRole("administrator")
                .antMatchers("/customer/**").hasAnyRole("administrator","customer")
                .antMatchers("/airline/**").hasAnyRole("administrator","airline_company")
                .antMatchers("/").permitAll() //allow any user to connect to the page
                .and().formLogin();

    }

}
