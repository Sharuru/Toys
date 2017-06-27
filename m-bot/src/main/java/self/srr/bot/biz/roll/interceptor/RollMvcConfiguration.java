package self.srr.bot.biz.roll.interceptor;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Roll spring mvc configuration
 * <p>
 * Created by Sharuru on 2017/06/27.
 */
@EnableAutoConfiguration
@Configuration
public class RollMvcConfiguration extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RollInterceptor()).addPathPatterns("/**/roll/**");
    }
}
