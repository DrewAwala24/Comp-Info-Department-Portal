package com.department.portal.model;

/**
 * Kenyan TVET / CDACC qualification levels offered by the
 * Computing and Informatics Department.
 */
public enum TvetLevel {
    LEVEL_6_DIPLOMA("Diploma (Level 6)"),
    LEVEL_5_CRAFT("Craft Certificate (Level 5)"),
    LEVEL_4_ARTISAN("Artisan (Level 4)");

    private final String displayName;

    TvetLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
