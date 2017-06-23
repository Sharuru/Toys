package self.srr.bot.biz.translate.interceptor;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import self.srr.bot.biz.fish.interceptor.FishInterceptor;

/**
 * Translate spring mvc configuration
 * <p>
 * Created by Sharuru on 2017/06/23.
 */
@EnableAutoConfiguration
@Configuration
public class TranslateMvcConfiguration extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FishInterceptor()).addPathPatterns("/**/translate/**");
    }
}
