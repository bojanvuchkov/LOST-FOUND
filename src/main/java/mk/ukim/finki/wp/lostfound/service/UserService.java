package mk.ukim.finki.wp.lostfound.service;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lostfound.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(String id);
    void deleteMessage(String userId, Long messageId);
    Optional<User> findByEmail(String email);
    void sendMail(HttpServletRequest request, String receiverMail, String subject, String message);
}
