package net.apexes.fetion4j.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
@ConditionalOnProperty(prefix = Fetion4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Fetion4jProperties.class })
public class Fetion4jAutoConfiguration {

	//spring 资源路径匹配解析器
	//“classpath”： 用于加载类路径（包括jar包）中的一个且仅一个资源；对于多个匹配的也只返回一个，所以如果需要多个匹配的请考虑“classpath*:”前缀
	//“classpath*”： 用于加载类路径（包括jar包）中的所有匹配的资源。带通配符的classpath使用“ClassLoader”的“Enumeration<URL> getResources(String name)”
	//方法来查找通配符之前的资源，然后通过模式匹配来获取匹配的资源。
	protected static ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	/*
	@Bean
	@ConditionalOnMissingBean
	
*/
}
