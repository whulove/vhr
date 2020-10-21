package org.javaboy.vhr.config;

import org.javaboy.vhr.model.Menu;
import org.javaboy.vhr.model.Role;
import org.javaboy.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/*
 * 这个类的作用主要是根据用户传过来的请求地址 分析出请求需要的角色
 * */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuService menuService;

    //比对工具，这里用来比对request的url和menu的url
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //当前请求的地址
        String requestUrl =((FilterInvocation)object).getRequestUrl();
        List<Menu> menus  = menuService.getAllMenusWithRole();
        for (Menu menu : menus) {
            //match()中第一个是匹配规则，第二个是需要匹配的对象
            if (antPathMatcher.match(menu.getUrl(),requestUrl)){
                List<Role> roles = menu.getRoles();
                String[] str = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    str[i] = roles.get(i).getName();

                }
                return SecurityConfig.createList(str);
            }
        }
        //没有匹配上的 登陆后访问           标记 后续判断用
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
