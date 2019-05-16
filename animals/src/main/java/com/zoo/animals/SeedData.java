package com.zoo.animals;


import com.zoo.animals.model.Role;
import com.zoo.animals.model.User;
import com.zoo.animals.model.UserRoles;
import com.zoo.animals.model.Zoo;
import com.zoo.animals.repo.RoleRepository;
import com.zoo.animals.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    private RoleRepository rolerepos;
    private UserRepository userrepos;

    public SeedData(RoleRepository rolerepos, UserRepository userrepos)
    {
        this.rolerepos = rolerepos;
        this.userrepos = userrepos;
    }

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");

        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));

        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));

        rolerepos.save(r1);
        rolerepos.save(r2);

        User u1 = new User("yoyo", "yoyo", users);

        User u2 = new User("admin", "password", admins);

        userrepos.save(u1);
        userrepos.save(u2);
    }
}