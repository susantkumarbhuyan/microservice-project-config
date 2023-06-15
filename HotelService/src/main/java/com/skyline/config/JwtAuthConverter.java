package com.skyline.config;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		Collection<GrantedAuthority> authorities = Stream
				.concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(), extractResourceRoles(jwt).stream())
				.collect(Collectors.toSet());
		return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
	}

	private String getPrincipalClaimName(Jwt jwt) {
		String claimName = SecurityConstants.JWT_PRINCIPAL_ATTRIBUTE;
		return jwt.getClaim(claimName);
	}

//	@SuppressWarnings("unchecked")
//	private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
//		Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
//		Map<String, Object> resource;
//		Collection<String> resourceRoles;
//		if (resourceAccess == null
//				|| (resource = (Map<String, Object>) resourceAccess
//						.get(SecurityConstants.JWT_RESOURCE_ACCESS_ID)) == null
//				|| (resourceRoles = (Collection<String>) resource.get("roles")) == null) {
//			return Set.of();
//		}
//		return resourceRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//				.collect(Collectors.toSet());
//	}
	
	
	// new 
     @SuppressWarnings({ "rawtypes", "unchecked" })
     private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
         return Stream.of("$.realm_access.roles", "$.resource_access.*.roles").flatMap(claimPaths -> {
             Object claim;
             try {
                 claim = JsonPath.read(jwt.getClaims(), claimPaths);
             } catch (PathNotFoundException e) {
                 claim = null;
             }
             if (claim == null) {
                 return Stream.empty();
             }
             if (claim instanceof String claimStr) {
                 return Stream.of(claimStr.split(","));
             }
             if (claim instanceof String[] claimArr) {
                 return Stream.of(claimArr);
             }
             if (Collection.class.isAssignableFrom(claim.getClass())) {
                 final var iter = ((Collection) claim).iterator();
                 if (!iter.hasNext()) {
                     return Stream.empty();
                 }
                 final var firstItem = iter.next();
                 if (firstItem instanceof String) {
                     return (Stream<String>) ((Collection) claim).stream();
                 }
                 if (Collection.class.isAssignableFrom(firstItem.getClass())) {
                     return (Stream<String>) ((Collection) claim).stream().flatMap(colItem -> ((Collection) colItem).stream()).map(String.class::cast);
                 }
             }
             return Stream.empty();
         })
         /* Insert some transformation here if you want to add a prefix like "ROLE_" or force upper-case authorities */
         //.map(SimpleGrantedAuthority::new)
         .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
         .map(GrantedAuthority.class::cast).toList();
     }
}