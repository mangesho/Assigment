package com.assigment;

import android.support.test.runner.AndroidJUnit4;

import com.assigment.data.UserDao;
import com.assigment.di.module.AppModule;
import com.assigment.di.module.DataModule;
import com.assigment.models.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import javax.inject.Inject;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserAddInsertUpdateDeleteTest {

    @Inject
    UserDao userDao;

    public User user = new User();

    @Before
    public void setUp() {
        TestComponent component = DaggerTestComponent.builder()
                .appModule(new AppModule(App.getAppInstance()))
                .dataModule(new DataModule()).build();
        component.inject(this);

    }

    @Test
    public void insertUser() {
        userDao.deleteAllUsers();

        user.setFirstName("Test");
        user.setLastName("Test");
        user.setId(1);
        user.setAvatar("");
        userDao.insertUser(user);

        List<User> userList = userDao.getAllUsers();

        assertThat(userList.size() , is(1));
        assertTrue(userList.get(0).getFirstName().equals("Test"));
        assertTrue(userList.get(0).getLastName().equals("Test"));
    }

    @Test
    public void deleteUser() {
        userDao.deleteAllUsers();

        user.setFirstName("Test");
        user.setLastName("Test");
        user.setId(1);
        user.setAvatar("");
        userDao.insertUser(user);
        List<User> userList = userDao.getAllUsers();

        assertThat(userList.size() , is(1));

        userDao.deleteUser(userList.get(0));

        userList = userDao.getAllUsers();

        assertThat(userList.size() , is(0));
    }

    @Test
    public void updateUser(){
        userDao.deleteAllUsers();

        user.setFirstName("Test");
        user.setLastName("Test");
        user.setId(1);
        user.setAvatar("");
        userDao.insertUser(user);

        List<User> userList = userDao.getAllUsers();
        assertThat(userList.size() , is(1));

        User user1 = userList.get(0);
        user1.setFirstName("TestUpdate");
        user1.setLastName("TestUpdate");

        userDao.updateUser(user1);

        userList = userDao.getAllUsers();
        assertThat(userList.size() , is(1));
        assertTrue(userList.get(0).getFirstName().equals("TestUpdate"));
        assertTrue(userList.get(0).getLastName().equals("TestUpdate"));
    }
}
