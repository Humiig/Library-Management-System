package com.ensas.librarymanagementsystem.bootstrap;

import com.ensas.librarymanagementsystem.entities.security.Authority;
import com.ensas.librarymanagementsystem.entities.security.Role;
import com.ensas.librarymanagementsystem.entities.security.User;
import com.ensas.librarymanagementsystem.repositories.*;
import com.ensas.librarymanagementsystem.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Transactional
@RequiredArgsConstructor
public class LoadData implements CommandLineRunner {

    private final UserServiceImpl userService;
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        Authority addBook = new Authority();
        addBook.setPermission("create_book");
        authorityRepository.save(addBook);

        Authority deleteBook = new Authority();
        deleteBook.setPermission("delete_book");
        authorityRepository.save(deleteBook);

        Authority updateBook = new Authority();
        updateBook.setPermission("update_book");
        authorityRepository.save(updateBook);

        Authority borrowBook = new Authority();
        borrowBook.setPermission("borrow_book");
        authorityRepository.save(borrowBook);

        Authority addAuthor = new Authority();
        addAuthor.setPermission("create_author");
        authorityRepository.save(addAuthor);

        Authority deleteAuthor = new Authority();
        deleteAuthor.setPermission("delete_author");
        authorityRepository.save(deleteAuthor);

        Authority updateAuthor = new Authority();
        updateAuthor.setPermission("update_author");
        authorityRepository.save(updateAuthor);

        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleRepository.save(adminRole);

        Set<Authority> adminAuthorities = new HashSet<>();
        adminAuthorities.add(addBook);
        adminAuthorities.add(deleteBook);
        adminAuthorities.add(updateBook);
        adminAuthorities.add(addAuthor);
        adminAuthorities.add(deleteAuthor);
        adminAuthorities.add(updateAuthor);
        adminRole.setAuthorities(adminAuthorities);

        roleRepository.save(adminRole);

        Role studentRole = new Role();
        studentRole.setName("STUDENT");
        roleRepository.save(studentRole);

        Set<Authority> studentAuthorities = new HashSet<>();
        studentAuthorities.add(borrowBook);
        studentRole.setAuthorities(studentAuthorities);

        roleRepository.save(studentRole);

        User admin = new User();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@gmail.com");
        admin.setPhone("0746598475");
        admin.setAddress("address admin");
        userRepository.save(admin);
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);
        userService.addRolesToUser(admin.getId(),roles);

        User student = new User();
        student.setFirstName("student");
        student.setLastName("student");
        student.setUsername("student");
        student.setPassword(passwordEncoder.encode("student"));
        student.setEmail("student@gmail.com");
        student.setPhone("0746598475");
        student.setAddress("address student");
        userRepository.save(student);
        List<Role> roles1 = new ArrayList<>();
        roles1.add(studentRole);
        userService.addRolesToUser(student.getId(),roles1);


    }
}
