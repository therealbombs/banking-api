// src/main/java/com/example/banking/service/TextResourceService.java
package com.ox.banking.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ox.banking.entity.TextResource;
import com.ox.banking.enums.Language;
import com.ox.banking.repository.TextResourceRepository;

@Service
public class TextResourceService {

	@Autowired
	private TextResourceRepository textResourceRepository;

	@Cacheable(value = "texts", key = "#key + '_' + #language + '_' + #version")
	public String getText(String key, Language language, Integer version) {
		return textResourceRepository.findByKeyAndLanguageAndVersionAndValidToIsNull(key, language, version)
				.map(TextResource::getText).orElse(key);
	}

	@Cacheable(value = "texts", key = "'all_' + #language + '_' + #version")
	public Map<String, String> getAllTexts(Language language, Integer version) {
		return textResourceRepository.findByLanguageAndVersionAndValidToIsNull(language, version).stream()
				.collect(Collectors.toMap(TextResource::getKey, TextResource::getText));
	}

	@Transactional
	@CacheEvict(value = "texts", allEntries = true)
	public TextResource updateText(String key, Language language, String newText) {
		// Trova l'ultima versione
		Optional<TextResource> currentOpt = textResourceRepository.findFirstByKeyAndLanguageOrderByVersionDesc(key,
				language);

		// Crea la nuova versione
		TextResource newVersion = new TextResource();
		newVersion.setKey(key);
		newVersion.setLanguage(language);
		newVersion.setText(newText);

		// Incrementa la versione o inizia da 1
		Integer newVersionNumber = currentOpt.map(TextResource::getVersion).map(v -> v + 1).orElse(1);
		newVersion.setVersion(newVersionNumber);

		// Imposta la data di validità della vecchia versione
		currentOpt.ifPresent(current -> {
			current.setValidTo(LocalDateTime.now());
			textResourceRepository.save(current);
		});

		return textResourceRepository.save(newVersion);
	}

	@Cacheable(value = "texts", key = "#key + '_' + #language + '_latest'")
	public String getLatestText(String key, Language language) {
		return textResourceRepository.findFirstByKeyAndLanguageOrderByVersionDesc(key, language)
				.map(TextResource::getText).orElse(key);
	}

	@Cacheable(value = "texts", key = "'latest_version_' + #language")
	public Integer getLatestVersion(Language language) {
		return textResourceRepository.findFirstByLanguageOrderByVersionDesc(language).map(TextResource::getVersion)
				.orElse(0);
	}

	@CacheEvict(value = "texts", allEntries = true)
	public void clearCache() {
		// Il metodo è vuoto perché l'annotazione fa tutto il lavoro
	}
}