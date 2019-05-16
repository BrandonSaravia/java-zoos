package com.zoo.animals.service;

import com.zoo.animals.model.User;
import com.zoo.animals.model.UserRoles;
import com.zoo.animals.model.Zoo;
import com.zoo.animals.repo.RoleRepository;
import com.zoo.animals.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository rolerepos;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("invalid credentials");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findUserById(long id) throws EntityNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user with id " + id + " not found"));
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("user with id " + id + " not found");
        }
    }

    @Transactional
    @Override
    public User save(User user) {
        User newUser = new User();

        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserRoles())
        {
            newRoles.add(new UserRoles(newUser, ur.getRole()));
        }
        newUser.setUserRoles(newRoles);

        return userRepository.save(newUser);

    }

    @Transactional
    @Override
    public User update(User user, long id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName());

        if (currentUser != null)
        {
            if (id == currentUser.getUserid())
            {
                if (user.getUsername() != null)
                {
                    currentUser.setUsername(user.getUsername());
                }

                if (user.getPassword() != null)
                {
                    currentUser.setPassword(user.getPassword());
                }

                if (user.getUserRoles().size() > 0)
                {
                    // with so many relationships happening, I decided to go
                    // with old school queries
                    // delete the old ones
                    rolerepos.deleteUserRolesByUserId(currentUser.getUserid());

                    // add the new ones
                    for (UserRoles ur : user.getUserRoles())
                    {
                        rolerepos.insertUserRoles(id, ur.getRole().getRoleid());
                    }
                }
                return userRepository.save(currentUser);
            }
            else
            {
                throw new EntityNotFoundException(Long.toString(id) + " Not current user");
            }
        }
        else
        {
            throw new EntityNotFoundException(authentication.getName());
        }

    }
}
