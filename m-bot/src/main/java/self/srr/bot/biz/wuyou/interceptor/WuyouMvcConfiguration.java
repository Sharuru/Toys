package self.srr.bot.biz.wuyou.interceptor;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Roll spring mvc configuration
 * <p>
 * Created by Sharuru on 2021/02/19.
 */
@EnableAutoConfiguration
@Configuration
public class WuyouMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WuyouInterceptor()).addPathPatterns("/**/wuyou/**");
    }
}
