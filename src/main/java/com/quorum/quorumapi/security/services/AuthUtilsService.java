package com.quorum.quorumapi.security.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.quorum.quorumapi.models.User;

@Service
public class AuthUtilsService {
  public UserDetails getCurrentUser() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null)
      return null;

    var principal = authentication.getPrincipal();

    var isUser = principal instanceof UserDetails;

    if (!isUser)
      return null;

    return (UserDetails) principal;
  }

  public boolean isCurrentUser(Integer id) {
    var detailedUser = getCurrentUser();
    var authUser = (User) detailedUser;

    return authUser.getId() == id;
  }

  @SuppressWarnings("unlikely-arg-type")
  public boolean isMod(Integer id) {
    var detailedUser = getCurrentUser();
    return detailedUser.getAuthorities().contains("MOD");
  }
}
