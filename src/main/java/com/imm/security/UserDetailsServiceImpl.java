package com.imm.security;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.imm.business.service.UserMngService;
import com.imm.business.service.role.MngRoleService;
import com.imm.business.service.role.MngUserRoleService;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.business.vo.role.MngRoleVo;
import com.imm.business.vo.role.MngUserRoleVo;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;

public class UserDetailsServiceImpl implements UserDetailsService {

	Log log = LogFactory.getLogger(this.getClass());
	
	@Autowired
	private MngUserRoleService mngUserRoleService;
	
	@Autowired
	private MngRoleService mngRoleService;
	
	@Autowired
	private UserMngService mngService;
	
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		User securityUser = null;
		
		UserMngVo mngVo = mngService.getUserByLoginId(userName);
		//账号密码错误，可以在这里手动抛出异常，让验证失败处理器AuthenticationFailureHandler进行处理
        if(null == mngVo){
        	log.error("userName does not exist! " + userName);  
            throw new UsernameNotFoundException("userName does not exist!" + userName); 
        }
        Collection<GrantedAuthority> grantedAuthorities = this.getGrantedAuthorities(mngVo);
        
        boolean enables = true;  
        boolean accountNonExpired = true;  
        boolean credentialsNonExpired = true;  
        boolean accountNonLocked = true; 
        securityUser = new org.springframework.security.core.userdetails.User(mngVo.getLoginId(), mngVo.getPassword(),
        		enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);  
        return securityUser;
        
	}
	
	
	/**
     * 根据用户获取该用户拥有的角色
     * @param user 当前用户
     * @return 用户角色集合
     */
    private Set<GrantedAuthority> getGrantedAuthorities(UserMngVo user) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        if(null != user){
        	 List<MngUserRoleVo> roles = mngUserRoleService.getMngUserRoleByUserId(user.getId());
             if(null != roles && roles.size() > 0 ) {
                 for(MngUserRoleVo role : roles) {
                	 MngRoleVo mngRoleVo = mngRoleService.getMngRoleById(role.getRoleId());
                     grantedAuthorities.add(new SimpleGrantedAuthority(mngRoleVo.getName()+""));
                 }  
             }
        }
        return grantedAuthorities;  
    }
	

}
