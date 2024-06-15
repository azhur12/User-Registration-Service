package org.azhur.utils;

import org.azhur.builder.CatBuilder;
import org.azhur.dto.CatDto;
import org.azhur.dto.MyUserDto;
import org.azhur.dto.OwnerDto;
import org.azhur.jpaEntities.Breed;
import org.azhur.jpaEntities.Cat;
import org.azhur.jpaEntities.MyUserJpa;
import org.azhur.jpaEntities.Owner;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MappingDtoUtil {
    public static OwnerDto ownerToDto(Owner owner) {
        if (owner == null) {
            return null;
        }
        return OwnerDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .lastname(owner.getLastName())
                .birthday(owner.getBirthday())
                .passportNumber(owner.getPassportNumber())
                .catsList(owner.getCatsList() != null ?
                        owner.getCatsList().stream()
                        .map(MappingDtoUtil::recursiveCatToDto)
                        .collect(Collectors.toList()) : Collections.emptyList()).build();

    }

    private static OwnerDto recursiveOwnerToDto(Owner owner) {
        if (owner == null) {
            return null;
        }
        return OwnerDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .lastname(owner.getLastName())
                .birthday(owner.getBirthday())
                .passportNumber(owner.getPassportNumber()).build();
    }

    public static Owner dtoToOwner(OwnerDto ownerDTO) {
        if (ownerDTO == null) return null;
        return Owner.builder()
                .id(ownerDTO.getId())
                .name(ownerDTO.getName())
                .lastName(ownerDTO.getLastname())
                .birthday(ownerDTO.getBirthday())
                .passportNumber(ownerDTO.getPassportNumber())
                .catsList(Optional.ofNullable(ownerDTO.getCatsList())
                        .orElseGet(Collections::emptyList)
                        .stream()
                        .map(MappingDtoUtil::dtoToCat)
                        .collect(Collectors.toList())).build();
    }

    public static CatDto catToDto(Cat cat) {
        if (cat == null) return null;
        return CatDto.builder()
                .id(cat.getId())
                .name(cat.getName())
                .birthday(cat.getBirthday())
                .breed(cat.getBreed().getBreedName())
                .friendsList(cat.getFriendsList() != null ?
                        cat.getFriendsList().stream()
                        .map(MappingDtoUtil::recursiveCatToDto)
                        .collect(Collectors.toList()) : Collections.emptyList())
                .friendOf(cat.getFriendOfList() != null ?
                        cat.getFriendOfList().stream()
                        .map(MappingDtoUtil::recursiveCatToDto)
                        .collect(Collectors.toList()) : Collections.emptyList())
                .owner(recursiveOwnerToDto(cat.getOwner()))
                .color(cat.getColor())
                .build();
    }

    private static CatDto recursiveCatToDto(Cat cat) {
        if (cat == null) return null;
        return CatDto.builder()
                .id(cat.getId())
                .name(cat.getName())
                .birthday(cat.getBirthday())
                .breed(cat.getBreed().getBreedName())
                .color(cat.getColor())
                .build();
    }

    public static Cat dtoToCat(CatDto catDto) {
        if (catDto == null) return null;
        return new CatBuilder()
                .id(catDto.getId())
                .name(catDto.getName())
                .birthday(catDto.getBirthday())
                .breed(new Breed(catDto.getBreed()))
                .color(catDto.getColor())
                .friendsList(catDto.getFriendsList() != null ?
                        catDto.getFriendsList().stream()
                        .map(MappingDtoUtil::dtoToCat)
                        .collect(Collectors.toList()) : Collections.emptyList())
                .friendOfList(catDto.getFriendOf() != null ?
                        catDto.getFriendOf().stream()
                        .map(MappingDtoUtil::dtoToCat)
                        .collect(Collectors.toList()) : Collections.emptyList())
                .owner(dtoToOwner(catDto.getOwner()))
                .build();
    }

    public static Owner updateOwner(Owner owner, OwnerDto ownerDTO) {
        if (ownerDTO != null) {
            owner.setName(ownerDTO.getName());
            owner.setLastName(ownerDTO.getLastname());
            owner.setPassportNumber(ownerDTO.getPassportNumber());
            Optional.ofNullable(ownerDTO.getCatsList())
                    .ifPresent(catsList -> owner.setCatsList(
                            catsList.stream()
                                    .map(MappingDtoUtil::dtoToCat)
                                    .collect(Collectors.toList())
                    ));
        }
        return owner;

    }

    public static Cat updateCat(Cat cat, CatDto catDTO) {
        Optional.ofNullable(catDTO)
                .ifPresent(dto -> {
                    Optional.ofNullable(dto.getName()).ifPresent(cat::setName);
                    Optional.ofNullable(dto.getBirthday()).ifPresent(cat::setBirthday);
                    Optional.ofNullable(dto.getColor()).ifPresent(cat::setColor);
                    Optional.ofNullable(dto.getFriendsList())
                            .ifPresent(friends -> cat.setFriendsList(
                                    friends.stream().map(MappingDtoUtil::dtoToCat).collect(Collectors.toList())));
                    Optional.ofNullable(dto.getFriendOf())
                            .ifPresent(friendOf -> cat.setFriendOfList(
                                    friendOf.stream().map(MappingDtoUtil::dtoToCat).collect(Collectors.toList())));
                    Optional.ofNullable(dto.getOwner()).map(MappingDtoUtil::dtoToOwner).ifPresent(cat::setOwner);
                });
        return cat;
    }

    public static MyUserDto userToDto(MyUserJpa user) {
        if (user == null) {
            return null;
        }
        return new MyUserDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                MappingDtoUtil.ownerToDto(user.getOwner())
        );
    }

    public static MyUserJpa dtoToUser(MyUserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return new MyUserJpa(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole(),
                MappingDtoUtil.dtoToOwner(userDto.getOwnerDto())
        );
    }

}
