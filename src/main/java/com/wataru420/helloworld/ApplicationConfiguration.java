package com.wataru420.helloworld;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wataru420.helloworld.core.Template;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class ApplicationConfiguration extends Configuration {
	@NotEmpty
	private String template;
	@NotEmpty
	private String defaultName = "Stranger";
	@Valid
	@NotNull
	private DataSourceFactory database = new DataSourceFactory();
	
	@Valid
    @NotNull
    @JsonProperty
    private RedisConfiguration redis = new RedisConfiguration();

	@JsonProperty
	public String getTemplate() {
		return template;
	}

	@JsonProperty
	public void setTemplate(String template) {
		this.template = template;
	}

	@JsonProperty
	public String getDefaultName() {
		return defaultName;
	}

	@JsonProperty
	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public Template buildTemplate() {
		return new Template(template, defaultName);
	}

	@JsonProperty("database")
	public DataSourceFactory getDataSourceFactory() {
		return database;
	}

	@JsonProperty("database")
	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.database = dataSourceFactory;
	}
	
	public RedisConfiguration getRedis()
	{
		return redis;
	}
}
