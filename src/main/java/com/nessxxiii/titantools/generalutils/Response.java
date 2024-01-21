package com.nessxxiii.titantools.generalutils;

public record Response<T>(T value, String error) {

    public static <T> Response<T> success(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null for a successful response.");
        }
        return new Response<>(value, null);
    }

    public static <T> Response<T> failure(String error) {
        if (error == null || error.isBlank()) {
            throw new IllegalArgumentException("Error must not be null or blank for a failure response.");
        }
        return new Response<>(null, error);
    }

    public boolean isSuccess() {
        return error == null;
    }
}

