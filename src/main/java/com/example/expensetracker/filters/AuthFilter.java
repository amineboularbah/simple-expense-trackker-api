package com.example.expensetracker.filters;

import com.example.expensetracker.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpServletRequest.getHeader("Authorization");
        if(authHeader != null){
            String[] authHeaderArr = authHeader.split("Bearer");
            if(authHeaderArr.length > 1 && authHeaderArr[1] != null){
                String token= authHeaderArr[1];
                try{
                    Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY).parseClaimsJws(token).getBody();
                    httpServletRequest.setAttribute("userId", Integer.parseInt(claims.get("userId").toString()));
                }catch (Exception e){
                    httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "Invalid/expired token");
                    return;
                }
            }else{
                httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be Bearer [token]");
                return;
            }
        }else{
            httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(),"Authorization token must be provided");
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);



    }
}
