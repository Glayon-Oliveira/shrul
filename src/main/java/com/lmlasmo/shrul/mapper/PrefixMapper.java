package com.lmlasmo.shrul.mapper;

import java.math.BigInteger;

import org.mapstruct.Mapper;

import com.lmlasmo.shrul.dto.PrefixDTO;
import com.lmlasmo.shrul.dto.register.RegisterPrefixDTO;
import com.lmlasmo.shrul.model.Prefix;

@Mapper(componentModel = "spring")
public interface PrefixMapper {

	public Prefix dtoToPrefix(RegisterPrefixDTO dto);

	public PrefixDTO prefixToDTO(Prefix prefix);

	public default Prefix idToPrefix(BigInteger id) {
		return new Prefix(id);
	}

	public default BigInteger prefixToId(Prefix prefix) {
		return prefix.getId();
	}

}
