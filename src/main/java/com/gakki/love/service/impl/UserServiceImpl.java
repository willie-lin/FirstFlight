package com.gakki.love.service.impl;

import com.gakki.love.domain.FeedBack;
import com.gakki.love.domain.User;
import com.gakki.love.repository.FeedBackRepository;
import com.gakki.love.repository.UserRepository;
import com.gakki.love.service.UserService;
import com.gakki.love.utils.EncryptUtils;
import com.gakki.love.utils.IntegerUtils;
import com.gakki.love.utils.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 林漠 on 2017/6/12.
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeedBackRepository feedBackRepository;

    @Override
    @Transactional(readOnly = true)
    public Pagination<User> getTopPeople(int page, int size){

        page = -1;

        int start = page*size;

        List<User> users = userRepository.getByIdJoinTopicUserId(start,size);

        int count = (int) userRepository.count();

        Pagination<User> pageUser = new Pagination<>( users,page,size,count);

        return pageUser;

    }

    @Override
    @Transactional(readOnly = true)
    public User login(User user){

        User u = null;

        if (user.getUsername() != null){

            u = userRepository.getByEmailAndPassword(user.getUsername(),user.getPassword());

        }else {

            u = userRepository.getByEmailAndPassword(user.getEmail(),user.getPassword());
        }

        return u != null ? u : new User();
    }

    @Override
    @Transactional
    public boolean newOrUpdate(User user){
        return IntegerUtils.isPositiveValue(userRepository.saveAndFlush(user).getId());
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Integer id){

        return  userRepository.getById(id);

    }


    @Override
    @Transactional(readOnly = true)
    public User getByName(String username){

        String regex  ="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        if (username.matches(regex)){
            return userRepository.getByEmail(EncryptUtils.execEncrypt(username));
        }
        return userRepository.getByUsername(username);

    }

    @Override
    @Transactional
    public void updateHead(User user){

         userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email){

        return userRepository.getByEmail(email);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean feedback(FeedBack feedBack){

        FeedBack f =feedBackRepository.saveAndFlush(feedBack);

        if (f==null){
            return false;
        }

        return true;

    }





}
