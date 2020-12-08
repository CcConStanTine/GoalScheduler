package com.pk.ms.services.user;

import com.pk.ms.dao.user.ImageRepository;
import com.pk.ms.entities.user.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ImageServiceTest {

    @Mock
    ImageRepository imageRepository;

    @Mock
    ImageUploader imageUploader;

    Image image1;
    Image image2;
    MultipartFile file;

    ImageService imageService;

    @BeforeEach
    void setUp() {
        image1 = new Image("someUrl1.com");
        image2 = new Image("someUrl2.com");
        file = new MockMultipartFile("fileThatDoesNotExists.txt",
                "fileThatDoesNotExists.txt",
                "text/plain",
                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));

        imageService = new ImageService(imageRepository, imageUploader);
    }

    @Test
    @DisplayName("Check if method saveImage() calls imageUploader method uploadImageOnHosting()")
    void should_MethodSaveImageCallsImageUploaderMethodUploadImageOnHosting() {
        //given
        given(imageUploader.uploadImage(file)).willReturn("someURL.com");
        //when
        imageService.saveImage(file);
        //then
        verify(imageUploader, Mockito.times(1)).uploadImage(file);
    }

    @Test
    @DisplayName("Check if method saveImage() calls repository method save()")
    void should_MethodSaveImageCallsRepositoryMethodSave() {
        //when
        imageService.saveImage(file);
        //then
        verify(imageRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Check if method uploadImage() calls imageUploader method uploadImageOnHosting()")
    void should_MethodUploadImageCallsImageUploaderMethodUploadImageOnHosting() {
        //given
        given(imageUploader.uploadImage(file)).willReturn("someURL.com");
        //when
        imageService.uploadImage(image1, file);
        //then
        verify(imageUploader, Mockito.times(1)).uploadImage(file);
    }

    @Test
    @DisplayName("Check if method uploadImage() update Image fileUrl field")
    void should_MethodUploadImageChangeImageFileUrlField() {
        //given
        String imageFileUrlBeforeUpdate = image1.getFileUrl();
        given(imageUploader.uploadImage(file)).willReturn("someURL.com");
        //when
        imageService.uploadImage(image1, file);
        //then
        assertNotEquals(imageFileUrlBeforeUpdate, image1.getFileUrl());
    }

    @Test
    @DisplayName("Check if method uploadImage() calls repository method save()")
    void should_MethodUploadImageCallsRepositoryMethodSave() {
        //when
        imageService.uploadImage(image1, file);
        //then
        verify(imageRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Check if method deleteImage() calls repository method delete() when given Image is not null")
    void should_MethodDeleteImageDeleteCallsRepositoryMethodDelete_When_GivenImageIsNotNull() {
        //when
        imageService.deleteImage(image1);
        //then
        verify(imageRepository, Mockito.times(1)).delete(image1);
    }

    @Test
    @DisplayName("Check if method deleteImage() does not call repository method delete() when given Image is null")
    void shouldNot_MethodDeleteImageDeleteCallsRepositoryMethodDelete_When_GivenImageIsNull() {
        //when
        imageService.deleteImage(null);
        //then
        verify(imageRepository, Mockito.times(0)).delete(any());
    }

    @Test
    @DisplayName("Check if method deleteImage() returns proper message when image is not null")
    void should_MethodDeleteImageReturnsProperMessage_When_GivenImageIsNotNull() {
        //when
        String actual = imageService.deleteImage(image1);
        //then
        assertEquals("Image deleted successfully. ", actual);
    }

    @Test
    @DisplayName("Check if method deleteImage() returns proper message when image is null")
    void should_MethodDeleteImageReturnsProperMessage_When_GivenImageIsNull() {
        //when
        String actual = imageService.deleteImage(null);
        //then
        assertEquals("Image deleted successfully. ", actual);
    }
}