package com.lmlasmo.shrul.testsetup;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmlasmo.shrul.dto.PrefixDTO;
import com.lmlasmo.shrul.dto.register.RegisterPrefixDTO;
import com.lmlasmo.shrul.service.PrefixService;

public abstract class AbstractPrefixControllerTestSetup extends AbstractUserControllerTestSetup{

	@Autowired private PrefixService prefixService;

	protected PrefixDTO prefix;

	@BeforeEach
	void setUpPrefix() throws Exception {
		RegisterPrefixDTO rPrefix = new RegisterPrefixDTO();
		rPrefix.setPrefix("prefix");
		prefix = prefixService.save(rPrefix, user.getId());
		user.getPrefixes().add(prefixService.getRepository().findById(prefix.getId()).get());
	}

}