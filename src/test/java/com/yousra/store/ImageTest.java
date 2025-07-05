package com.yousra.store;

import com.yousra.store.model.Image;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageTest {

    @Test
    public void testArticleProperties(){
        Image img1 = new Image();
        img1.setId(UUID.randomUUID());
        img1.setUrl("img1.jpeg");
        assertEquals("img1.jpeg", img1.getUrl());
    }
}
