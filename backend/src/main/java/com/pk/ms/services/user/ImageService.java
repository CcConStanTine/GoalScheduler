package com.pk.ms.services.user;

import com.pk.ms.dao.user.ImageRepository;
import com.pk.ms.entities.user.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final ImageUploader imageUploader;

    public ImageService(ImageRepository imageRepository, ImageUploader imageUploader) {
        this.imageRepository = imageRepository;
        this.imageUploader = imageUploader;
    }

    public Image saveImage(MultipartFile file) {
        String fileUrl = uploadImageOnHosting(file);
        return save(new Image(fileUrl));
    }

    public Image uploadImage(Image image, MultipartFile file) {
        String fileUrl = uploadImageOnHosting(file);
        image.setFileUrl(fileUrl);
        return save(image);
    }

    public String deleteImage(Image image) {
        if(isNotNull(image)) {
            delete(image);
        }
        return "Image deleted successfully. ";
    }

    private boolean isNotNull(Image image) {
        return image != null;
    }

    private Image save(Image image) {
        return imageRepository.save(image);
    }

    private String uploadImageOnHosting(MultipartFile file) {
        return imageUploader.uploadImage(file);
    }

    private Image getImageByIdFromRepo(long imageId) {
        return imageRepository.findById(imageId);
    }

    private void delete(Image image) {
        imageRepository.delete(image);
    }
}
