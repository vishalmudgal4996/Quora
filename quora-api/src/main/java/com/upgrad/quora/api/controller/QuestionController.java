package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.QuestionBusinessService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@RestController
@RequestMapping("/")
public class QuestionController {

    @Autowired
    private QuestionBusinessService questionBusinessService;

    @RequestMapping(method = RequestMethod.POST, path = "/question/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(@RequestHeader("authorization")final String authorization,final QuestionRequest questionRequest) throws AuthorizationFailedException {

        final QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setContent(questionRequest.getContent());

        final QuestionEntity question = questionBusinessService.createQuestion(questionEntity, authorization);

        return new ResponseEntity<QuestionResponse>(new QuestionResponse().id(question.getUuid()).status("QUESTION CREATED"), HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.GET, path =  "/question/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestions(@RequestHeader("authorization")final String authorization) throws AuthorizationFailedException {

        final List<QuestionEntity> allQuestions = questionBusinessService.getAllQuestions(authorization);

        ListIterator<QuestionEntity> quesIterator = allQuestions.listIterator();

        List<QuestionDetailsResponse> questionDetailsResponseList = new LinkedList<>();

        while (quesIterator.hasNext()){
            QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse();

            QuestionEntity question = quesIterator.next();
            questionDetailsResponse.setId(question.getUuid());
            questionDetailsResponse.setContent(question.getContent());

            questionDetailsResponseList.add(questionDetailsResponse);
        }

        return new ResponseEntity<List<QuestionDetailsResponse>>(questionDetailsResponseList,HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/question/edit/{questionId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> editQuestionContent(@PathVariable("questionId")final String questionId, @RequestHeader("authorization")final String authorization, final QuestionEditRequest editRequest) throws AuthorizationFailedException, InvalidQuestionException {

        final QuestionEntity questionEntity = questionBusinessService.editQuestionContent(questionId, editRequest.getContent(), authorization);

        return new ResponseEntity<QuestionEditResponse>(new QuestionEditResponse().id(questionEntity.getUuid()).status("QUESTION EDITED"),HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.DELETE, path = "/question/delete/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDeleteResponse> deleteQuestion(@PathVariable("questionId")final String questionId, @RequestHeader("authorization")final String authorization) throws AuthorizationFailedException, InvalidQuestionException {

        final String question = questionBusinessService.deleteQuestion(questionId, authorization);

        return new ResponseEntity<QuestionDeleteResponse>(new QuestionDeleteResponse().id(question).status("QUESTION DELETED"),HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, path = "question/all/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestionsByUser(@PathVariable("userId")final String userId, @RequestHeader("authorization")final String authorization) throws AuthorizationFailedException, UserNotFoundException {

        final List<QuestionEntity> allQuestionsByUser = questionBusinessService.getAllQuestionsByUser(userId, authorization);

        ListIterator<QuestionEntity> quesIterator = allQuestionsByUser.listIterator();

        List<QuestionDetailsResponse> questionDetailsResponseList = new LinkedList<>();

        while (quesIterator.hasNext()){
            QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse();

            QuestionEntity question = quesIterator.next();
            questionDetailsResponse.setId(question.getUuid());
            questionDetailsResponse.setContent(question.getContent());

            questionDetailsResponseList.add(questionDetailsResponse);
        }

        return new ResponseEntity<List<QuestionDetailsResponse>>(questionDetailsResponseList,HttpStatus.OK);
    }



}
