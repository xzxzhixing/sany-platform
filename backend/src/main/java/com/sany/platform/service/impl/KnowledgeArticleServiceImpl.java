package com.sany.platform.service.impl;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.mapper.KnowledgeArticleMapper;
import com.sany.platform.model.entity.KnowledgeArticle;
import com.sany.platform.model.vo.PageResult;
import com.sany.platform.service.KnowledgeArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class KnowledgeArticleServiceImpl implements KnowledgeArticleService {

    private static final Logger log = LoggerFactory.getLogger(KnowledgeArticleServiceImpl.class);

    @Resource
    private KnowledgeArticleMapper knowledgeArticleMapper;

    @Override
    public KnowledgeArticle getById(Long id) {
        if (id == null) throw new BusinessException(400, "文章ID不能为空");
        KnowledgeArticle article = knowledgeArticleMapper.selectById(id);
        if (article == null) throw new BusinessException(404, "文章不存在或已被删除");
        return article;
    }

    @Override
    public PageResult<KnowledgeArticle> pageList(String category, String title, Integer status,
                                                 Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) pageNum = 1;
        if (pageSize == null || pageSize < 1) pageSize = 15;

        long total = knowledgeArticleMapper.countList(category, title, status);
        if (total == 0) {
            return new PageResult<>(0, pageNum, pageSize, Collections.emptyList());
        }

        List<KnowledgeArticle> all = knowledgeArticleMapper.selectList(category, title, status);
        int from = (pageNum - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());
        return new PageResult<>(total, pageNum, pageSize, all.subList(from, to));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KnowledgeArticle create(KnowledgeArticle article) {
        log.info("创建知识文章, 标题: {}", article.getTitle());
        article.setArticleNo(generateArticleNo());
        article.setDeleted(0);
        int rows = knowledgeArticleMapper.insert(article);
        if (rows != 1) throw new BusinessException("文章创建失败");
        log.info("知识文章创建成功, ID: {}, 编号: {}", article.getId(), article.getArticleNo());
        return article;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(KnowledgeArticle article) {
        if (article.getId() == null) throw new BusinessException(400, "文章ID不能为空");
        KnowledgeArticle exist = knowledgeArticleMapper.selectById(article.getId());
        if (exist == null) throw new BusinessException(404, "文章不存在");
        int rows = knowledgeArticleMapper.updateById(article);
        if (rows != 1) throw new BusinessException("文章更新失败");
        log.info("知识文章更新成功, ID: {}", article.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除知识文章, ID: {}", id);
        if (id == null) throw new BusinessException(400, "文章ID不能为空");
        KnowledgeArticle exist = knowledgeArticleMapper.selectById(id);
        if (exist == null) throw new BusinessException(404, "文章不存在");
        int rows = knowledgeArticleMapper.deleteById(id);
        if (rows != 1) throw new BusinessException("删除失败");
        log.info("知识文章删除成功, ID: {}", id);
    }

    private String generateArticleNo() {
        String max = knowledgeArticleMapper.selectMaxArticleNo();
        if (max == null) return "KB-0001";
        int seq = Integer.parseInt(max.substring(max.lastIndexOf("-") + 1)) + 1;
        return "KB-" + String.format("%04d", seq);
    }
}
