package com.tahir.where_did_my_money_go.common.exception;

import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private ErrorResponse buildError(
                        HttpStatus status,
                        String message,
                        HttpServletRequest request) {
                return ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(status.value())
                                .error(status.getReasonPhrase())
                                .message(message)
                                .path(request.getRequestURI())
                                .build();
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(
                        ResourceNotFoundException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request));
        }

        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<ErrorResponse> handleUnauthorized(
                        UnauthorizedException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(buildError(HttpStatus.FORBIDDEN, ex.getMessage(), request));
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ErrorResponse> handleAuth(
                        AuthenticationException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(buildError(HttpStatus.UNAUTHORIZED, ex.getMessage(), request));
        }

        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<ErrorResponse> handleDuplicate(
                        DuplicateResourceException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(buildError(HttpStatus.CONFLICT, ex.getMessage(), request));
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorResponse> handleBadRequest(
                        BadRequestException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
        }

        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ErrorResponse> handleBusiness(
                        BusinessException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidation(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                Map<String, String> errors = new HashMap<>();

                ex.getBindingResult().getFieldErrors()
                                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

                ErrorResponse response = buildError(
                                HttpStatus.BAD_REQUEST,
                                "Validation failed",
                                request);

                response.setValidationErrors(errors);

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ErrorResponse> handleConstraintViolation(
                        ConstraintViolationException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErrorResponse> handleDatabase(
                        DataIntegrityViolationException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(buildError(HttpStatus.CONFLICT, "Database constraint violation", request));
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ErrorResponse> handleBadCredentials(
                        BadCredentialsException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(buildError(HttpStatus.UNAUTHORIZED, "Invalid credentials", request));
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorResponse> handleAccessDenied(
                        AccessDeniedException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(buildError(HttpStatus.FORBIDDEN, "Access denied", request));
        }

        @ExceptionHandler(EmailNotVerifiedException.class)
        public ResponseEntity<ErrorResponse> handleEmailNotVerified(
                        EmailNotVerifiedException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(buildError(HttpStatus.FORBIDDEN, "Email not verified", request));
        }

        @ExceptionHandler(InvalidTokenException.class)
        public ResponseEntity<ErrorResponse> handleInvalidToken(
                        InvalidTokenException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(buildError(HttpStatus.FORBIDDEN, "Invalid token", request));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneric(
                        Exception ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request));
        }
}