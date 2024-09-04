package org.crowdfund.utils;

import lombok.NonNull;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.models.ProjectCategory;

/**
 * The type Project category util.
 */
public final class ProjectCategoryUtil {

    private ProjectCategoryUtil() {
    }


    /**
     * Get project category project category.
     *
     * @param projectStatus the project status
     * @return project category
     */
    public static ProjectCategory getProjectCategory(@NonNull final String projectStatus){
        try{
            return ProjectCategory.valueOf(projectStatus);
        }catch (final IllegalArgumentException e){
            throw new InvalidRequestException("Invalid category present in request :" + projectStatus);
        }
    }

    /**
     * Gets project sub category.
     *
     * @param projectCategory   the project category
     * @param subCategoryString the sub category string
     * @return project sub category
     */
    public static ProjectCategory.IProjectSubCategory getProjectSubCategory(
            @NonNull final ProjectCategory projectCategory,
            @NonNull final String subCategoryString) {
        try {
            return switch (projectCategory) {
                case MUSIC -> ProjectCategory.MusicCategory.valueOf(subCategoryString);
                case ART -> ProjectCategory.ArtCategory.valueOf(subCategoryString);
                case FILM -> ProjectCategory.FilmCategory.valueOf(subCategoryString);
                case DANCE -> ProjectCategory.DanceCategory.valueOf(subCategoryString);
                case TECHNOLOGY -> ProjectCategory.TechnologyCategory.valueOf(subCategoryString);
                case GAMES -> ProjectCategory.GamesCategory.valueOf(subCategoryString);
                default -> throw new InvalidRequestException("UnSupported project category present in request");
            };
        } catch (final IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid project sub category present in request");
        }
    }
}
