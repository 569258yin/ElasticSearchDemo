package es.dao;

import es.item.bean.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cao on 3/24/16.
 */
@Repository("categoryDAO")
public interface CategoryDAO {
    List<Category> getCategoriesByTenantId(@Param("tenantId") String tenantId, @Param("withDeleted") boolean withDeleted);

    Category getCategoryById(@Param("id") String id);

    int createCategory(@Param("category") Category category);

    int createCategories(@Param("categories") List<Category> categories);

    int updateCategory(@Param("category") Category category);

    int deleteCategory(@Param("id") String id);

    List<Category> getAllCategories();

    int importCategories(@Param("items") List<Category> categories);
}
