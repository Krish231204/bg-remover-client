package in.vipransh.bgremover.service.impl;

import org.springframework.stereotype.Service;
import in.vipransh.bgremover.service.UserService;
import in.vipransh.bgremover.dto.UserDTO;
import in.vipransh.bgremover.entity.UserEntity;
import in.vipransh.bgremover.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        Optional<UserEntity> OptionalUser =  userRepository.findByClerkId(userDTO.getClerkId());
        if(OptionalUser.isPresent()){
            UserEntity exsistingUser = OptionalUser.get();
            exsistingUser.setEmail(userDTO.getEmail());
            exsistingUser.setFirstName(userDTO.getFirstName());
            exsistingUser.setLastName(userDTO.getLastName());
            exsistingUser.setPhotoUrl(userDTO.getPhotoUrl());
            if (userDTO.getCredits() != null) {
                exsistingUser.setCredits(userDTO.getCredits());
            }
            exsistingUser = userRepository.save(exsistingUser);
            return mapToDTO(exsistingUser);
        }
        UserEntity newUser = mapToEntity(userDTO);
        userRepository.save(newUser);
        return mapToDTO(newUser);
    }

    private UserDTO mapToDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .clerkId(userEntity.getClerkId())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .photoUrl(userEntity.getPhotoUrl())
                .credits(userEntity.getCredits())
                .build();
    }

    private UserEntity mapToEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .clerkId(userDTO.getClerkId())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .photoUrl(userDTO.getPhotoUrl())
                .build();
        
    }

}
