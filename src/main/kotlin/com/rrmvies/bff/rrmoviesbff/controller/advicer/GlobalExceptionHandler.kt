package com.rrmvies.bff.rrmoviesbff.controller.advicer

import com.rrmvies.bff.rrmoviesbff.controller.exception.ResourceNotFoundException
import com.rrmvies.bff.rrmoviesbff.dto.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice // Esta anotação ativa o tratamento de exceções global
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class )
    fun handleResourceNotFoundException(
        ex: ResourceNotFoundException,
        request: WebRequest
    ): ResponseEntity<ErrorResponseDto> {
        val errorDetails = ErrorResponseDto(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            message = ex.message, // A mensagem que passamos ao criar a exceção
            path = request.getDescription(false).substringAfter("uri=")
        )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    // Um handler genérico para qualquer outra exceção não tratada
    // Isso evita que o usuário final veja um stack trace completo
    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ErrorResponseDto> {
        val errorDetails = ErrorResponseDto(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = "Ocorreu um erro inesperado. Tente novamente mais tarde.", // Mensagem genérica
            path = request.getDescription(false).substringAfter("uri=")
        )
        // É uma boa prática logar a exceção real no servidor
        // log.error("Exceção não tratada: ", ex)
        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
