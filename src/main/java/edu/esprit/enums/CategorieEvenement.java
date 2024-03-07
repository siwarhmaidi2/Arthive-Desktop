package edu.esprit.enums;

public enum CategorieEvenement {
        PEINTURE("Peinture"),
        SCULPTURE("Sculpture"),
        MUSIQUE("Musique"),
        DANSE("Danse"),
        CINEMA("Cinéma"),
        THEATRE("Théâtre"),
        PHOTOGRAPHIE("Photographie"),
        ART_NUMERIQUE("Art Numérique"),
        ART_URBAIN("Art Urbain"),
        LITTERATURE("Littérature");

        private final String libelle;

        CategorieEvenement(String libelle) {
            this.libelle = libelle;
        }

        // Getter pour le libellé, si nécessaire
        public String getLibelle() {
            return libelle;
        }
    }



