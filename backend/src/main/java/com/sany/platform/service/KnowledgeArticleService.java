package com.sany.platform.service;

import com.sany.platform.model.entity.KnowledgeArticle;
import com.sany.platform.model.vo.PageResult;

public interface KnowledgeArticleService {

    KnowledgeArticle getById(Long id);

    PageResult<KnowledgeArticle> pageList(String category, String title, Integer status,
                                          Integer pageNum, Integer pageSize);

    KnowledgeArticle create(KnowledgeArticle article);

    void update(KnowledgeArticle article);

    void delete(Long id);
}
