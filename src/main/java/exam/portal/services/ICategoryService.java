package exam.portal.services;

import java.util.List;

import exam.portal.models.Category;

public interface ICategoryService {
	void save(Category cat);
	List<Category> listAll();
	Category findById(int id);
	void deleteCategory(int id);
	void updateStatus(int id, boolean status);
}
