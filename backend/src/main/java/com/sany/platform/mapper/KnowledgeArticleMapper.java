package com.sany.platform.mapper;

import com.sany.platform.model.entity.KnowledgeArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KnowledgeArticleMapper {

    KnowledgeArticle selectById(@Param("id") Long id);

    List<KnowledgeArticle> selectList(@Param("category") String category,
                                      @Param("title") String title,
                                      @Param("status") Integer status);

    long countList(@Param("category") String category,
                   @Param("title") String title,
                   @Param("status") Integer status);

    int insert(KnowledgeArticle article);

    int updateById(KnowledgeArticle article);

    int deleteById(@Param("id") Long id);

    String selectMaxArticleNo();
}
