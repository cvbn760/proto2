package com.protobuf.test.util;

import com.protobuf.test.Model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private static User user;

    public static AuthorizedUser safeGet(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static long authUserId() {
        return safeGet().getId();
    }

    public static boolean canEdit(User userx) {
        if (safeGet() == null) {
            if (userx.getId() != null) {
                throw new AccessDeniedException("Anonymous cannot edit other people's accounts ...");
            }
            // Если аноним и у сохраняемого пользователя нет id то можно сохранить

            return true;
        }
        else {
            if (authUserId() != userx.getId()) {
                throw new AccessDeniedException("You cannot edit other people's accounts ...");
            }
            return true;
        }
    }
}
