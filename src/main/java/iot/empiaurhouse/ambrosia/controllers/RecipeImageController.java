package iot.empiaurhouse.ambrosia.controllers;

import iot.empiaurhouse.ambrosia.commandobjects.RecipeCommand;
import iot.empiaurhouse.ambrosia.services.ImageService;
import iot.empiaurhouse.ambrosia.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class RecipeImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public RecipeImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping({"recipe/{id}/img","recipe/{id}/image","recipe/{id}/imageupload"})
    public String renderImageUploader(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        model.addAttribute("buttonText","Upload");

        return "recipe/imageupload";
    }

    @PostMapping("recipe/{id}/img")
    public String handleImagePost(@PathVariable String id, @RequestParam("recipeImgFile") MultipartFile file){

        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/recipe/modusoperandi/" + id;
    }



    @GetMapping("recipe/{id}/getimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));

        if (recipeCommand.getImage() != null) {
            byte[] byteArray = new byte[recipeCommand.getImage().length];
            int i = 0;

            for (Byte wrappedByte : recipeCommand.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }



}
