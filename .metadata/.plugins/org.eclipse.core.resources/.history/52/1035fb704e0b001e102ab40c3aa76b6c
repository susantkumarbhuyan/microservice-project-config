package com.hsignz.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomPermissionHandler implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		boolean hasAccess = false;

		Iterator<? extends GrantedAuthority> itr = authentication.getAuthorities().iterator();
		List<String> roleIdList = new ArrayList<>();
		while (itr.hasNext()) {
			GrantedAuthority grantedAuthority = (GrantedAuthority) itr.next();
			if (grantedAuthority != null && grantedAuthority.getAuthority() != null) {
				if (grantedAuthority.getAuthority().equals("ROLE_admin")) {
					return true;
				}
				roleIdList.add(grantedAuthority.getAuthority().replace("ROLE_", ""));
			}
		}

		String permissions = permission.toString();
		String[] methodPermissions = permissions.split(",");
		for (String tmpPermission : methodPermissions) {
			hasAccess = roleIdList.contains(tmpPermission);
			if (hasAccess) {
				break;
			}
		}
		log.debug("");
		return hasAccess;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		boolean hasAccess = false;

		hasAccess = hasPermission(authentication, targetType, permission);
		if (hasAccess) {
			// "preferred_username"
			Jwt jwt = (Jwt) authentication.getPrincipal();
			if (!targetId.equals(jwt.getClaim(targetType))) {
				return false;
			}
		}
		return hasAccess;
	}

}
