/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * OpenMRS is also distributed under the terms of the Healthcare Disclaimer located at
 * http://openmrs.org/license.
 *
 * <p>Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS graphic logo is a
 * trademark of OpenMRS Inc.
 */
package org.openmrs.module.biometric.api.observability;

import java.time.Duration;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stopwatch {
  private static final Logger LOGGER = LoggerFactory.getLogger(Stopwatch.class);

  private final String name;
  private Instant startTime;

  public Stopwatch(String name) {
    this.name = name;
  }

  public Stopwatch start() {
    this.startTime = Instant.now();
    return this;
  }

  public void stopAndLog() {
    final Instant endTime = Instant.now();
    final Duration duration = Duration.between(startTime, endTime);

    LOGGER.warn(
        "[{};{}] : {} : {} ms", new Object[] {startTime, endTime, name, duration.toMillis()});
  }
}
