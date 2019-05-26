package com.ritonelion.bootopen.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer
{
    public final static String SESSION_ID = "id";
    public final static String SESSION_role = "role";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry)
    {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");

        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter
    {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception
        {
            HttpSession session = request.getSession();
            if (session.getAttribute(SESSION_ID) != null && session.getAttribute(SESSION_role) != null)
                return true;

            String url = "/login";
            response.sendRedirect(url);
            return false;
        }
    }
}
