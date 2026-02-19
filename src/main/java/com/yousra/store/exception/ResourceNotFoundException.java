package com.yousra.store.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, UUID id) {
        super(resourceName + " non trouvé(e) : " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
