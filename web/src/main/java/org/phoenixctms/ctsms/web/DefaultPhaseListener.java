package org.phoenixctms.ctsms.web;

/*
 * Copyright 2012 OmniFaces.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * Default implementation for the PhaseListener interface.
 *
 * @author Arjan Tijms
 */
public abstract class DefaultPhaseListener implements PhaseListener {

	private static final long serialVersionUID = -7252366571645029385L;
	private PhaseId phaseId;

	public DefaultPhaseListener(PhaseId phaseId) {
		this.phaseId = phaseId;
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		// NOOP.
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// NOOP.
	}

	@Override
	public PhaseId getPhaseId() {
		return phaseId;
	}
}