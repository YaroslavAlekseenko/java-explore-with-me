package ru.practicum.main.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.dto.user.UserDto;
import ru.practicum.main.exceptions.NameAlreadyExistException;
import ru.practicum.main.mappers.UserMapper;
import ru.practicum.main.repositories.UserRepository;
import ru.practicum.main.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByName(userDto.getName())) {
            log.warn(String.format("Can't create user with name: %s, the name was used by another user", userDto.getName()));
            throw new NameAlreadyExistException(String.format("Can't create user with name: %s, the name was used by another user",
                    userDto.getName()));
        }
        log.debug(String.format("The user with name %s was created", userDto.getName()));
        return userMapper.toUserDto(userRepository.save(userMapper.toUserModel(userDto)));
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        log.debug("Received users");
        PageRequest page = PageRequest.of(from, size);
        if (ids == null) {
            return userRepository.findAll(page).stream()
                    .map(userMapper::toUserDto)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findAllById(ids).stream()
                    .map(userMapper::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void deleteUser(Long id) {
        log.debug("User with id: {} was deleted ", id);
        userRepository.deleteById(id);
    }
}