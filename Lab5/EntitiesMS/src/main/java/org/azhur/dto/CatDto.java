package org.azhur.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.azhur.jpaEntities.Breed;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatDto {
    int id;
    String name;
    Date birthday;
    String breed;
    List<CatDto> friendsList;
    List<CatDto> friendOf;
    OwnerDto owner;
    String color;

    public void addFriend(CatDto friend) {
        friendsList.add(friend);
        friendOf.add(friend);
    }
}
