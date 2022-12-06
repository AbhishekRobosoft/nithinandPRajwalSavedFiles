package com.robosoft.lorem.filter;

import com.robosoft.lorem.service.UserServiceImpl;
import com.robosoft.lorem.utility.JWTUtility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        String userName = null;

        try
        {
            if (null != authorization && authorization.startsWith("Bearer "))
            {
                token = authorization.substring(7);
                userName = jwtUtility.getUsernameFromToken(token);
            }
        }
        catch (ExpiredJwtException e)
        {
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
            httpServletResponse.getWriter().print("Session Expired please login again...");
            return;
        }
        catch (MalformedJwtException e)
        {
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            httpServletResponse.getWriter().print("Invalid token..");
            return;
        }


        if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userServiceImpl.loadUserByUsername(userName);

                if (jwtUtility.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                    );

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
