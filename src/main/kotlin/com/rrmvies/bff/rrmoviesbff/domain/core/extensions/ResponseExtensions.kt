package com.rrmvies.bff.rrmoviesbff.domain.core.extensions

import org.springframework.http.ResponseEntity

fun <T> T?.ok(): ResponseEntity<T> = ResponseEntity.ok(this)
