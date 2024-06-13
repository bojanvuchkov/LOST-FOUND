package mk.ukim.finki.wp.lostfound.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lostfound.model.Email;
import mk.ukim.finki.wp.lostfound.model.User;
import mk.ukim.finki.wp.lostfound.model.exceptions.EmailNotFoundException;
import mk.ukim.finki.wp.lostfound.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp.lostfound.repository.EmailRepository;
import mk.ukim.finki.wp.lostfound.repository.UserRepository;
import mk.ukim.finki.wp.lostfound.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;

    public UserServiceImpl(UserRepository userRepository, EmailRepository emailRepository) {
        this.userRepository = userRepository;
        this.emailRepository = emailRepository;
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteMessage(String userId, Long messageId) {
       User user = findById(userId).orElseThrow(UserNotFoundException::new);
       Email email = emailRepository.findById(messageId).orElseThrow(EmailNotFoundException::new);
       user.getReceivedEmails().remove(email);
       userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void sendMail(HttpServletRequest request, String receiverMail, String subject, String message) {
        User rec=userRepository.findByEmail(receiverMail).orElseThrow(UserNotFoundException::new);
        String username = request.getUserPrincipal().getName();
        User sen=userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        Email email = new Email(sen, rec, subject, message);
        rec.getReceivedEmails().add(email);
        emailRepository.save(email);
        userRepository.save(rec);
    }
}
