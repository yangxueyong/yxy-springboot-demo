package com.example.yxy.config;

import com.example.yxy.entity.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SecurityUtil {

//    @Autowired
//    private UserMapper userMapper ;

    //主要是前面的用户名，后面的随便写
    public static String[] userRoleArrays = {
            "会展领导","会展经理","会展暖通主管","会展弱电主管",
            "会展消防主管","会展安保主管","会展员工","物业项目经理",
            "物业工程部经理","物业暖通班长", "物业暖通组员","物业弱电班长",
            "物业弱电组员","物业消防班长","物业消防组员","物业维修班长",
            "物业维修组员","物业安保主管","物业安保队长","物业安保班长","文员"} ;

    private static InMemoryUserDetailsManager inMemoryUserDetailsManager ;

    public InMemoryUserDetailsManager findInstance(){
        try{
            if(SecurityUtil.inMemoryUserDetailsManager==null){
                SecurityUtil.inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

                List<String> securityUserList = new ArrayList<>() ;

                //查询并添加所有用户ID
                List<SecurityUser> userList = new ArrayList<>();//userMapper.selectUserList(null);
                userList.add(new SecurityUser("zs"));
                userList.add(new SecurityUser("zhangdada"));
                userList.add(new SecurityUser("wangdada"));
                userList.add(new SecurityUser("lidada"));

                if(Objects.nonNull(userList) && userList.size()>0){
                    userList.stream().forEach(user -> {
                        securityUserList.add(user.getId().toString());
                    });
                }
                //添加所有用户角色
                securityUserList.addAll(Arrays.asList(userRoleArrays));

                if(Objects.nonNull(securityUserList) && securityUserList.size()>0){
                    String[][] userListArray = new String[securityUserList.size()][4];
                    for (int i = 0 ; i<securityUserList.size(); i++){
                        String userIdOrRoleName = securityUserList.get(i);
                        String[] userArray = {userIdOrRoleName,"password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"};
                        userListArray[i] = userArray ;
                    }

                    for (String[] user : userListArray) {
                        List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
                        SecurityUtil.inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                                authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
                    }
                    log.info("===============初始化加载 inMemoryUserDetailsManager 完毕！！！");
                }
            }
        }catch (Exception e){
            log.info("=======================findInstance异常" + e.getMessage());
        }

        return SecurityUtil.inMemoryUserDetailsManager;
    }

    public synchronized void createUser(String userName) {
        try{
            if(!SecurityUtil.inMemoryUserDetailsManager.userExists(userName)) {
                List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>(){{
                    add(new SimpleGrantedAuthority("ROLE_ACTIVITI_USER"));
                    add(new SimpleGrantedAuthority("GROUP_activitiTeam"));
                }};
                SecurityUtil.inMemoryUserDetailsManager.createUser(new User(userName, passwordEncoder().encode("password"),roles));
            }
        }catch (Exception e){
            log.info("==============createUser异常" + e.getMessage());

        }
    }

    public void logInAs(String userName) {
        try{
            createUser(userName);

            UserDetails user = findInstance().loadUserByUsername(userName);
            if (user == null) {
                throw new IllegalStateException("User " + userName + " doesn't exist, please provide a valid user");
            }

            SecurityContextHolder.setContext(new SecurityContextImpl(new Authentication() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return user.getAuthorities();
                }

                @Override
                public Object getCredentials() {
                    return user.getPassword();
                }

                @Override
                public Object getDetails() {
                    return user;
                }

                @Override
                public Object getPrincipal() {
                    return user;
                }

                @Override
                public boolean isAuthenticated() {
                    return true;
                }

                @Override
                public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                }

                @Override
                public String getName() {
                    return user.getUsername();
                }
            }));
            org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(userName);
        }catch (Exception e){
            log.info("==============logInAs异常" + e.getMessage());
        }
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


