package org.crowdfund.models;

/**
 * enum class for Project categories
 */
public enum ProjectCategory {
    MUSIC,
    ART,
    GAMES,
    DANCE,
    TECHNOLOGY,
    FILM,
    COMICS,
    PHOTOGRAPHY;

    public interface IProjectSubCategory {

    }

    public enum MusicCategory implements IProjectSubCategory {
        KIDS,
        HIPHOP,
        JAZZ,
        FAITH
    }

    public enum FilmCategory implements IProjectSubCategory {
        ACTION,
        ANIMATION,
        COMEDY,
        HORROR,
        FANTASY
    }

    public enum GamesCategory implements IProjectSubCategory {
        MOBILE,
        PUZZLES,
        VIDEO_GAMES
    }

    public enum ArtCategory implements IProjectSubCategory {
        DIGITAL,
        ILLUSTRATION,
        PAINTING,
        SCULPTURE
    }

    public enum TechnologyCategory implements IProjectSubCategory {
        APPS,
        HARDWARE,
        SOFTWARE
    }

    public enum DanceCategory implements IProjectSubCategory {
        WORKSHOPS,
        PERFORMANCES
    }
}
