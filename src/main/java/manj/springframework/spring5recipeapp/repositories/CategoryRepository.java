package manj.springframework.spring5recipeapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import manj.springframework.spring5recipeapp.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findByDescription(String description);

}
