package com.example.nurecareercenterua.exception.model;

import java.util.Date;

public record HttpResponse(String name, int code, String message, Date time) {
}
