package org.azhur.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerDto {
    Integer id;
    String name;
    String lastname;
    Date birthday;
    String passportNumber;
    List<CatDto> catsList;

    public void addCat(CatDto cat) {
        if (catsList != null) {
            catsList.add(cat);
        } else {
            catsList = new ArrayList<>();
            catsList.add(cat);
        }
    }
}

