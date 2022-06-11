package com.xiaoyuan.back.controller;

import com.xiaoyuan.back.db.entity.Category;
import com.xiaoyuan.back.service.CategoryService;
import com.xiaoyuan.back.vo.param.CategoryParam;
import com.xiaoyuan.utils.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FileName:    CategoryController
 * Author:      小袁
 * Date:        2022/4/15 10:54
 * Description: 分类栏目的控制层
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类栏目控制层")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类栏目数据
     * @param categoryParam
     * @return
     */
    @PostMapping
    public R insertCategory(@RequestBody CategoryParam categoryParam) {
        return categoryService.insert(categoryParam);
    }

    /**
     * 根据ID删除分类栏目
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据ID删除所有子分类栏目（包括父级分类如果有）")
    public R removeCategoryById(@PathVariable(value = "id") Integer id) {
        return categoryService.remove(id);
    }

    @PutMapping
    public R modifyCategoryById(@RequestBody Category category) {
        return categoryService.modify(category);
    }

    @PutMapping("move")
    public R moveCategory(@RequestBody Category category) {
        return categoryService.move(category);
    }

    /**
     * 查询所有分类的目录结构
     * @return
     */
    @GetMapping
    @ApiOperation(value = "查询所有分类的目录结构")
    public R getCategoryList() {
        return categoryService.listCategory();
    }

    /**
     * 根据ID获取对象
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation(value = "根据ID获取对象")
    public R getCategoryById(@PathVariable(value = "id") Integer id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("parent")
    public R findParentCategoryAll() {
        return categoryService.findParentCategoryAll();
    }
}
