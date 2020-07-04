package com.pri.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * className: WebSecurityConfig <BR>
 * description: 安全配置<BR>
 * remark: <BR>
 * author: 1024 <BR>
 * createDate: 2020-06-28 10:13 <BR>
 */
@Configuration /* 就相当于springmvc.xml文件 ChenQi*/
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)//开启基于方法授权
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * methodName: userDetailsService <BR>
     * description:  定义用户信息服务（查询用户信息）<BR>
     * remark: 将用户信息添加到security框架的内存中，供账号密码登录时，查询使用；<BR>
     * 但是在生产环境中，用户信息是会从数据量中获取的<BR>
     * param:  <BR>
     * return: org.springframework.security.core.userdetails.UserDetailsService <BR>
     * author: ChenQi <BR>
     * createDate: 2020-07-04 22:43 <BR>
     */
/*    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager ();
        manager.createUser(User.withUsername("zhangsan").password("123").authorities("p1").build());
        manager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
        return manager;
    }*/

    /**
     * methodName: passwordEncoder <BR>
     * description: 密码编码器 <BR>
     * remark: <BR>
     * param:  <BR>
     * return: org.springframework.security.crypto.password.PasswordEncoder <BR>
     * author: ChenQi <BR>
     * createDate: 2020-06-29 23:44 <BR>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        /* NoOpPasswordEncoder表示明文，不进行编码设置；在生产环境中，不可能使用明文的 ChenQi*/
        // return NoOpPasswordEncoder.getInstance();
        /* 使用BCrypt编码格式 ChenQi*/
        return new BCryptPasswordEncoder();
    }

    //安全拦截机制（最重要）
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 屏蔽CSRF控制，即spring security不再限制CSRF 1024
        http.csrf().disable()
                .authorizeRequests()
               /*.antMatchers("/r/r1").hasAuthority("p2")
               .antMatchers("/r/r2").hasAuthority("p2")*/
                .antMatchers("/r/**").authenticated()//所有/r/**的请求必须认证通过
                .anyRequest().permitAll()//除了/r/**，其它的请求可以访问
                .and()
                .formLogin()//允许表单登录
                // 指定我们自己的登录页,spring security以重定向方式跳转到/login-view 1024
                // .loginPage("/login-view")//登录页面
                // 指定登录处理的URL，也就是用户名、密码表单提交的目的路径  1024
                //.loginProcessingUrl("/login")
                //自定义登录成功的页面地址
                .successForwardUrl("/login-success")
                .and()
                // 会话控制
                .sessionManagement()
                // 如果需要就创建一个Session（默认）登录时
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                // 提供系统退出支持，使用 WebSecurityConfigurerAdapter 会自动被应用  1024
                .logout()
                // 设置触发退出操作的URL (默认是 /logout ).1024
                .logoutUrl("/logout")
                // 退出之后跳转的URL。默认是 /login?logout  1024
                .logoutSuccessUrl("/login");
    }
}
