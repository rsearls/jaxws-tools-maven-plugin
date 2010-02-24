/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.ws.plugins.tools;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

/**
 * 
 * 
 * @author alessio.soldano@jboss.com
 * @since 24-Feb-2010
 *
 */
abstract class AbstractToolsMojo extends AbstractMojo
{
   /**
   * @parameter expression="${project}"
   * @readonly
   */
   protected MavenProject project;

   /**
    * Map of of plugin artifacts.
    *
    * @parameter expression="${plugin.artifactMap}"
    * @readonly
    */
   private Map pluginArtifactMap;

   /**
    * Enables/Disables Java source generation.
    * 
    * @parameter default-value="true"
    */
   protected Boolean generateSource;
   
   /**
    * 
    * @parameter default-value="false"
    */
   protected Boolean verbose;
   
   /**
    * Either ${build.outputDirectory} or ${build.testOutputDirectory}.
    */
   protected abstract File getDestDir();
   
   /**
    * Either ${project.compileClasspathElements} or ${project.testClasspathElements}
    */
   protected abstract List<String> getClasspath();
   
   protected ClassLoader getMavenClasspathAwareClassLoader(ClassLoader parent)
   {
      List<String> classpath = getClasspath();
      final int size = classpath.size();
      URL[] urls = new URL[size];
      for (int i=0; i < size; i++)
      {
         try
         {
            urls[i] = new File(classpath.get(i)).toURI().toURL();
         }
         catch (MalformedURLException mue)
         {
            getLog().warn("Skipping invalid classpath element: " + classpath.get(i));
         }
      }
      return new URLClassLoader(urls, parent);
   }
   
}
