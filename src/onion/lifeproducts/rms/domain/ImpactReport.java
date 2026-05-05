package onion.lifeproducts.rms.application;

public record ImpactReport(
    double totalCarbonFootprint,
    double recyclabilityScore,
    String summary
) {}
