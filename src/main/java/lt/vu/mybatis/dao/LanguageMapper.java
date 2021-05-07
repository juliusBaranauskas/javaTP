package lt.vu.mybatis.dao;

import lt.vu.mybatis.model.Language;
import org.apache.ibatis.annotations.Select;
import org.mybatis.cdi.Mapper;

import java.util.List;

@Mapper
public interface LanguageMapper {

    @Select("Select * from language")
    List<Language> findAll();

    int insert(Language language);
}
