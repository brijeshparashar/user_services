package com.demo.user.mapper;

import com.demo.user.dao.entity.AddressEntity;
import com.demo.user.dao.entity.UserEntity;
import com.demo.user.model.UserAddress;
import com.demo.user.model.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper class to map UserDetails object to UserEntity objects.
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    void mapUserDetailsToUserEntity(UserDetails userDetails, @MappingTarget UserEntity userEntity);

    void mapUserAddressToAddressEntity(UserAddress userAddress, @MappingTarget AddressEntity addressEntity);
}
