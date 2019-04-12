package com.weston.study.boot.mybatis.starter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义扫描，支持spring的动态配置 A {@link ImportBeanDefinitionRegistrar} to allow
 * annotation configuration of MyBatis mapper scanning. Using an @Enable
 * annotation allows beans to be registered via @Component configuration,
 * whereas implementing {@code BeanDefinitionRegistryPostProcessor} will work
 * for XML configuration.
 *
 * @author Michael Lanyon
 * @author Eduardo Macarron
 * 
 * @see MapperFactoryBean
 * @see ClassPathMapperScanner
 * @since 1.2.0
 */
@Slf4j
public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

	private ResourceLoader resourceLoader;
	private Environment env;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		AnnotationAttributes annoAttrs = AnnotationAttributes
				.fromMap(importingClassMetadata.getAnnotationAttributes(MapperScan.class.getName()));
		ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);

		// this check is needed in Spring 3.1
		if (resourceLoader != null) {
			scanner.setResourceLoader(resourceLoader);
		}

		Class<? extends Annotation> annotationClass = annoAttrs.getClass("annotationClass");
		if (!Annotation.class.equals(annotationClass)) {
			scanner.setAnnotationClass(annotationClass);
		}

		Class<?> markerInterface = annoAttrs.getClass("markerInterface");
		if (!Class.class.equals(markerInterface)) {
			scanner.setMarkerInterface(markerInterface);
		}

		Class<? extends BeanNameGenerator> generatorClass = annoAttrs.getClass("nameGenerator");
		if (!BeanNameGenerator.class.equals(generatorClass)) {
			scanner.setBeanNameGenerator(BeanUtils.instantiateClass(generatorClass));
		}

		Class<? extends MapperFactoryBean> mapperFactoryBeanClass = annoAttrs.getClass("factoryBean");
		if (!MapperFactoryBean.class.equals(mapperFactoryBeanClass)) {
			scanner.setMapperFactoryBean(BeanUtils.instantiateClass(mapperFactoryBeanClass));
		}

		scanner.setSqlSessionTemplateBeanName(annoAttrs.getString("sqlSessionTemplateRef"));
		scanner.setSqlSessionFactoryBeanName(annoAttrs.getString("sqlSessionFactoryRef"));

		List<String> basePackages = new ArrayList<String>();
		for (String pkg : annoAttrs.getStringArray("value")) {
			if (StringUtils.hasText(pkg)) {
				basePackages.add(parsePlaceHolder(pkg));
			}
		}
		for (String pkg : annoAttrs.getStringArray("basePackages")) {
			if (StringUtils.hasText(pkg)) {
				basePackages.add(parsePlaceHolder(pkg));
			}
		}
		for (Class<?> clazz : annoAttrs.getClassArray("basePackageClasses")) {
			basePackages.add(ClassUtils.getPackageName(clazz));
		}
		scanner.registerFilters();
		scanner.doScan(StringUtils.toStringArray(basePackages));
	}

	private String parsePlaceHolder(String pro) {
		if (pro != null && pro.contains(PropertySourcesPlaceholderConfigurer.DEFAULT_PLACEHOLDER_PREFIX)) {
			String value = env.getProperty(pro.substring(2, pro.length() - 1));

			if (log.isDebugEnabled()) {
				log.debug("find placeholder value " + value + " for key " + pro);
			}

			if (null == value) {
				throw new IllegalArgumentException("property " + pro + " can not find!!!");
			}
			return value;
		}
		return pro;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}
}
