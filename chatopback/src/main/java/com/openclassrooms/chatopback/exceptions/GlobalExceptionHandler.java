package com.openclassrooms.chatopback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleSecurityException(Exception exception) {

		exception.printStackTrace();

		if (exception instanceof BadCredentialsException) {

			return new ResponseEntity<String>("{}", HttpStatus.UNAUTHORIZED);
		}

		else if (exception instanceof MalformedJwtException) {

			return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
		}

		else if (exception instanceof AccountStatusException) {

			return new ResponseEntity<String>("{\"message\":\"The account is locked\"}", HttpStatus.FORBIDDEN);
		}

		else if (exception instanceof AccessDeniedException) {

			return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
		}

		else if (exception instanceof MalformedJwtException) {

			return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
		}

		else if (exception instanceof SignatureException) {

			return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
		}

		else if (exception instanceof ExpiredJwtException) {

			return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<String>("{\"message\":\"" + exception.getMessage() + "\"}",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
