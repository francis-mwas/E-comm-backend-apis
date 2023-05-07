package com.fram.ecommercebackend.api.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fram.ecommercebackend.model.LocalUser;
import com.fram.ecommercebackend.model.dao.LocalUserDao;
import com.fram.ecommercebackend.service.JwtService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
@Log4j2
public class JWTRequestFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private LocalUserDao localUserDao;

    public JWTRequestFilter(JwtService jwtService, LocalUserDao localUserDao){
        this.jwtService = jwtService;
        this.localUserDao = localUserDao;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        log.info("The token to decode: {}", tokenHeader);
        if(tokenHeader !=null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);

            try{
                String username = jwtService.getUSerName(token);
                Optional<LocalUser> OpLocalUser = localUserDao.findByUsernameIgnoreCase(username);
                if(OpLocalUser.isPresent()){

                    LocalUser user = OpLocalUser.get();
                    log.info("The user we want: {}",user);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (JWTDecodeException ex){

            }
        }
        filterChain.doFilter(request, response);
    }
}
