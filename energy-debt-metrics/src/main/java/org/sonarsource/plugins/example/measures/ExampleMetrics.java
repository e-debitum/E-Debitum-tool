/*
 * Example Plugin for SonarQube
 * Copyright (C) 2009-2020 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.plugins.example.measures;

import java.util.List;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import static java.util.Arrays.asList;

public class ExampleMetrics implements Metrics {

  public static final Metric<Integer> ENERGY_SMELLS = new Metric.Builder("energy_smells", "Energy Smells", Metric.ValueType.INT)
          .setDescription("Number of energy smells in the code")
          .setDirection(Metric.DIRECTION_NONE)
          .setQualitative(false)
          .setDomain(CoreMetrics.DOMAIN_MAINTAINABILITY)
          .create();

  public static final Metric<Double> MAX_ENERGY_DEBT = new Metric.Builder("max_energy_debt", "Maximum Energy Debt", Metric.ValueType.FLOAT)
          .setDescription("Worst case of excess energy used to execute a section of code")
          .setDirection(Metric.DIRECTION_NONE)
          .setQualitative(false)
          .setDomain(CoreMetrics.DOMAIN_MAINTAINABILITY)
          .create();

  public static final Metric<Double> MIN_ENERGY_DEBT = new Metric.Builder("min_energy_debt", "Minimum Energy Debt", Metric.ValueType.FLOAT)
          .setDescription("Best case of excess energy used to execute a section of code")
          .setDirection(Metric.DIRECTION_NONE)
          .setQualitative(false)
          .setDomain(CoreMetrics.DOMAIN_MAINTAINABILITY)
          .create();


  @Override
  public List<Metric> getMetrics() {
    return asList(ENERGY_SMELLS, MAX_ENERGY_DEBT, MIN_ENERGY_DEBT);
  }
}
