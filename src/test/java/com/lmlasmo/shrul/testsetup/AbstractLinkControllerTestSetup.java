package com.lmlasmo.shrul.testsetup;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmlasmo.shrul.dto.LinkDTO;
import com.lmlasmo.shrul.dto.register.RegisterLinkDTO;
import com.lmlasmo.shrul.service.LinkService;

public abstract class AbstractLinkControllerTestSetup extends AbstractPrefixControllerTestSetup{

	@Autowired protected LinkService linkService;

	protected LinkDTO link;

	@BeforeEach
	public void setUpLink() {
		RegisterLinkDTO rLink = new RegisterLinkDTO();
		rLink.setPrefixId(prefix.getId());
		rLink.setDestination("https://www.google.com/");

		link = linkService.save(rLink);
	}

}
