package org.sonarsource.plugins.energydebt.measures;

import com.sun.tools.javac.util.Pair;
import org.sonar.api.ce.measure.Issue;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.sonar.api.ce.measure.Component.Type.FILE;
import static org.sonarsource.plugins.energydebt.measures.EnergyMetrics.*;

public class ComputeEnergyDebt implements MeasureComputer {
    private HashMap<String, Pair<Double, Double>> rules = new HashMap<String, Pair<Double, Double>>(){
        {
            // Rule key, (maximum debt, minimum debt)
            put("DrawAllocation", new Pair<>(0.158d, 0.036d));
            put("HashMapUsage", new Pair<>(0.229d, 0.28d));
            put("ExcessiveMethodCalls", new Pair<>(9.529d, 0.557d));
            put("MemberIgnoringMethod", new Pair<>(7.844d, 0.088d));
            put("Recycle", new Pair<>(0.533d, 0.015d));
            put("ViewHolder", new Pair<>(2.105d, 0.892d));
            put("WakeLock", new Pair<>(0.194d, 0.01d));
        }
    };

    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext context) {
        return context.newDefinitionBuilder()
                .setOutputMetrics(ENERGY_SMELLS.key(), MAX_ENERGY_DEBT.key(), MIN_ENERGY_DEBT.key())
                .build();
    }

    @Override
    public void compute(MeasureComputerContext context) {
        HashMap<String, Integer> counters = new HashMap<>();

        int energy_smells = 0;
        double totalMaxED = 0;
        double totalMinED = 0;

        if (context.getComponent().getType().equals(FILE)){
            List<? extends Issue> fileIssues = context.getIssues();
            for(Issue issue : fileIssues){
                String key = issue.ruleKey().rule();
                if(rules.containsKey(key) && issue.ruleKey().repository().equals("energy-debt-java")) {
                    energy_smells++;
                    counters.put(key, counters.getOrDefault(key, 0) + 1);
                    Pair<Double, Double> values = rules.get(key);
                    totalMaxED += values.fst;
                    totalMinED += values.snd;
                }
            }
            context.addMeasure(ENERGY_SMELLS.key(), energy_smells);
            context.addMeasure(MAX_ENERGY_DEBT.key(), totalMaxED);
            context.addMeasure(MIN_ENERGY_DEBT.key(), totalMinED);
            return;
        }

        for (Measure measure : context.getChildrenMeasures(ENERGY_SMELLS.key())) {
            energy_smells += measure.getIntValue();
        }
        context.addMeasure(ENERGY_SMELLS.key(), energy_smells);

        for (Measure measure : context.getChildrenMeasures(MAX_ENERGY_DEBT.key())) {
            totalMaxED += measure.getDoubleValue();
        }
        context.addMeasure(MAX_ENERGY_DEBT.key(), totalMaxED);


        for (Measure measure : context.getChildrenMeasures(MIN_ENERGY_DEBT.key())) {
            totalMinED += measure.getDoubleValue();
        }
        context.addMeasure(MIN_ENERGY_DEBT.key(), totalMinED);


    }
}
