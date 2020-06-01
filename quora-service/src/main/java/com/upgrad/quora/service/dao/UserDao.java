package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity signUp(UserEntity userEntity){
        entityManager.persist(userEntity);
        return userEntity;
    }


    public UserEntity userByUsername(String username){
        try{
            return entityManager.createNamedQuery("userByUsername",UserEntity.class).setParameter("username",username)
                    .getSingleResult();
        }catch (NoResultException nre){
            return null;
        }
    }


    public UserEntity userByEmail(String email){
        try{
            return entityManager.createNamedQuery("userByEmail",UserEntity.class).setParameter("email",email)
                    .getSingleResult();
        }catch (NoResultException nre){
            return null;
        }
    }


    public UserAuthTokenEntity createAuthToken(UserAuthTokenEntity userAuthTokenEntity){
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }


    public UserAuthTokenEntity getUserAuthToken(String accessToken){
        try{
            return entityManager.createNamedQuery("userAuthTokenByAccessToken",UserAuthTokenEntity.class)
                    .setParameter("accessToken",accessToken).getSingleResult();

        }catch (NoResultException nre){
            return null;
        }
    }


    public void updateUser(UserEntity userEntity){
            entityManager.merge(userEntity);
    }

}
