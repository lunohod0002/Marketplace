package com.example.demo.Dto.comment;

import java.time.LocalDate;

public record CommentDto (

    String title,
    String text,
    LocalDate dateArrival,
    int mark,
    String username)
{
}
