package org.azhur.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.azhur.dto.CatDto;
import org.azhur.jpaEntities.Breed;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatUserModel {
    String name;
    Date birthday;
    String breed;
    List<CatUserModel> friendsList;
    List<CatUserModel> friendOf;
    String color;
}
