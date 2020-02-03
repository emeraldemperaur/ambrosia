package iot.empiaurhouse.ambrosia.services;

import iot.empiaurhouse.ambrosia.model.Recipe;
import iot.empiaurhouse.ambrosia.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            Recipe focusRecipe = recipeRepository.findById(recipeId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            focusRecipe.setImage(byteObjects);
            recipeRepository.save(focusRecipe);
            log.debug("Successfully uploaded a Multipart File for: " + focusRecipe.getDescription());

        } catch (IOException e) {
            log.error("Error occurred", e);
            e.printStackTrace();
        }

    }



}
