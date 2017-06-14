package self.srr.bot.biz.fish.interceptor;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Fish spring mvc configuration
 * <p>
 * Created by Sharuru on 2017/06/14.
 */
@EnableAutoConfiguration
@Configuration
public class FishMvcConfiguration extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FishInterceptor()).addPathPatterns("/**/fish/**");
    }
}
