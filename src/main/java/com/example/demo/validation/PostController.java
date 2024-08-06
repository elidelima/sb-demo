package com.example.demo.validation;

import com.example.demo.exceptions.CustomBaseException;
import com.example.demo.exceptions.SimpleResponse;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @PostMapping("/new-post")
    public ResponseEntity saveNewPost(@Valid @RequestBody Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new CustomBaseException(
                    HttpStatus.BAD_REQUEST,
                    new SimpleResponse(errorMessages.toString())
            );
        }
        return ResponseEntity.ok().build();
    }
}
