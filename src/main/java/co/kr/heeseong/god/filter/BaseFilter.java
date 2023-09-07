package co.kr.heeseong.god.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class BaseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uuid = UUID.randomUUID().toString();
        log.info("uuid : {}, api request url : {}", uuid, ((HttpServletRequest)request).getRequestURL());

        StopWatch sw = new StopWatch();
        sw.start();

        chain.doFilter(request, response);

        sw.stop();
        log.info("uuid : {}, api running time : {} (ms)", uuid, sw.getTotalTimeMillis());
    }
}
