package com.simon.framework.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;

/**
 * @ClassName: ScanClassUtils <br>
 * @Description: 搜索包路径下class <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public class ScanClassUtils {
	/**
	 * 根据多个包名搜索class 例如: ScanClassUtils.scanPakcages("neusoft.com.**.*");
	 * 
	 * @param basePackages
	 *            各个包名使用逗号分隔,各个包名可以有通配符
	 * @return List包含className
	 */
	public static List<String> scanPackages(final String basePackages) throws IllegalArgumentException {
		Assert.notNull(basePackages, "'basePakcages' must be not null");
		final ResourcePatternResolver rl = new PathMatchingResourcePatternResolver();
		final MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(rl);
		final List<String> result = new ArrayList<String>();
		final String[] arrayPackages = basePackages.split(",");
		try {
			for (int j = 0; j < arrayPackages.length; j++) {
				final String packageToScan = arrayPackages[j];
				final String packagePart = packageToScan.replace('.', '/');
				final String classPattern = "classpath*:/" + packagePart + "/**/*.class";
				final Resource[] resources = rl.getResources(classPattern);
				for (int i = 0; i < resources.length; i++) {
					final Resource resource = resources[i];
					final MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
					final String className = metadataReader.getClassMetadata().getClassName();
					result.add(className);
				}
			}
		} catch (final Exception e) {
			new IllegalArgumentException("scan pakcage class error,pakcages:" + basePackages);
		}

		return result;
	}

}
