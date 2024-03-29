/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.wft.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wft.service.services.ITeamRepositoryService;
import com.wft.service.services.ITournamentService;
import com.wft.service.services.IUserService;
import com.wft.ui.welcome.WFTWelcomeWindow;
import com.wft.util.BaseApplication;

/**
 * The Application's "main" class
 */
@Component(value = "webFootTournamentApplication")
@Scope(value = "prototype")
@SuppressWarnings("serial")
public class WebFootTournamentApplication extends BaseApplication implements ApplicationServiceProvider {	
	@Autowired
	private transient WFTWelcomeWindow wftWelcomeWindow;

	@Autowired
	private transient ITeamRepositoryService iTeamRepositoryService;
	
	@Autowired
	private transient IUserService iUserService;

	@Autowired
	private transient ITournamentService iTournamentService;

	public IUserService getUserService() {
		return iUserService;
	}

	public ITournamentService getTournamentService() {
		return iTournamentService;
	}

	@Override
	public void init() {
		setMainWindow(wftWelcomeWindow);
	}


	public WFTWelcomeWindow getWftWelcomeWindow() {
		return wftWelcomeWindow;
	}

}
