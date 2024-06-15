package org.azhur.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerUserModel {
    private String name;
    private String lastname;
    private Date birthday;
    private String passportNumber;
}
