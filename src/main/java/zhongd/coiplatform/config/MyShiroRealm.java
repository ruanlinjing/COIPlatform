package zhongd.coiplatform.config;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import zhongd.coiplatform.entity.DO.user.IgPermission;
import zhongd.coiplatform.entity.DO.user.IgRole;
import zhongd.coiplatform.entity.DO.user.IgUser;
import zhongd.coiplatform.service.user.IgUserService;

public class MyShiroRealm extends AuthorizingRealm{
	@Autowired
	private IgUserService igUserService;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		IgUser currentUser = igUserService.getIgUserByUsername(username);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<IgRole> roleSet = (Set<IgRole>)igUserService.getUserRoleSet(currentUser.getIgUserId()).get("set");
		Set<IgPermission> permissionSet = (Set<IgPermission>) igUserService.getUserUserPermissions(currentUser.getIgUserId()).get("set");
		Set<String> roleNames = new HashSet<String>();
		Set<String> permissionNames = new HashSet<String>();
		if (Objects.nonNull(roleSet) && !roleSet.isEmpty()) {
			for (IgRole role : roleSet) {
				roleNames.add(role.getRoleCode());
			}
		}
		if (Objects.nonNull(permissionSet) && !permissionSet.isEmpty()) {
			for (IgPermission permission : permissionSet) {
				permissionNames.add(permission.getPermissionCode());
			}
		}
		info.setRoles(roleNames);
		info.setStringPermissions(permissionNames);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
	}


}
