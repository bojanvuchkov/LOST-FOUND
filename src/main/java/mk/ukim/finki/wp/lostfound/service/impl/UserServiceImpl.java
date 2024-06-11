package mk.ukim.finki.wp.lostfound.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lostfound.model.Email;
import mk.ukim.finki.wp.lostfound.model.User;
import mk.ukim.finki.wp.lostfound.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp.lostfound.repository.EmailRepository;
import mk.ukim.finki.wp.lostfound.repository.UserRepository;
import mk.ukim.finki.wp.lostfound.service.UserService;
import org.springframework.stereotype.Service;

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
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void sendMail(HttpServletRequest request, String receiverMail, String subject, String message) {
        User rec=userRepository.findByEmail(receiverMail).orElseThrow(UserNotFoundException::new);
        String username = request.getUserPrincipal().getName();
        User sen=userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        Email email = new Email(sen, rec, subject, message);
        emailRepository.save(email);
        rec.getReceivedEmails().add(email);
        userRepository.save(rec);
    }
}
