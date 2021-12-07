package com.example.denverbroncos;

public class BeaconStadiumSectionMapper {
    static String map(final String originalMajor) {
        final String correctedValue = DenverStadiumConstants.BEACON_SECTION_MAPPER.get(originalMajor);
        if (correctedValue != null) {
            return correctedValue;
        }
        return originalMajor;
    }
}
