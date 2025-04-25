package com.lmlasmo.shrul.mapper;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lmlasmo.shrul.dto.UrlAccessDTO;
import com.lmlasmo.shrul.model.Link;
import com.lmlasmo.shrul.model.UrlAccess;

@Mapper(componentModel = "spring")
public interface UrlAccessMapper {
	
	@Mapping(target = "link", source = "linkId")
	public UrlAccess dtoToAccess(UrlAccessDTO access);

	@Mapping(target = "linkId", source = "link")
	public UrlAccessDTO accessToDTO(UrlAccess access);
	
	public default Link idToLink(String id) {
		return new Link(id);
	}
	
	public default String linkToId(Link link) {
		return link.getId();
	}
	
	public default byte[] stringToIp(String ip) {		
		try {
            return InetAddress.getByName(ip).getAddress();
        } catch (UnknownHostException e) {
            return new byte[4];
        }
	}
	
	public default String ipToString(byte[] ip) {
		try {
            return InetAddress.getByAddress(ip).getHostAddress();
        } catch (UnknownHostException e) {
            return "0.0.0.0";
        }
	}	
	
}
