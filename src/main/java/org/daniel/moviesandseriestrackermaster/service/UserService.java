    package org.daniel.moviesandseriestrackermaster.service;

    import org.daniel.moviesandseriestrackermaster.dto.UserDTO;
    import org.daniel.moviesandseriestrackermaster.models.User;
    import org.daniel.moviesandseriestrackermaster.repository.UserRepository;
    import org.springframework.stereotype.Service;

    import java.util.Optional;
    import java.util.UUID;

    @Service
    public class UserService {

        private final UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public User createUser(UserDTO userDTO) {
           if(userRepository.existsByEmail(userDTO.getEmail())){
               throw new IllegalArgumentException("Email is taken");
           }

           User user = new User();
           user.setName(userDTO.getName());
           user.setEmail(userDTO.getEmail());

           return userRepository.save(user);
        }

        public Optional<User> getUserById(UUID id) {
            return userRepository.findById(id);
        }


    }
