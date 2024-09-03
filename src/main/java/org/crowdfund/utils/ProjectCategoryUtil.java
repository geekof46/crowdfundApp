package org.crowdfund.utils;

import lombok.NonNull;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.models.ProjectCategory;

public final class ProjectCategoryUtil {

    private ProjectCategoryUtil() {
    }


    /**
     *
     * @param projectStatus
     * @return
     */
    public static ProjectCategory getProjectCategory(@NonNull final String projectStatus){
        try{
            return ProjectCategory.valueOf(projectStatus);
        }catch (final IllegalArgumentException e){
            throw new InvalidRequestException("Invalid category present in request :" + projectStatus);
        }
    }

    /**
     *
     * @param projectCategory
     * @param subCategoryString
     * @return
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
