package org.azhur.utils;
import org.azhur.dto.CatDto;
import org.azhur.dto.MyUserDto;
import org.azhur.dto.OwnerDto;
import org.azhur.models.CatUserModel;
import org.azhur.models.MyUser;
import org.azhur.models.OwnerUserModel;

import java.util.*;
import java.util.stream.Collectors;

import static org.azhur.utils.MappingDtoUtil.dtoToOwner;

public class MappingUserModelsUtil {

    public static CatDto toCatDto(CatUserModel userModel) {
        if (userModel == null) {
            return null;
        }

        CatDto catDto = CatDto.builder()
                .name(userModel.getName())
                .birthday(userModel.getBirthday())
                .breed(userModel.getBreed())
                .color(userModel.getColor())
                .build();

        catDto.setFriendsList(
                Optional.ofNullable(userModel.getFriendsList())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(MappingUserModelsUtil::toCatDtoNoFriends)
                        .collect(Collectors.toList())
        );

        catDto.setFriendOf(
                Optional.ofNullable(userModel.getFriendOf())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(MappingUserModelsUtil::toCatDtoNoFriends)
                        .collect(Collectors.toList())
        );

        return catDto;
    }
    private static CatDto toCatDtoNoFriends(CatUserModel userModel) {
        if (userModel == null) {
            return null;
        }

        return CatDto.builder()
                .name(userModel.getName())
                .birthday(userModel.getBirthday())
                .breed(userModel.getBreed())
                .color(userModel.getColor())
                .friendsList(Collections.emptyList())
                .friendOf(Collections.emptyList())
                .build();
    }


    public static CatUserModel toCatUserModel(CatDto dto) {
        if (dto == null) {
            return null;
        }


        CatUserModel userModel = CatUserModel.builder()
                .name(dto.getName())
                .birthday(dto.getBirthday())
                .breed(dto.getBreed())
                .color(dto.getColor())
                .build();


        userModel.setFriendsList(
                Optional.ofNullable(dto.getFriendsList())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(MappingUserModelsUtil::toCatUserModelNoFriends)
                        .collect(Collectors.toList())
        );

        userModel.setFriendOf(
                Optional.ofNullable(dto.getFriendOf())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(MappingUserModelsUtil::toCatUserModelNoFriends)
                        .collect(Collectors.toList())
        );

        return userModel;
    }

    private static CatUserModel toCatUserModelNoFriends(CatDto dto) {
        if (dto == null) {
            return null;
        }

        return CatUserModel.builder()
                .name(dto.getName())
                .birthday(dto.getBirthday())
                .breed(dto.getBreed())
                .color(dto.getColor())
                .friendsList(Collections.emptyList())
                .friendOf(Collections.emptyList())
                .build();
    }

    public static OwnerDto toOwnerDto(OwnerUserModel userModel) {
        if (userModel == null) {
            return null;
        }

        return OwnerDto.builder()
                .name(userModel.getName())
                .lastname(userModel.getLastname())
                .birthday(userModel.getBirthday())
                .passportNumber(userModel.getPassportNumber())
                .catsList(new ArrayList<>())
                .build();
    }

    public static OwnerUserModel toOwnerUserModel(OwnerDto dto) {
        if (dto == null) {
            return null;
        }

        return new OwnerUserModel(
                dto.getName(),
                dto.getLastname(),
                dto.getBirthday(),
                dto.getPassportNumber()
        );
    }

    public static MyUserDto userToDto(MyUser user) {
        if (user == null) {
            return null;
        }
        return new MyUserDto(
                user.getUsername(), // email
                user.getPassword(),
                user.getRole(),
                MappingDtoUtil.ownerToDto(user.getOwner())
        );
    }

    public static MyUser dtoToUser(MyUserDto userDto) {
        if (userDto == null) {
            return null;
        }
        MyUser user = new MyUser(
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole(),
                MappingDtoUtil.dtoToOwner(userDto.getOwnerDto())
        );
        return user;
    }

}
