package com.wataru420.helloworld;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.wataru420.helloworld.core.Music;
import com.wataru420.helloworld.core.Person;
import com.wataru420.helloworld.core.Template;
import com.wataru420.helloworld.db.MusicDAO;
import com.wataru420.helloworld.db.PersonDAO;
import com.wataru420.helloworld.filter.DateNotSpecifiedFilter;
import com.wataru420.helloworld.filter.DateNotSpecifiedServletFilter;
import com.wataru420.helloworld.filter.DateRequiredFeature;
import com.wataru420.helloworld.health.TemplateHealthCheck;
import com.wataru420.helloworld.resources.FilteredResource;
import com.wataru420.helloworld.resources.HelloWorldResource;
import com.wataru420.helloworld.resources.MusicResource;
import com.wataru420.helloworld.resources.PeopleResource;
import com.wataru420.helloworld.resources.ViewResource;

public class DropwizardApplication extends Application<ApplicationConfiguration> {
	public static void main(String[] args) throws Exception {
		new DropwizardApplication().run(args);
	}

	private final HibernateBundle<ApplicationConfiguration> hibernateBundle = new HibernateBundle<ApplicationConfiguration>(
			Person.class,Music.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(
				ApplicationConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public String getName() {
		return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
		// bootstrap.addCommand(new RenderCommand());
		bootstrap.addBundle(new AssetsBundle());
		bootstrap.addBundle(new MigrationsBundle<ApplicationConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(
					ApplicationConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});
		bootstrap.addBundle(hibernateBundle);
		bootstrap.addBundle(new ViewBundle());
		
        //bootstrap.setName("dropwizard-redis");
        bootstrap.addBundle(GuiceBundle.newBuilder()
            .addModule(new ApplicationModule())
            .enableAutoConfig(getClass().getPackage().getName())
            .build()
        );
	}

	@Override
	public void run(ApplicationConfiguration configuration,
			Environment environment) throws ClassNotFoundException {
		final PersonDAO dao = new PersonDAO(hibernateBundle.getSessionFactory());
		final MusicDAO musicDAO = new MusicDAO(hibernateBundle.getSessionFactory());
		final JedisPool jedisPool = new JedisPool(
				new JedisPoolConfig(),
				configuration.getRedis().getHostname(),
				configuration.getRedis().getPort());

		// environment.jersey().getResourceConfig().getResourceFilterFactories().add(new
		// DateNotSpecifiedFilterFactory());
		//
		// environment.jersey().register(new BasicAuthProvider<>(new
		// ExampleAuthenticator(),
		// "SUPER SECRET STUFF"));
		// environment.jersey().register(new HelloWorldResource(template));
		// environment.jersey().register(new ProtectedResource());
		
		// environment.jersey().register(new PersonResource(dao));
		// environment.jersey().register(new FilteredResource());
		
		//environment.jersey().register(new PreMatchingFilter());
		
		//environment.jersey().register(new DateRequiredFeature());
		//environment.jersey().register(DateRequiredFeature.class);
		//environment.jersey().getResourceConfig().getContainerRequestFilters().add(new DateNotSpecifiedFilter());
		//environment.jersey().register(new DateNotSpecifiedFilter());
		//environment.jersey().getResourceConfig().getContainerResponseFilters().add(new DateNotSpecifiedFilterFactory());
		
		//environment.jersey().getResourceConfig().getContainerRequestFilters().add(new PreMatchingFilter());
		//environment.servlets().addFilter("PreMatchingFilter",DateNotSpecifiedServletFilter.class).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

		final Template template = configuration.buildTemplate();
		environment.healthChecks().register("template",
				new TemplateHealthCheck(template));
		environment.jersey().register(new ViewResource());
		environment.jersey().register(new HelloWorldResource(template));
		environment.jersey().register(new PeopleResource(dao,jedisPool));
		environment.jersey().register(new MusicResource(musicDAO,jedisPool));
		


		environment.jersey().register(new FilteredResource());
		


	}
}
