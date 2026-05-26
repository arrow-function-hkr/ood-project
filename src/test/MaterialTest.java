package test;

import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.RecyclingCategory;
import onion.lifeproducts.rms.domain.RecyclingGuidance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaterialTest {

    private RecyclingGuidance guidance;
    private Material material;

    @BeforeEach
    void setUp() {
        guidance  = new RecyclingGuidance("Place in recycling bin");
        material  = new Material("Plastic", 0.8f, 3.5f, RecyclingCategory.RECYCLABLE, guidance);
    }

    // --- setName ---

    @Test
    @DisplayName("setName should accept a valid name and return true")
    void shouldAcceptValidNameAndReturnTrue() {
        // Act
        boolean result = material.setName("Recycled Plastic");

        // Assert
        assertTrue(result);
        assertEquals("Recycled Plastic", material.getName());
    }

    @Test
    @DisplayName("setName should reject null and return false")
    void shouldRejectNullNameAndReturnFalse() {
        // Arrange
        String originalName = material.getName();

        // Act
        boolean result = material.setName(null);

        // Assert
        assertFalse(result);
        assertEquals(originalName, material.getName()); // name unchanged
    }

    @Test
    @DisplayName("setName should reject a blank string and return false")
    void shouldRejectBlankStringNameAndReturnFalse() {
        // Arrange
        String originalName = material.getName();

        // Act
        boolean result = material.setName("   ");

        // Assert
        assertFalse(result);
        assertEquals(originalName, material.getName()); // name unchanged
    }

    @Test
    @DisplayName("setName should reject an empty string and return false")
    void shouldRejectEmptyStringNameAndReturnFalse() {
        // Arrange
        String originalName = material.getName();

        // Act
        boolean result = material.setName("");

        // Assert
        assertFalse(result);
        assertEquals(originalName, material.getName()); // name unchanged
    }

    // --- constructor / getters ---

    @Test
    @DisplayName("getName should return the name passed at construction")
    void shouldReturnNamePassedAtConstruction() {
        assertEquals("Plastic", material.getName());
    }

    @Test
    @DisplayName("getEmissionFactor should return the emission factor passed at construction")
    void shouldReturnEmissionFactorPassedAtConstruction() {
        assertEquals(3.5f, material.getEmissionFactor(), 0.001f);
    }

    @Test
    @DisplayName("getRecycleRate should return the recycle rate passed at construction")
    void shouldReturnRecycleRatePassedAtConstruction() {
        assertEquals(0.8f, material.getRecycleRate(), 0.001f);
    }

    @Test
    @DisplayName("getRecyclingCategory should return the category passed at construction")
    void shouldReturnRecyclingCategoryPassedAtConstruction() {
        assertEquals(RecyclingCategory.RECYCLABLE, material.getRecyclingCategory());
    }

    @Test
    @DisplayName("getRecyclingGuidance should return the guidance object passed at construction")
    void shouldReturnRecyclingGuidancePassedAtConstruction() {
        assertEquals(guidance, material.getRecyclingGuidance());
    }
}
