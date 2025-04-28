package com.lmlasmo.shrul.mapper;

import java.math.BigInteger;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lmlasmo.shrul.dto.LinkDTO;
import com.lmlasmo.shrul.dto.register.RegisterLinkDTO;
import com.lmlasmo.shrul.model.Link;
import com.lmlasmo.shrul.model.Prefix;

@Mapper(componentModel = "spring")
public interface LinkMapper {

	@Mapping(target = "prefix", source = "prefixId")
	public Link dtoToLink(RegisterLinkDTO link);

	@Mapping(target = "prefixId", source = "prefix")
	public LinkDTO linkToDTO(Link link);

	public default Prefix idToPrefix(BigInteger id) {
		return new Prefix(id);
	}

	public default BigInteger prefixToId(Prefix prefix) {
		return prefix.getId();
	}

}
