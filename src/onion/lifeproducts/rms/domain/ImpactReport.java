package org.dom;

public record ImpactReport(
    double totalCarbonFootprint,
    double recyclabilityScore,
    String summary
) {}