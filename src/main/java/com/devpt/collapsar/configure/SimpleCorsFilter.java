package com.devpt.collapsar.configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lishaoyan on 2015/5/19.
 */
@Component
public class SimpleCorsFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCorsFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
//        if (req.getHeader("Access-Control-Request-Method") != null || "OPTIONS".equals(req.getMethod())) {
//            logger.debug("origin: {}", req.getHeader("Origin"));
            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            response.setHeader("Access-Control-Max-Age", "0");
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//        }
        chain.doFilter(req, res);
    }
}
