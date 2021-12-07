package com.example.denverbroncos;

import java.util.HashMap;

public class DenverStadiumConstants {

    // MockAPI Dashboard URL : https://mockapi.io/projects/61adbd67d228a9001703aeed

    // UUID ---> Specific to Denver Stadium

    // Beacon one
    // UUID - <someUUID>
    // Major - Section 203
    // Minor - Each Seat

    // Beacon two
    // UUID - <someUUID>
    // Major - Section 422
    // Minor - Each Seat

    static final String DENVER_STADIUM_UUID = "12345678-9012-3456-7890-123456789012";
    static final String SECTION_203 = "203";
    static final String SECTION_422 = "422";

    static final HashMap<String, String> BEACON_SECTION_MAPPER  = new HashMap<String, String>() {{
        put("10", SECTION_203);
        put("9999", SECTION_422);
    }};


    static final String OFFER_URL = "https://61adbd67d228a9001703aeec.mockapi.io/stadium/offer";
    // return the following from the URL
    // {
    //   "section":"203",
    //   "product":"chickfila"     // can be either of the two below values
    //   }
    static final String OFFER_PRODUCT_COKE = "coke";
    static final String OFFER_PRODUCT_CHICKFILA = "chickfila";
}
