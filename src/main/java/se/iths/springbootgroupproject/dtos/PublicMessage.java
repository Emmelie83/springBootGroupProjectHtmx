package se.iths.springbootgroupproject.dtos;

import se.iths.springbootgroupproject.entities.User;

import java.time.LocalDate;

public record PublicMessage(LocalDate createdDate, String messageTitle, String messageBody, User user) {
}
