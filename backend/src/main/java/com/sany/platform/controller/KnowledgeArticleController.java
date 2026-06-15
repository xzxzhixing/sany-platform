package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.model.entity.KnowledgeArticle;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.KnowledgeArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/knowledge")
public class KnowledgeArticleController {

    private static final Logger log = LoggerFactory.getLogger(KnowledgeArticleController.class);

    @Resource
    private KnowledgeArticleService knowledgeArticleService;

    @GetMapping("/page")
    public Result<PageResult<KnowledgeArticle>> pageList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "15") Integer pageSize) {
        return Result.success(knowledgeArticleService.pageList(category, title, status, pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public Result<KnowledgeArticle> getById(@PathVariable Long id) {
        return Result.success(knowledgeArticleService.getById(id));
    }

    @PostMapping
    public Result<KnowledgeArticle> create(@RequestBody KnowledgeArticle article) {
        return Result.success(knowledgeArticleService.create(article));
    }

    @PutMapping
    public Result<Void> update(@RequestBody KnowledgeArticle article) {
        knowledgeArticleService.update(article);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        knowledgeArticleService.delete(id);
        return Result.success();
    }
}
