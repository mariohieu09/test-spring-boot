package com.example.springproject.service.impl;

import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.entity.User;
import com.example.springproject.exception.UserNotFoundException;
import com.example.springproject.repository.UserRepository;
import com.example.springproject.service.UserService;
import com.example.springproject.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public UserResponse getById(String id) {
    log.info("(request) getById: {}", id);
    UserResponse user = repository.getByUserId(id);
    if (user != null)
      return user;
    else
      throw new UserNotFoundException();
  }

  @Override
  public UserResponse create(UserRequest request) {
    log.info("(request) create: {}", request);
//    User user = MapperUtils.toEntity(request, User.class);
//    create(user);
//    return MapperUtils.toDTO(user, UserResponse.class);
    User user = new User(
          request.getUsername(),
          request.getPassword(),
          request.getEmail(),
          request.getPhone(),
          request.getRole()
    );
//    user.setId("ok");
    this.create(user);
    return new UserResponse(
          user.getId(),
          user.getUsername(),
          user.getPassword(),
          user.getEmail(),
          user.getPhone(),
          user.getRole()
    );
  }

  @Override
  public void delete(Long id) {

  }

  @Override
  public PageResponse<UserResponse> list(String keyword, int size, int page, boolean isAll) {
    log.info("(list) keyword: {}, size : {}, page: {}, isAll: {}", keyword, size, page, isAll);
    Pageable pageable = PageRequest.of(page, size);
    Page<UserResponse> list = isAll ?
          repository.findAllShipment(pageable) : repository.searchUser(pageable, keyword);
    return PageResponse.of(list.getContent(), (int) list.getTotalElements());
  }
}