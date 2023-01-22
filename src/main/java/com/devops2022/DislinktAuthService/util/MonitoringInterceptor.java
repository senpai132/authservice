package com.devops2022.DislinktAuthService.util;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;

@Component
public class MonitoringInterceptor implements HandlerInterceptor {
    private final Counter requests;
    private final Counter successfulRequests;
    private final Counter failedRequests;
    private final Counter notFoundRequests;
    private final Counter uniqueUserRequests;
    private HashMap<String, Date> uniqueUsers;
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringInterceptor.class);

    public MonitoringInterceptor(MeterRegistry registry) {
        this.requests = Counter.builder("auth-request")
                .description("Number of HTTP requests for server endpoints")
                .register(registry);
        this.successfulRequests = Counter.builder("auth-request-successful")
                .description("Number of successful HTTP requests for server endpoints")
                .register(registry);
        this.failedRequests = Counter.builder("auth-request-failed")
                .description("Number of failed HTTP requests for server endpoints")
                .register(registry);
        this.notFoundRequests = Counter.builder("auth-request-notFound")
                .description("Number of 404 HTTP requests for server endpoints")
                .register(registry);
        this.uniqueUserRequests = Counter.builder("auth-request-uniqueUser")
                .description("Number of unique user HTTP requests for server endpoints")
                .register(registry);
        uniqueUsers = new HashMap<String, Date>();
    }

    @Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler) throws Exception
    {
        LOGGER.info("Request sent to " + requestServlet.getRequestURL());
        requests.increment();

        String uniqueUser =  getClientIpAddressIfServletRequestExist(requestServlet) + " " +
                requestServlet.getHeader("User-Agent");
        Date currentDate = new Date();
        if (uniqueUsers.containsKey(uniqueUser)) {
            if (addMinutesToDate(5, uniqueUsers.get(uniqueUser)).before(currentDate)) {
                uniqueUsers.put(uniqueUser, currentDate);
                uniqueUserRequests.increment();
            }
        } else {
            uniqueUsers.put(uniqueUser, currentDate);
            uniqueUserRequests.increment();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        if (response.getStatus() >= 400) {
            if (response.getStatus() == 404) {
                notFoundRequests.increment();
            }
            failedRequests.increment();
        } else {
            successfulRequests.increment();
        }

        LOGGER.info("System finished request" + request.getRequestURL() +
                " with EXIT CODE: " + response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception
    {
    }

    private static Date addMinutesToDate(int minutes, Date beforeTime) {

        final long ONE_MINUTE_IN_MILLIS = 60000;
        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs
                + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

    private static String getClientIpAddressIfServletRequestExist(HttpServletRequest request) {

        final String[] IP_HEADER_CANDIDATES = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        };

        if (request == null) {
            return "0.0.0.0";
        }

        for (String header: IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                String ip = ipList.split(",")[0];
                return ip;
            }
        }

        return request.getRemoteAddr();
    }
}
