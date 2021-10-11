package com.restdoc.restassured;

import org.springframework.stereotype.Service;

@Service
public class NoteService {
        public Note getNote(){
            return  new Note();
        }
}
