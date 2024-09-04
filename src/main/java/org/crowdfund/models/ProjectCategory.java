package org.crowdfund.models;

/**
 * enum class for Project categories
 */
public enum ProjectCategory {
    /**
     * Music project category.
     */
    MUSIC,
    /**
     * Art project category.
     */
    ART,
    /**
     * Games project category.
     */
    GAMES,
    /**
     * Dance project category.
     */
    DANCE,
    /**
     * Technology project category.
     */
    TECHNOLOGY,
    /**
     * Film project category.
     */
    FILM,
    /**
     * Comics project category.
     */
    COMICS,
    /**
     * Photography project category.
     */
    PHOTOGRAPHY;

    /**
     * The interface Project sub category.
     */
    public interface IProjectSubCategory {

    }

    /**
     * The enum Music category.
     */
    public enum MusicCategory implements IProjectSubCategory {
        /**
         * Kids music category.
         */
        KIDS,
        /**
         * Hiphop music category.
         */
        HIPHOP,
        /**
         * Jazz music category.
         */
        JAZZ,
        /**
         * Faith music category.
         */
        FAITH
    }

    /**
     * The enum Film category.
     */
    public enum FilmCategory implements IProjectSubCategory {
        /**
         * Action film category.
         */
        ACTION,
        /**
         * Animation film category.
         */
        ANIMATION,
        /**
         * Comedy film category.
         */
        COMEDY,
        /**
         * Horror film category.
         */
        HORROR,
        /**
         * Fantasy film category.
         */
        FANTASY
    }

    /**
     * The enum Games category.
     */
    public enum GamesCategory implements IProjectSubCategory {
        /**
         * Mobile games category.
         */
        MOBILE,
        /**
         * Puzzles games category.
         */
        PUZZLES,
        /**
         * Video games games category.
         */
        VIDEO_GAMES
    }

    /**
     * The enum Art category.
     */
    public enum ArtCategory implements IProjectSubCategory {
        /**
         * Digital art category.
         */
        DIGITAL,
        /**
         * Illustration art category.
         */
        ILLUSTRATION,
        /**
         * Painting art category.
         */
        PAINTING,
        /**
         * Sculpture art category.
         */
        SCULPTURE
    }

    /**
     * The enum Technology category.
     */
    public enum TechnologyCategory implements IProjectSubCategory {
        /**
         * Apps technology category.
         */
        APPS,
        /**
         * Hardware technology category.
         */
        HARDWARE,
        /**
         * Software technology category.
         */
        SOFTWARE
    }

    /**
     * The enum Dance category.
     */
    public enum DanceCategory implements IProjectSubCategory {
        /**
         * Workshops dance category.
         */
        WORKSHOPS,
        /**
         * Performances dance category.
         */
        PERFORMANCES
    }
}
