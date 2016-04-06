package org.github.nelsonstr.maven.enforcer.rule;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;


/**
 * Checks package cycles within the target folder classes folder.
 * <p>
 * This code is based on code by Daniel Seidewitz shown on
 * <a href="http://stackoverflow.com/questions/3416547/maven-jdepend-fail-build-with-cycles">stackoverflow</a>
 *
 * </p>
 */

/*
 * @startuml
 * Developer -> Jenkins : compile
 * Jenkins --> Ansible : Install
 * @enduml
 */

public class NoCyclicPackageDependencyRule implements EnforcerRule {

	private boolean shouldIfail = false;
	private String ignoredArtifactId = "";

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute(final EnforcerRuleHelper helper)
			throws EnforcerRuleException {
		final Log log = helper.getLog();

		try {
			final MavenProject project = (MavenProject) helper
					.evaluate("${project}");
			final String projectName = project.getName();

			final File classesDir = new File(
					(String) helper
							.evaluate("${project.build.outputDirectory}"));

			if (classesDir.canRead()) {
				final JDepend jdepend = new JDepend();
				jdepend.addDirectory(classesDir.getAbsolutePath());
				addTestClassesIfRequested(helper, classesDir, jdepend);

				final Collection<JavaPackage> packages = jdepend.analyze();

				if (jdepend.containsCycles()) {
					final String buffer = collectCycles(packages);
					final StringBuilder stringBuffer = new StringBuilder(
							"Dependency cycle check found package cycles in '");
					stringBuffer.append(projectName).append("': ")
							.append(buffer);
					if (this.shouldIfail
							&& !ignoredArtifactId.contains(project
									.getGroupId()
									+ ':'
									+ project.getArtifactId())) {
						throw new EnforcerRuleException(stringBuffer.toString());
					}
				} else {
					log.info("No package cycles found in '" + projectName
							+ "'.");
				}
			} else {
				log.warn("Skipping package cycle analysis since '" + classesDir
						+ "' does not exist.");
			}
		} catch (final ExpressionEvaluationException e) {
			throw new EnforcerRuleException(
					"Dependency cycle check is unable to evaluate expression '"
							+ e.getLocalizedMessage() + "'.", e);
		} catch (final IOException e) {
			throw new EnforcerRuleException(
					"Dependency cycle check is unable to access a classes directory '"
							+ e.getLocalizedMessage() + "'.", e);
		}
	}

	private static void addTestClassesIfRequested(
			final EnforcerRuleHelper helper, final File classesDir,
			final JDepend jdepend) throws ExpressionEvaluationException,
			IOException {
		final File testClassesDir = new File(
				(String) helper
						.evaluate("${project.build.testOutputDirectory}"));
		if (testClassesDir.canRead()) {
			jdepend.addDirectory(classesDir.getAbsolutePath());
		}
	}

	private static String collectCycles(final Collection<JavaPackage> packages) {
		final StringBuilder buffer = new StringBuilder(512);
		StringBuilder tab = new StringBuilder("\n\t");
		for (final JavaPackage aPackage : packages) {
			final List<JavaPackage> dependencies = new ArrayList<>();
			aPackage.collectCycle(dependencies);
			if (!dependencies.isEmpty()) {
				for (final JavaPackage dependency : dependencies) {
					buffer.append(tab).append("->")
							.append(dependency.getName());
					tab.append('\t');
				}
				buffer.append('\n');
				tab = new StringBuilder("\n\t");
			}
		}
		return buffer.toString();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Always returns the empty string.
	 * </p>
	 * <p/>
	 * 
	 * @see org.apache.maven.enforcer.rule.api.EnforcerRule#getCacheId()
	 */
	@Override
	public String getCacheId() {
		return "";
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Always returns <code>false</code>.
	 * </p>
	 * <p/>
	 * 
	 * @see org.apache.maven.enforcer.rule.api.EnforcerRule#isCacheable()
	 */
	@Override
	public boolean isCacheable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Always returns <code>false</code>.
	 * </p>
	 * <p/>
	 * 
	 * @see org.apache.maven.enforcer.rule.api.EnforcerRule#isResultValid(org.apache.maven.enforcer.rule.api.EnforcerRule)
	 */
	@Override
	public boolean isResultValid(final EnforcerRule rule) {
		return false;
	}

	// --- object basics
	// --------------------------------------------------------

	/**
	 * Simple param. This rule will fail if the value is true.
	 * <p/>
	 * 
	 * @return the shouldIfail
	 */
	public boolean isShouldIfail() {
		return shouldIfail;
	}

	/**
	 * Simple param. This rule will fail if the value is true.
	 * <p/>
	 * 
	 * @param shouldIfail
	 *            the shouldIfail to set
	 */
	public void setShouldIfail(boolean shouldIfail) {
		this.shouldIfail = shouldIfail;
	}

	public String getIgnoredArtifactId() {
		return ignoredArtifactId;
	}

	public void setIgnoredArtifactId(String ignoredArtifactId) {
		this.ignoredArtifactId = ignoredArtifactId;
	}
}
