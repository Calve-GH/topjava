package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryUtil {
    public static <T extends AbstractBaseEntity> T saveModel(T model, AtomicInteger counter, Map<Integer, T> repository) {
        if (model.isNew()) {
            model.setId(counter.incrementAndGet());
            repository.put(model.getId(), model);
            return model;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(model.getId(), (id, oldModel) -> model);
    }
}
