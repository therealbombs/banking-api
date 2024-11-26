package com.ox.banking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ox.banking.entity.TextResource;
import com.ox.banking.enums.Language;
import com.ox.banking.repository.base.BaseHistoricalRepository;

@Repository
public interface TextResourceRepository extends BaseHistoricalRepository<TextResource, Long> {
	Optional<TextResource> findByKeyAndLanguageAndVersionAndValidToIsNull(String key, Language language,
			Integer version);

	List<TextResource> findByLanguageAndVersionAndValidToIsNull(Language language, Integer version);

	Optional<TextResource> findFirstByKeyAndLanguageOrderByVersionDesc(String key, Language language);

	Optional<TextResource> findFirstByLanguageOrderByVersionDesc(Language language);
}